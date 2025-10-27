package kakeibo_app_java.kakeibo_app_java.service;

import kakeibo_app_java.kakeibo_app_java.dto.CategoryRequest;
import kakeibo_app_java.kakeibo_app_java.dto.CategoryResponse;

public interface IncomeCategoryService {
    CategoryResponse updateCategory(Long id,CategoryRequest request);
    void deleteCategory(Long categoryId);
}
