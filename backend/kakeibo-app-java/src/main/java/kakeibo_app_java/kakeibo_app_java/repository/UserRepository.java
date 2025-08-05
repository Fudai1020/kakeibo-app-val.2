package kakeibo_app_java.kakeibo_app_java.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kakeibo_app_java.kakeibo_app_java.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User> findByEmail(String email);
    
}
