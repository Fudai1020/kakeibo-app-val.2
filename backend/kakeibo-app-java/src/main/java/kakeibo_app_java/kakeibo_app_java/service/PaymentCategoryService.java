package kakeibo_app_java.kakeibo_app_java.service;

import kakeibo_app_java.kakeibo_app_java.dto.CategoryRequest;
import kakeibo_app_java.kakeibo_app_java.dto.CategoryResponse;

public interface PaymentCategoryService {
    CategoryResponse updateCategory(Long id,CategoryRequest request);
    void deleteCateogry(Long categoryId);
}
