package kakeibo_app_java.kakeibo_app_java.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kakeibo_app_java.kakeibo_app_java.dto.CategoryRequest;
import kakeibo_app_java.kakeibo_app_java.dto.CategoryResponse;
import kakeibo_app_java.kakeibo_app_java.entity.IncomeCategory;
import kakeibo_app_java.kakeibo_app_java.exception.BadRequestException;
import kakeibo_app_java.kakeibo_app_java.repository.IncomeCategoryRepository;
import kakeibo_app_java.kakeibo_app_java.repository.IncomeRepository;
@Service
@Transactional
public class IncomeCategoryServiceImpl implements IncomeCategoryService{
    private final IncomeCategoryRepository incomeCategoryRepository;
    private final IncomeRepository incomeRepository;

    public IncomeCategoryServiceImpl(IncomeCategoryRepository incomeCategoryRepository,IncomeRepository incomeRepository){
        this.incomeCategoryRepository = incomeCategoryRepository;
        this.incomeRepository = incomeRepository;
    }
    @Override
    public CategoryResponse updateCategory(Long id,CategoryRequest request){
        IncomeCategory incomeCategory = incomeCategoryRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("カテゴリが見つかりません"));
        if(request.getName() != null && !request.getName().isBlank()){
            incomeCategory.setCategoryName(request.getName());
        }
        IncomeCategory updated = incomeCategoryRepository.save(incomeCategory);
        return new CategoryResponse(updated.getId(),updated.getCategoryName());
    }
    @Override
    public void deleteCategory(Long categoryId){
        if(!incomeCategoryRepository.existsById(categoryId)){
            throw new BadRequestException("カテゴリが見つかりません");
        }
        incomeRepository.deleteAllByCategoryId(categoryId);
        incomeCategoryRepository.deleteById(categoryId);
    }
}
