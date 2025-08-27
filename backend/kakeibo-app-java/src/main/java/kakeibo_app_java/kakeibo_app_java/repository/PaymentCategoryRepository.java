package kakeibo_app_java.kakeibo_app_java.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kakeibo_app_java.kakeibo_app_java.entity.PaymentCategory;
import kakeibo_app_java.kakeibo_app_java.entity.User;

public interface PaymentCategoryRepository extends JpaRepository<PaymentCategory,Long>{
 Optional<PaymentCategory> findByUserAndPaymentCategoryName(User user,String categoryName);
 List<PaymentCategory> findByUser(User user);   
}
