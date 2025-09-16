package kakeibo_app_java.kakeibo_app_java.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kakeibo_app_java.kakeibo_app_java.entity.ShareCode;

public interface ShareCodeRepository extends JpaRepository<ShareCode,Long>{

    Optional<ShareCode> findByCode(String code);
    
}