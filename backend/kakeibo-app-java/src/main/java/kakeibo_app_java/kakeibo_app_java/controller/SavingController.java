package kakeibo_app_java.kakeibo_app_java.controller;


import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kakeibo_app_java.kakeibo_app_java.dto.SavingMonthlySummary;
import kakeibo_app_java.kakeibo_app_java.dto.SavingRequest;
import kakeibo_app_java.kakeibo_app_java.dto.SavingSummaryDto;
import kakeibo_app_java.kakeibo_app_java.service.SavingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
@RestController
@RequestMapping("/api/savings")
public class SavingController {

    private final SavingService savingService;
    public SavingController(SavingService savingService){
        this.savingService = savingService;
    }
    @PostMapping("/save")
    public ResponseEntity<Void> saveSaving(@RequestBody SavingRequest savingRequest){
        savingService.saveSaving(savingRequest);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{userId}/{year}/{month}/summary")
    public List<SavingMonthlySummary> gMonthlySummary(@PathVariable Long userId ,@PathVariable int year,@PathVariable int month) {
        return savingService.getMonthlySavingSummaries(userId, year, month);
    }
    @GetMapping("/{userId}/{year}/{month}/cumulative")
    public List<SavingSummaryDto> getCumulativeSummaries(@PathVariable Long userId,@PathVariable int year,@PathVariable int month) {
        LocalDate endDate = YearMonth.of(year, month).atEndOfMonth();
        return savingService.getCumulativeSummaries(userId, endDate); 
    }
    
}
