package kakeibo_app_java.kakeibo_app_java.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kakeibo_app_java.kakeibo_app_java.entity.Income;

public interface IncomeRepository extends JpaRepository<Income,Long>{
    @Query("SELECT (SUM(i.amount), 0) "+
            "FROM Income i " +
            "WHERE i.user.id = :userId " +
            "AND YEAR(i.incomeDate) = :year " +
            "AND MONTH(i.incomeDate) = :month ")
    BigDecimal getMonthlyIncomeSum(@Param("userId") Long id,@Param("year") int year,@Param("month") int month);

    @Query("SELECT i "+
            "FROM Income i " +
            "WHERE i.user.id = :userId " +
            "AND YEAR(i.incomeDate) = :year "+
            "AND MONTH(i.incomeDate) = :month ")
    List<Income> findMonthlyIncomes(@Param("userId") Long id,@Param("year") int year,@Param("month") int month);
}
