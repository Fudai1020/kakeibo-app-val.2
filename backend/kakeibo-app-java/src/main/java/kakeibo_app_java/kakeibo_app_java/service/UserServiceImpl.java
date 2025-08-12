package kakeibo_app_java.kakeibo_app_java.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kakeibo_app_java.kakeibo_app_java.entity.User;
import kakeibo_app_java.kakeibo_app_java.exception.BadRequestException;
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

    @Override
    public User getUserById(Long id){
        User user = userRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("ユーザが見つかりません"));
        return user;
    }

    @Override
    public User editProfile(Long id,String name,String memo){
        User user = userRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("ユーザが見つかりません"));

        if(name != null && !name.isBlank()){
            user.setName(name);
        }
        if(memo != null && !memo.isBlank()){
            user.setMemo(memo);
        }

        return userRepository.save(user);

    }
    @Override
    public User changePassword(Long id,String password,String newPassword){
        User user = userRepository.findById(id)
                    .orElseThrow(()-> new BadRequestException("ユーザが見つかりません"));
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new BadRequestException("パスワードが正しくありません");
        }
        if(newPassword == null || newPassword.trim().isEmpty()){
            throw new BadRequestException("値を入力してください");
        }
        user.setPassword(passwordEncoder.encode(newPassword));

        return userRepository.save(user);
    }

     
}