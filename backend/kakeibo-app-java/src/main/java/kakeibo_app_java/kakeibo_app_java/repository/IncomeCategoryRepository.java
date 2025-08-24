package kakeibo_app_java.kakeibo_app_java.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kakeibo_app_java.kakeibo_app_java.entity.IncomeCategory;
import kakeibo_app_java.kakeibo_app_java.entity.User;

public interface IncomeCategoryRepository extends JpaRepository<IncomeCategory,Long>{
    Optional<IncomeCategory> findByUserAndCategoryName(User user,String categoryName);
    List<IncomeCategory> findByUser(User user);
}
