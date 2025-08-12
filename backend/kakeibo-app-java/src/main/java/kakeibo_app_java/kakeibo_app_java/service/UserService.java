package kakeibo_app_java.kakeibo_app_java.service;

import kakeibo_app_java.kakeibo_app_java.entity.User;

public interface UserService {
    User register(String email,String password,String confirmPassword);
    User login(String email,String password);
    User getUserById(Long id);
    User editProfile(Long id,String name,String memo);
    User changePassword(Long id,String password,String newPassword);
}