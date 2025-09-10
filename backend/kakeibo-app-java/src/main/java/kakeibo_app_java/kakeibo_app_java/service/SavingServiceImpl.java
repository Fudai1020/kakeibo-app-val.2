package kakeibo_app_java.kakeibo_app_java.service;


import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kakeibo_app_java.kakeibo_app_java.dto.Allocation;
import kakeibo_app_java.kakeibo_app_java.dto.SavingDto;
import kakeibo_app_java.kakeibo_app_java.dto.SavingMonthlySummary;
import kakeibo_app_java.kakeibo_app_java.dto.SavingRequest;
import kakeibo_app_java.kakeibo_app_java.dto.SavingSummaryDto;
import kakeibo_app_java.kakeibo_app_java.entity.Saving;
import kakeibo_app_java.kakeibo_app_java.entity.SavingAllocation;
import kakeibo_app_java.kakeibo_app_java.entity.User;
import kakeibo_app_java.kakeibo_app_java.exception.BadRequestException;
import kakeibo_app_java.kakeibo_app_java.repository.SavingAllocationRepository;
import kakeibo_app_java.kakeibo_app_java.repository.SavingRepository;
import kakeibo_app_java.kakeibo_app_java.repository.UserRepository;

@Service
@Transactional
public class SavingServiceImpl implements SavingService {
    private final SavingRepository savingRepository;
    private final SavingAllocationRepository savingAllocationRepository;
    private final UserRepository userRepository;

    public SavingServiceImpl(SavingRepository savingRepository,SavingAllocationRepository savingAllocationRepository,UserRepository userRepository){
        this.savingRepository = savingRepository;
        this.savingAllocationRepository = savingAllocationRepository;
        this.userRepository = userRepository;
    }
    @Override
    public void saveSaving(SavingRequest savingRequest){
        User user = userRepository.findById(savingRequest.getUserId())
            .orElseThrow(()-> new BadRequestException("ユーザ名が見つかりません"));
        for(SavingDto savingDto:savingRequest.getSavings()){
            Saving saving = savingRepository.findByUserAndName(user, savingDto.getName())
                .orElseGet(() -> savingRepository.save(
                    Saving.builder()
                        .name(savingDto.getName())
                        .user(user)
                        .isPrivate(savingRequest.isPrivate())
                        .createdAt(LocalDate.now())
                        .build())
                    );
            if(savingDto.getAllocations() == null || savingDto.getAllocations().isEmpty()){
                continue;
            }
            for(Allocation a :savingDto.getAllocations()){
                if(a.getSavingDate() == null){
                    throw new BadRequestException("savingDateは必須です");
                }
                if(a.getAllocationAmount() == null){
                    throw new BadRequestException("savingAmountは必須です");
                }
            LocalDate d = a.getSavingDate();
            YearMonth ym = YearMonth.from(d);
            LocalDate start = ym.atDay(1);
            LocalDate end = ym.atEndOfMonth();
            SavingAllocation allocation = savingAllocationRepository
                .findBySavingIdAndMonth(saving.getId(), start, end)
                .orElseGet(() -> SavingAllocation.builder()
                    .saving(saving)
                    .allocationDate(d)
                    .createdAt(LocalDate.now())
                    .build()
                );
                allocation.setAmount(a.getAllocationAmount());
                savingAllocationRepository.save(allocation);
            }
        }
    }
    @Override
    public List<SavingMonthlySummary> getMonthlySavingSummaries(Long userId,int year,int month){
        userRepository.findById(userId)
            .orElseThrow(()-> new BadRequestException("ユーザが見つかりません"));

        YearMonth ym = YearMonth.of(year, month);
        LocalDate starDate = ym.atDay(1);
        LocalDate day = ym.atEndOfMonth();

        return savingRepository.findMonthlySummariesByUserAndDateRange(userId, starDate, day);
    }
    @Override
    public List<SavingSummaryDto> getCumulativeSummaries(Long userId,LocalDate endDate){
        userRepository.findById(userId)
            .orElseThrow(()-> new BadRequestException("ユーザが見つかりません"));
        return savingRepository.findCumulativeSummaries(userId, endDate);
    }
}
