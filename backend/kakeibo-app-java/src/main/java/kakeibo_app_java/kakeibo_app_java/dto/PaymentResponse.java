package kakeibo_app_java.kakeibo_app_java.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private Long id;
    private String name;
    private String memo;
    @JsonFormat(pattern =  "yyyy-MM-dd",timezone = "Asia/Tokyo")
    private LocalDate paymentDate;
    private boolean isPrivate;
    private BigDecimal amount;
    private Long paymentCategoryId;
    private String paymentCategoryName;
    private Long userId;
}
