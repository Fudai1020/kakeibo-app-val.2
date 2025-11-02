package kakeibo_app_java.kakeibo_app_java.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kakeibo_app_java.kakeibo_app_java.dto.CategoryRequest;
import kakeibo_app_java.kakeibo_app_java.dto.CategoryResponse;
import kakeibo_app_java.kakeibo_app_java.dto.PaymentRequest;
import kakeibo_app_java.kakeibo_app_java.dto.PaymentResponse;
import kakeibo_app_java.kakeibo_app_java.dto.PaymentSummary;
import kakeibo_app_java.kakeibo_app_java.entity.Payment;
import kakeibo_app_java.kakeibo_app_java.service.PaymentCategoryService;
import kakeibo_app_java.kakeibo_app_java.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@CrossOrigin(origins = "https://kakeibo-app-val-2.vercel.app",allowCredentials = "true")
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentCategoryService paymentCategoryService;
    
    public PaymentController(PaymentService paymentService,PaymentCategoryService paymentCategoryService){
        this.paymentService = paymentService;
        this.paymentCategoryService = paymentCategoryService;
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
    @GetMapping("/{id}/{year}/{month}/sum")
    public BigDecimal getPaymentSum(@PathVariable Long id,@PathVariable int year,@PathVariable int month) {
        return paymentService.getPaymentSum(id, year, month);
    }
    @GetMapping("/public/{partnerId}")
    public ResponseEntity<List<PaymentResponse>> getPublicPayments(@PathVariable Long partnerId,@RequestParam int year,@RequestParam int month) {
        List<PaymentResponse> responses = paymentService.getPublicPayments(partnerId, year, month);
        return ResponseEntity.ok(responses);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<PaymentResponse> updatePayments(@PathVariable Long id, @RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.updatePayments(id, request);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/updateCategory/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long categoryId,@RequestBody CategoryRequest request){
        CategoryResponse response = paymentCategoryService.updateCategory(categoryId, request);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id){
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/deleteCategory/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId){
        paymentCategoryService.deleteCateogry(categoryId);
        return ResponseEntity.noContent().build();
    }
}
