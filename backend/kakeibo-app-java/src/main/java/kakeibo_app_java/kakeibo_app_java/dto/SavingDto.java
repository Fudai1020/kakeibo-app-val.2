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
public class SavingDto{
    private String name;
    private List<Allocation> allocations;
} 

