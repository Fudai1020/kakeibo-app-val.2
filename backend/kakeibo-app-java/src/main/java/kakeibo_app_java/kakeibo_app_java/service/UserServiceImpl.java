package kakeibo_app_java.kakeibo_app_java.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kakeibo_app_java.kakeibo_app_java.entity.User;
import kakeibo_app_java.kakeibo_app_java.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String email, String password, String confirmPassword) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if(existingUser.isPresent()){
        throw new RuntimeException("このメールアドレスはすでに登録されています");
        }
        if(!password.equals(confirmPassword)){
            throw new RuntimeException("パスワードが一致しません");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setMemo("");
        user.setCreatedAt(new java.util.Date());

        return userRepository.save(user);
    }

    @Override
    public User login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if(userOpt.isEmpty()){
            throw new RuntimeException("メールアドレスが正しくありません");
        }
        User user = userOpt.get();
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("パスワードが正しくありません");
        }
        return user;
    }

     
}