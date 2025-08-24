package kakeibo_app_java.kakeibo_app_java.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncomeResponse {
    private Long id;
    private String name;
    private String memo;
    @JsonFormat(pattern =  "yyyy-MM-dd",timezone = "Asia/Tokyo")
    private LocalDate incomeDate;
    private boolean isPrivate;
    private BigDecimal amount;
    private Long incomeCategoryId;
    private String incomeCategoryName;
    private Long userId;
}
