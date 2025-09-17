package kakeibo_app_java.kakeibo_app_java.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinRequest {
    private Long partnerId;
    private String code;
}
