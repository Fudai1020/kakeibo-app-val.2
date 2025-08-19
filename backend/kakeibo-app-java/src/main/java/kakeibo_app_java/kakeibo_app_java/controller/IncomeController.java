package kakeibo_app_java.kakeibo_app_java.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kakeibo_app_java.kakeibo_app_java.dto.IncomeRequest;
import kakeibo_app_java.kakeibo_app_java.dto.IncomeResponse;
import kakeibo_app_java.kakeibo_app_java.entity.Income;
import kakeibo_app_java.kakeibo_app_java.service.IncomeService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/incomes")
public class IncomeController {
    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService){
        this.incomeService = incomeService;
    }

    @PostMapping("/save")
    public IncomeResponse saveIncome(@RequestBody IncomeRequest incomeRequest) {
        Income income = incomeService.saveIncome(incomeRequest);
        return IncomeResponse.builder()
                .id(income.getId())
                .amount(income.getAmount())
                .name(income.getName())
                .memo(income.getMemo())
                .isPrivate(income.getIsPrivate())
                .incomeDate(income.getIncomeDate())
                .userId(income.getUser().getId())
                .incomeCategoryId(income.getIncomeCategory() != null ? income.getIncomeCategory().getId():null)
                .incomeCategoryName(income.getIncomeCategory() != null ? income.getIncomeCategory().getCategoryName():null)
                .build();
    }
    
}
