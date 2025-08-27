package kakeibo_app_java.kakeibo_app_java.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentSummary {
    private BigDecimal totalAmount;
    private List<Map<String,Object>> categoryTotals;
}