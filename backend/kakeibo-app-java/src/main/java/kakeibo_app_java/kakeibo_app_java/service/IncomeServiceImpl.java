package kakeibo_app_java.kakeibo_app_java.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kakeibo_app_java.kakeibo_app_java.dto.IncomeRequest;
import kakeibo_app_java.kakeibo_app_java.entity.Income;
import kakeibo_app_java.kakeibo_app_java.entity.IncomeCategory;
import kakeibo_app_java.kakeibo_app_java.entity.User;
import kakeibo_app_java.kakeibo_app_java.exception.BadRequestException;
import kakeibo_app_java.kakeibo_app_java.repository.IncomeCategoryRepository;
import kakeibo_app_java.kakeibo_app_java.repository.IncomeRepository;
import kakeibo_app_java.kakeibo_app_java.repository.UserRepository;

@Service
@Transactional
public class IncomeServiceImpl implements IncomeService{
    private final IncomeRepository incomeRepository;
    private final IncomeCategoryRepository incomeCategoryRepository;
    private final UserRepository userRepository;

    public IncomeServiceImpl(IncomeRepository incomeRepository,IncomeCategoryRepository incomeCategoryRepository,UserRepository userRepository){
        this.incomeRepository = incomeRepository;
        this.incomeCategoryRepository = incomeCategoryRepository;
        this.userRepository = userRepository;
    }
    @Override
    public Income saveIncome(IncomeRequest dto){
        IncomeCategory category =   null;
                User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new BadRequestException("ユーザが見つかりません"));
        if(dto.getIncomeCategoryId() != null){
            category = incomeCategoryRepository.findById(dto.getIncomeCategoryId())
                    .orElseThrow(()->new BadRequestException("カテゴリが見つかりません"));
        }else if(dto.getIncomeCategoryName() != null && !dto.getIncomeCategoryName().isBlank()){
                category = IncomeCategory.builder()
                        .categoryName(dto.getIncomeCategoryName())
                        .user(user)
                        .build(); 
                category = incomeCategoryRepository.save(category);
        }

        Income income = Income.builder()
            .name(dto.getName())
            .memo(dto.getMemo())
            .incomeDate(dto.getIncomeDate())
            .amount(dto.getAmount())
            .incomeCategory(category)
            .user(user)
            .createdAt(LocalDateTime.now())
            .build();

        return incomeRepository.save(income);
    }
}
