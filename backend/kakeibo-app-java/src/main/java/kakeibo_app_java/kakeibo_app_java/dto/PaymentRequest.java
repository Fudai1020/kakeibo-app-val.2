package kakeibo_app_java.kakeibo_app_java.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {
    private String name;
    private String memo;
    private LocalDate paymentDate;
    private boolean isPrivate;
    private BigDecimal amount;
    private Long paymentCategoryId;
    private String paymentCategoryName;
    private LocalDate createdAt;
    private Long userId;
}
