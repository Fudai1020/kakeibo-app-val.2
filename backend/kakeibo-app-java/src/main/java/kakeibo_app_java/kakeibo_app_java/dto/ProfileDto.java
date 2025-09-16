package kakeibo_app_java.kakeibo_app_java.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDto {
    private String name;
    private String email;
    private String memo;
    private LocalDate sharedAt;
}
