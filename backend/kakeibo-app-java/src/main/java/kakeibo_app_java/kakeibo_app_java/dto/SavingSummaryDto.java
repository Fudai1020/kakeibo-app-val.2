package kakeibo_app_java.kakeibo_app_java.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingSummaryDto {
    private Long id;
    private String name;
    private BigDecimal totalAmount;
}
