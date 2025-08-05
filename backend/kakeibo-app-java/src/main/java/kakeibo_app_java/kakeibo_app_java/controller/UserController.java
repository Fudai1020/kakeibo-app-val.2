package kakeibo_app_java.kakeibo_app_java.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kakeibo_app_java.kakeibo_app_java.dto.LoginRequest;
import kakeibo_app_java.kakeibo_app_java.dto.RegisterRequest;
import kakeibo_app_java.kakeibo_app_java.dto.UserResponse;
import kakeibo_app_java.kakeibo_app_java.entity.User;
import kakeibo_app_java.kakeibo_app_java.service.UserService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService){
        this.userService = userService;
    }
    
    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest request) {
        User user = userService.register(request.getEmail(),request.getPassword(), request.getConfirmPassword());
        return new UserResponse(user);
    }
    @PostMapping("/login")
    public UserResponse login(@RequestBody LoginRequest request) {
        User user = userService.login(request.getEmail(), request.getPassword());
        return new UserResponse(user);
    }
    
    
}
