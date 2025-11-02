package kakeibo_app_java.kakeibo_app_java.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kakeibo_app_java.kakeibo_app_java.dto.ChangePasswordRequest;
import kakeibo_app_java.kakeibo_app_java.dto.EditProfileReqest;
import kakeibo_app_java.kakeibo_app_java.dto.LoginRequest;
import kakeibo_app_java.kakeibo_app_java.dto.RegisterRequest;
import kakeibo_app_java.kakeibo_app_java.dto.UserResponse;
import kakeibo_app_java.kakeibo_app_java.entity.User;
import kakeibo_app_java.kakeibo_app_java.security.JwtTokenProvider;
import kakeibo_app_java.kakeibo_app_java.service.UserService;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin(origins = "https://kakeibo-app-val-2.vercel.app")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;


    public UserController(UserService userService,JwtTokenProvider jwtTokenProvider){
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest request) {
        User user = userService.register(request.getEmail(),request.getPassword(), request.getConfirmPassword());
        return new UserResponse(user);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        User user = userService.login(request.getEmail(),request.getPassword());
        if(user == null){
            return ResponseEntity.status(401).body("Invalid emal or password");
        }
        String token = jwtTokenProvider.createToken(user.getEmail());
        return ResponseEntity.ok(Map.of("token",token,"userId",user.getId(),"email",user.getEmail()));
    }
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new UserResponse(user);
    }
    
    @PostMapping("/{id}/editProfile")
    public UserResponse editProfile(@PathVariable Long id, @RequestBody EditProfileReqest reqest) {
        User user = userService.editProfile(id,reqest.getName(), reqest.getMemo());
        return new UserResponse(user);
    }
    @PutMapping("/{id}/changePassword")
    public UserResponse changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest request) {
        User user = userService.changePassword(id, request.getPassword(), request.getNewPassword());
        return new  UserResponse(user);
    
    }
    
    
    
}
