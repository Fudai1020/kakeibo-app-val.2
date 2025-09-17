package kakeibo_app_java.kakeibo_app_java.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

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
    public ShareServiceImpl(SharedRepository sharedRepository,ShareCodeRepository shareCodeRepository,UserRepository userRepository){
        this.sharedRepository = sharedRepository;
        this.shareCodeRepository = shareCodeRepository;
        this.userRepository = userRepository;
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
        return ProfileDto.builder()
            .name(owner.getName())
            .email(owner.getEmail())
            .memo(owner.getMemo())
            .sharedAt(shared.getStartDate().toLocalDate())
            .build();
    }
}
