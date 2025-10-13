package kakeibo_app_java.kakeibo_app_java.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavingResponse {
    private Long id;
    private String name;
    private boolean isPrivate;
    private LocalDate createdAt;
    private List<AllocationResponse> allocations;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public static class AllocationResponse{
        private Long id;
        private BigDecimal allocationAmount;
        private LocalDate savingDate;
    }
}
