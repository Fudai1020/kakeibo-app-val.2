package kakeibo_app_java.kakeibo_app_java.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryResponse {
    private Long id;
    private String name;
}
