package kakeibo_app_java.kakeibo_app_java.service;

import java.math.BigDecimal;
import java.util.List;

import kakeibo_app_java.kakeibo_app_java.dto.PaymentRequest;
import kakeibo_app_java.kakeibo_app_java.dto.PaymentSummary;
import kakeibo_app_java.kakeibo_app_java.entity.Payment;

public interface PaymentService {
    Payment savePayment(PaymentRequest paymentRequest);
    List<String> findByUserAndPaymentCategoryName(Long id);
    PaymentSummary getMonthlyPaymentSummary(Long id,int year,int month);
    List<Payment> findMonthlyPayments(Long id,int year,int month);
    BigDecimal getPaymentSum(Long id,int year,int month);
}
