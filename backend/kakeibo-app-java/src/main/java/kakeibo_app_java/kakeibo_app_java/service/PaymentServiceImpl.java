package kakeibo_app_java.kakeibo_app_java.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kakeibo_app_java.kakeibo_app_java.dto.PaymentRequest;
import kakeibo_app_java.kakeibo_app_java.dto.PaymentSummary;
import kakeibo_app_java.kakeibo_app_java.entity.Payment;
import kakeibo_app_java.kakeibo_app_java.entity.PaymentCategory;
import kakeibo_app_java.kakeibo_app_java.entity.User;
import kakeibo_app_java.kakeibo_app_java.exception.BadRequestException;
import kakeibo_app_java.kakeibo_app_java.repository.PaymentCategoryRepository;
import kakeibo_app_java.kakeibo_app_java.repository.PaymentRepository;
import kakeibo_app_java.kakeibo_app_java.repository.UserRepository;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService{
    private final PaymentRepository paymentRepository;
    private final PaymentCategoryRepository paymentCategoryRepository;
    private final UserRepository userRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,PaymentCategoryRepository paymentCategoryRepository,UserRepository userRepository){
        this.paymentRepository = paymentRepository;
        this.paymentCategoryRepository = paymentCategoryRepository;
        this.userRepository = userRepository;
    }
    @Override
    public Payment savePayment(PaymentRequest dto){
        PaymentCategory category = null;
        User user = userRepository.findById(dto.getUserId())
            .orElseThrow(()-> new BadRequestException("ユーザが見つかりません"));
        if(dto.getPaymentCategoryId() != null){
            category =  paymentCategoryRepository.findById(dto.getPaymentCategoryId())
                .orElseThrow(()->new BadRequestException("カテゴリが見つかりません"));
        }else if(dto.getPaymentCategoryName() != null && !dto.getPaymentCategoryName().isBlank()){
            category = paymentCategoryRepository.findByUserAndPaymentCategoryName(user,dto.getPaymentCategoryName())
                .orElseGet(()->{
                    return paymentCategoryRepository.save(
                        PaymentCategory.builder()
                            .paymentCategoryName(dto.getPaymentCategoryName())
                            .user(user)
                            .build()
                    );
                });
        }
        Payment payment = Payment.builder()
            .name(dto.getName())
            .amount(dto.getAmount())
            .memo(dto.getMemo())
            .paymentDate(dto.getPaymentDate())
            .paymentCategory(category)
            .user(user)
            .createdAt(LocalDate.now())
            .build();       
        return paymentRepository.save(payment);
    }
    @Override
    public List<String> findByUserAndPaymentCategoryName(Long id){
        User user = userRepository.findById(id)
            .orElseThrow(()-> new BadRequestException("ユーザが見つかりません"));
        return paymentCategoryRepository.findByUser(user)
                .stream()
                .map(PaymentCategory::getPaymentCategoryName)
                .distinct()
                .toList();
    }
    @Override
    public PaymentSummary getMonthlyPaymentSummary(Long id,int year,int month){
        BigDecimal total = paymentRepository.getMonthlyPaymentSum(id, year, month);
        List<Map<String,Object>> categoryTotals = paymentRepository.findMonthlyPaymentTotalsByCategory(id, year, month);
        return new PaymentSummary(total,categoryTotals);
    }
    @Override
    public List<Payment> findMonthlyPayments(Long id,int year,int month){
        return paymentRepository.findMonthlyPayments(id, year, month);
    }
}
