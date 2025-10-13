package kakeibo_app_java.kakeibo_app_java.service;


import java.time.LocalDate;
import java.util.List;

import kakeibo_app_java.kakeibo_app_java.dto.SavingMonthlySummary;
import kakeibo_app_java.kakeibo_app_java.dto.SavingRequest;
import kakeibo_app_java.kakeibo_app_java.dto.SavingSummaryDto;

public interface SavingService {
    void saveSaving(SavingRequest savingRequest);
    List<SavingMonthlySummary> getMonthlySavingSummaries(Long userId,int year,int month);
    List<SavingSummaryDto> getCumulativeSummaries(Long userId,LocalDate endDate);
    List<SavingSummaryDto> getPublicSummaries(Long userId,LocalDate endDate);
}

