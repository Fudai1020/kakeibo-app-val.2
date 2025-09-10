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
public class  Allocation {
    private BigDecimal allocationAmount;
    private LocalDate savingDate;
}
    
