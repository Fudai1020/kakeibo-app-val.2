package kakeibo_app_java.kakeibo_app_java.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kakeibo_app_java.kakeibo_app_java.entity.Shared;

public interface SharedRepository extends JpaRepository<Shared,Long>{
    Optional<Shared> findByOwnerIdOrPartnerId(Long ownerId,Long partnerId);
}