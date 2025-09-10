package kakeibo_app_java.kakeibo_app_java.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavingMonthlySummary {
    private Long id;
    private String name;
    private BigDecimal amount;
}