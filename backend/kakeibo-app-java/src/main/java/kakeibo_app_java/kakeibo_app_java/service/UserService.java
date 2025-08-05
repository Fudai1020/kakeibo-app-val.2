package kakeibo_app_java.kakeibo_app_java.service;

import kakeibo_app_java.kakeibo_app_java.entity.User;

public interface UserService {
    User register(String email,String password,String confirmPassword);
    User login(String email,String password);
}