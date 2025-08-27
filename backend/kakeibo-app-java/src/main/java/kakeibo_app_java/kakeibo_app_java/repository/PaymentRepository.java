package kakeibo_app_java.kakeibo_app_java.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kakeibo_app_java.kakeibo_app_java.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Long>{
    @Query("SELECT COALESCE(SUM(p.amount),0) "+
            "FROM Payment p "+
            "WHERE p.user.id = :userId "+
            "AND YEAR(p.paymentDate) = :year "+
            "AND MONTH(p.paymentDate) = :month ")
    BigDecimal getMonthlyPaymentSum(@Param("userId") Long id,@Param("year") int year,@Param("month") int month);
    @Query("SELECT new map(p.paymentCategory.paymentCategoryName as category, SUM(p.amount) as total)" +
            "FROM Payment p " +
            "WHERE p.user.id = :userId " +
            "AND YEAR(p.paymentDate) = :year " +
            "AND MONTH(p.paymentDate) =:month " +
            "GROUP BY p.paymentCategory.paymentCategoryName ")
    List<Map<String,Object>> findMonthlyPaymentTotalsByCategory(@Param("userId") Long id,@Param("year") int year,@Param("month") int month);
    @Query("SELECT p " +
            "FROM Payment p " +
            "WHERE p.user.id = :userId " +
            "AND YEAR(p.paymentDate) = :year  "+
            "AND MONTH(p.paymentDate) = :month ")
    List<Payment> findMonthlyPayments(@Param("userId")Long id,@Param("year") int year,@Param("month")int month);
}
