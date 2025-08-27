package kakeibo_app_java.kakeibo_app_java.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import kakeibo_app_java.kakeibo_app_java.KakeiboAppJavaApplication;
import kakeibo_app_java.kakeibo_app_java.dto.PaymentRequest;
import kakeibo_app_java.kakeibo_app_java.dto.PaymentResponse;
import kakeibo_app_java.kakeibo_app_java.dto.PaymentSummary;
import kakeibo_app_java.kakeibo_app_java.entity.Payment;
import kakeibo_app_java.kakeibo_app_java.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    
    public PaymentController(PaymentService paymentService, KakeiboAppJavaApplication kakeiboAppJavaApplication){
        this.paymentService = paymentService;
    }

    @PostMapping("/save")
    public PaymentResponse savePayment(@RequestBody PaymentRequest paymentRequest){
        Payment payment = paymentService.savePayment(paymentRequest);
        return PaymentResponse.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .memo(payment.getMemo())
                .isPrivate(payment.isPrivate())
                .paymentDate(payment.getPaymentDate())
                .userId(payment.getUser().getId())
                .paymentCategoryId(payment.getPaymentCategory() != null ? payment.getPaymentCategory().getCategoryId():null)
                .paymentCategoryName(payment.getPaymentCategory() != null ? payment.getPaymentCategory().getPaymentCategoryName():null)
                .build();
    }
    @GetMapping("/user/{id}")
    public List<String> findByUserAndPaymentCategoryName(@PathVariable Long id) {
        return paymentService.findByUserAndPaymentCategoryName(id);
    }
    @GetMapping("/{id}/{year}/{month}/summary")
    public PaymentSummary getPaymentSummary(@PathVariable Long id,@PathVariable int year,@PathVariable int month) {
        return paymentService.getMonthlyPaymentSummary(id, year, month);
    }
    @GetMapping("/{id}/{year}/{month}")
    public List<PaymentResponse> findMonthlyPayments(@PathVariable Long id,@PathVariable int year,@PathVariable int month) {
        List<Payment> payments = paymentService.findMonthlyPayments(id, year, month);
        return  payments.stream().map(payment -> PaymentResponse.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .name(payment.getName())
                .memo(payment.getMemo())
                .isPrivate(payment.isPrivate())
                .userId(payment.getUser().getId())
                .paymentDate(payment.getPaymentDate())
                .paymentCategoryId(payment.getPaymentCategory() != null ? payment.getPaymentCategory().getCategoryId():null)
                .paymentCategoryName(payment.getPaymentCategory() != null ? payment.getPaymentCategory().getPaymentCategoryName():null)
                .build())
                .toList();
    }
    
    
}
