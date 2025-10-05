package kakeibo_app_java.kakeibo_app_java.service;

import java.math.BigDecimal;
import java.util.List;

import kakeibo_app_java.kakeibo_app_java.dto.IncomeRequest;
import kakeibo_app_java.kakeibo_app_java.entity.Income;

public interface IncomeService {
    Income saveIncome(IncomeRequest incomeRequest);
    BigDecimal getMonthlyIncomeSum(Long id,int year,int month);
    List<String> findByUserAndCategoryName(Long id);
    List<Income> findMonthlyIncomes(Long id,int year,int month);
    BigDecimal getPublicIncome(Long partnerId,int year,int month);
}
