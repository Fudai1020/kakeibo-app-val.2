package kakeibo_app_java.kakeibo_app_java.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kakeibo_app_java.kakeibo_app_java.dto.CategoryRequest;
import kakeibo_app_java.kakeibo_app_java.dto.CategoryResponse;
import kakeibo_app_java.kakeibo_app_java.dto.IncomeRequest;
import kakeibo_app_java.kakeibo_app_java.dto.IncomeResponse;
import kakeibo_app_java.kakeibo_app_java.entity.Income;
import kakeibo_app_java.kakeibo_app_java.service.IncomeCategoryService;
import kakeibo_app_java.kakeibo_app_java.service.IncomeService;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    private final IncomeService incomeService;
    private final IncomeCategoryService incomeCategoryService;

    public IncomeController(IncomeService incomeService,IncomeCategoryService incomeCategoryService){
        this.incomeService = incomeService;
        this.incomeCategoryService = incomeCategoryService;
    }

    @PostMapping("/save")
    public IncomeResponse saveIncome(@RequestBody IncomeRequest incomeRequest) {
        Income income = incomeService.saveIncome(incomeRequest);
        return IncomeResponse.builder()
                .id(income.getId())
                .amount(income.getAmount())
                .name(income.getName())
                .memo(income.getMemo())
                .isPrivate(income.isPrivate())
                .incomeDate(income.getIncomeDate())
                .userId(income.getUser().getId())
                .incomeCategoryId(income.getIncomeCategory() != null ? income.getIncomeCategory().getId():null)
                .incomeCategoryName(income.getIncomeCategory() != null ? income.getIncomeCategory().getCategoryName():null)
                .build();
    }
    @GetMapping("/{id}/{year}/{month}/sum")
    public BigDecimal getMonthlyIncomeSum(@PathVariable Long id,@PathVariable int year,@PathVariable int month) {
        return incomeService.getMonthlyIncomeSum(id, year, month);
    }
    
    @GetMapping("/user/{id}")
    public List<String> findByUserAndIncomeName(@PathVariable Long id){
        return incomeService.findByUserAndCategoryName(id);
    }
    @GetMapping("/{id}/{year}/{month}")
    public List<IncomeResponse> findMontlyIncomes(@PathVariable Long id,@PathVariable int year,@PathVariable int month) {
        List<Income>  incomes = incomeService.findMonthlyIncomes(id, year, month);
        return incomes.stream().map(income -> IncomeResponse.builder()
                .id(income.getId())
                .amount(income.getAmount())
                .name(income.getName())
                .memo(income.getMemo())
                .isPrivate(income.isPrivate())
                .userId(income.getUser().getId())
                .incomeDate(income.getIncomeDate())
                .incomeCategoryId(income.getIncomeCategory() != null ? income.getIncomeCategory().getId():null)
                .incomeCategoryName(income.getIncomeCategory() != null ? income.getIncomeCategory().getCategoryName():null)
                .build())
                .toList();

    }
    @GetMapping("/public/{partnerId}")
    public BigDecimal getPublicIncome(@PathVariable Long partnerId,@RequestParam int year,@RequestParam int month) {
        return incomeService.getPublicIncome(partnerId, year, month);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<IncomeResponse> updateIncome(@PathVariable Long id,@RequestBody IncomeRequest request) {
        IncomeResponse response = incomeService.updateIncome(id, request);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/updateCategory/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long categoryId,@RequestBody CategoryRequest request) {
        CategoryResponse response = incomeCategoryService.updateCategory(categoryId, request);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id){
        incomeService.deleteIncome(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/deleteCategory/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId){
        incomeCategoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
    
}
