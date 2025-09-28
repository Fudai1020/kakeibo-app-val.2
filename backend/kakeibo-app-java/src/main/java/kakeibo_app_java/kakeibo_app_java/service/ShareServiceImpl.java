package kakeibo_app_java.kakeibo_app_java.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import kakeibo_app_java.kakeibo_app_java.dto.ProfileDto;
import kakeibo_app_java.kakeibo_app_java.entity.ShareCode;
import kakeibo_app_java.kakeibo_app_java.entity.Shared;
import kakeibo_app_java.kakeibo_app_java.entity.User;
import kakeibo_app_java.kakeibo_app_java.exception.BadRequestException;
import kakeibo_app_java.kakeibo_app_java.repository.ShareCodeRepository;
import kakeibo_app_java.kakeibo_app_java.repository.SharedRepository;
import kakeibo_app_java.kakeibo_app_java.repository.UserRepository;

@Service
public class ShareServiceImpl implements ShareService{
    private final SharedRepository sharedRepository;
    private final ShareCodeRepository shareCodeRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;
    public ShareServiceImpl(SharedRepository sharedRepository,ShareCodeRepository shareCodeRepository,UserRepository userRepository,SimpMessagingTemplate messagingTemplate){
        this.sharedRepository = sharedRepository;
        this.shareCodeRepository = shareCodeRepository;
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
    }
    public static final String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
    public static final int codeLength = 6;

    public String generateRamdomCode(){
        Random random = new Random();
        StringBuilder sb = new StringBuilder(codeLength);
        for(int i = 0;i < codeLength; i++){
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
    @Override
    public String generateShareCode(Long ownerId){
        User user = userRepository.findById(ownerId)
            .orElseThrow(() -> new BadRequestException("ユーザが見つかりません"));
        String code = generateRamdomCode();
        ShareCode shareCode = ShareCode.builder()
            .owner(user)
            .code(code)
            .build();
        shareCodeRepository.save(shareCode);
        return code;
    }
    @Override
    public ProfileDto joinShared(Long partnerId,String code){
        ShareCode shareCode = shareCodeRepository.findByCode(code)
            .orElseThrow(() -> new BadRequestException("合言葉が違います"));
        User owner = shareCode.getOwner();
        User partner = userRepository.findById(partnerId)
            .orElseThrow(() -> new BadRequestException("ユーザが見つかりません"));
        Shared shared = Shared.builder()
            .owner(owner)
            .partner(partner)
            .startDate(LocalDateTime.now())
            .isActive(true)
            .build();
        sharedRepository.save(shared);

        messagingTemplate.convertAndSend("/topic/share/"+owner.getId(),"partnerJoined");
        return ProfileDto.builder()
            .id(owner.getId())
            .name(owner.getName())
            .email(owner.getEmail())
            .memo(owner.getMemo())
            .sharedAt(shared.getStartDate().toLocalDate())
            .build();
    }
    @Override
    public ProfileDto getPartnerProfile(Long userId){
        Shared shared = sharedRepository.findByOwnerIdOrPartnerId(userId,userId)
            .orElseThrow(() -> new BadRequestException("共有相手が見つかりません"));

        User partner  = shared.getOwner().getId().equals(userId)
         ? shared.getPartner() : shared.getOwner();
        
        return ProfileDto.builder()
            .id(partner.getId())
            .name(partner.getName())
            .email(partner.getEmail())
            .memo(partner.getMemo())
            .sharedAt(shared.getStartDate().toLocalDate())
            .build();
    }
    @Override
    public void leaveShared(Long userId){
        Shared shared = sharedRepository.findByOwnerIdOrPartnerId(userId, userId)
            .orElseThrow(() -> new BadRequestException("共有関係が見つかりません"));
        sharedRepository.delete(shared);
        Long partnerId = shared.getOwner().getId().equals(userId)
            ? shared.getPartner().getId()
            : shared.getOwner().getId();
        messagingTemplate.convertAndSend("/topic/share/"+partnerId,"partnerLeft");
    }
}
