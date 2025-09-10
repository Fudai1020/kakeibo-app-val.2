package kakeibo_app_java.kakeibo_app_java.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kakeibo_app_java.kakeibo_app_java.entity.SavingAllocation;

public interface SavingAllocationRepository extends JpaRepository<SavingAllocation,Long>{
    @Query("""
            SELECT a FROM SavingAllocation a
            WHERE a.saving.id = :savingId
             AND a.allocationDate BETWEEN :start AND :end 
            """)
    Optional<SavingAllocation> findBySavingIdAndMonth(@Param("savingId") Long id,@Param("start") LocalDate start,@Param("end") LocalDate end);
    @Query("""
            SELECT COALESCE(SUM(a.amount),0)
            FROM SavingAllocation a
            WHERE a.saving.id = :savingId
            AND allocationDate BETWEEN :start AND :end 
            """)
    BigDecimal findMonthlyAmountBySavingId(@Param("savingId") Long savingId,@Param("start") LocalDate start,@Param("end") LocalDate end);
}
