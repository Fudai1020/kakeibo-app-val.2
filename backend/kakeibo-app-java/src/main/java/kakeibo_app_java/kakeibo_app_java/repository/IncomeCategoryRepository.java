package kakeibo_app_java.kakeibo_app_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kakeibo_app_java.kakeibo_app_java.entity.IncomeCategory;

public interface IncomeCategoryRepository extends JpaRepository<IncomeCategory,Long>{
    
}
