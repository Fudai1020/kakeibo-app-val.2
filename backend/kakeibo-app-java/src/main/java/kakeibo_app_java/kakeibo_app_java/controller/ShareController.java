package kakeibo_app_java.kakeibo_app_java.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kakeibo_app_java.kakeibo_app_java.dto.JoinRequest;
import kakeibo_app_java.kakeibo_app_java.dto.ProfileDto;
import kakeibo_app_java.kakeibo_app_java.service.ShareService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@CrossOrigin(origins = "https://kakeibo-app-val-2.vercel.app",allowCredentials = "true")
@RequestMapping("/api/shared")
public class ShareController {
    private final ShareService shareService;
    public ShareController(ShareService shareService){
        this.shareService = shareService;
    }
    @PostMapping("/generate/{ownerId}")
    public ResponseEntity<String> generateCode(@PathVariable Long ownerId){
        String code = shareService.generateShareCode(ownerId);
        return ResponseEntity.ok(code);
    }
    @PostMapping("/join")
    public ResponseEntity<ProfileDto> joinShared(@RequestBody JoinRequest joinRequest){
        ProfileDto profileDto = shareService.joinShared(joinRequest.getPartnerId(), joinRequest.getCode());
        return ResponseEntity.ok(profileDto);
    }
    @GetMapping("/profile/{userId}")
    public ResponseEntity<ProfileDto> getPartnerProfile(@PathVariable Long userId) {
        ProfileDto profileDto = shareService.getPartnerProfile(userId);
        return ResponseEntity.ok(profileDto);
    }
    @DeleteMapping("/leave/{userId}")
    public ResponseEntity<Void> stopShared(@PathVariable Long userId){
        shareService.leaveShared(userId);
        return ResponseEntity.noContent().build();
    }
}
