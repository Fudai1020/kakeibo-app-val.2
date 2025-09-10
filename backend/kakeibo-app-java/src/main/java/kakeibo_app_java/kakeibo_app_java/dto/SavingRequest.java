package kakeibo_app_java.kakeibo_app_java.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavingRequest {
    private boolean isPrivate;
    private Long userId;
    private List<SavingDto> savings;
}

