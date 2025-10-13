package kakeibo_app_java.kakeibo_app_java.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kakeibo_app_java.kakeibo_app_java.dto.SavingMonthlySummary;
import kakeibo_app_java.kakeibo_app_java.dto.SavingSummaryDto;
import kakeibo_app_java.kakeibo_app_java.entity.Saving;
import kakeibo_app_java.kakeibo_app_java.entity.User;


public interface SavingRepository extends JpaRepository<Saving,Long>{
    Optional<Saving> findByUserAndName(User user,String name);
    Optional<Saving> findByIdAndUserId(Long id,Long userId);
    @Query("""
            SELECT new kakeibo_app_java.kakeibo_app_java.dto.SavingMonthlySummary(
                s.id,s.name,COALESCE(SUM(a.amount),0)
            )
            FROM Saving s
            LEFT JOIN s.allocations a
                ON a.allocationDate BETWEEN :startDate AND :endDate
            WHERE s.user.id = :userId
            GROUP BY s.id,s.name
        """)
    List<SavingMonthlySummary> findMonthlySummariesByUserAndDateRange(
        @Param("userId") Long id,@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
    @Query("""
            SELECT new kakeibo_app_java.kakeibo_app_java.dto.SavingSummaryDto(
            s.id,
            s.name,
            COALESCE(SUM(a.amount),0)
            )
            FROM Saving s
            LEFT JOIN s.allocations a
            ON a.allocationDate <= :endDate
            WHERE s.user.id = :userId
            GROUP BY s.id,s.name
            """)
    List<SavingSummaryDto> findCumulativeSummaries(@Param("userId") Long userId,@Param("endDate") LocalDate endDate);
    @Query("""
            SELECT new kakeibo_app_java.kakeibo_app_java.dto.SavingSummaryDto(
            s.id,
            s.name,
            COALESCE(SUM(a.amount),0)
            )
            FROM Saving s
            LEFT JOIN s.allocations a
            ON a.allocationDate <= :endDate
            WHERE s.user.id = :userId
            AND s.isPrivate = false
            GROUP BY s.id,s.name
            """)
    List<SavingSummaryDto> findPublicSummaries(@Param("userId") Long userId,@Param("endDate") LocalDate endDate);
}