package kakeibo_app_java.kakeibo_app_java.service;

import kakeibo_app_java.kakeibo_app_java.dto.IncomeRequest;
import kakeibo_app_java.kakeibo_app_java.entity.Income;

public interface IncomeService {
    Income saveIncome(IncomeRequest incomeRequest);
}
