package kakeibo_app_java.kakeibo_app_java.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kakeibo_app_java.kakeibo_app_java.dto.CategoryRequest;
import kakeibo_app_java.kakeibo_app_java.dto.CategoryResponse;
import kakeibo_app_java.kakeibo_app_java.entity.PaymentCategory;
import kakeibo_app_java.kakeibo_app_java.exception.BadRequestException;
import kakeibo_app_java.kakeibo_app_java.repository.PaymentCategoryRepository;

@Service
@Transactional
public class PaymentCategoryServiceImpl implements PaymentCategoryService{
    private final PaymentCategoryRepository paymentCategoryRepository;
    public PaymentCategoryServiceImpl(PaymentCategoryRepository paymentCategoryRepository){
        this.paymentCategoryRepository = paymentCategoryRepository;
    }
    @Override
    public CategoryResponse updateCategory(Long id,CategoryRequest request){
        PaymentCategory paymentCategory = paymentCategoryRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("カテゴリが見つかりません"));
        if(request.getName() != null && request.getName().isBlank()){
            paymentCategory.setPaymentCategoryName(request.getName());
        }
        PaymentCategory update = paymentCategoryRepository.save(paymentCategory);
        return new CategoryResponse(update.getCategoryId(),update.getPaymentCategoryName());
    }
    @Override
    public void deleteCateogry(Long categoryId){
        if(!paymentCategoryRepository.existsById(categoryId)){
            throw new BadRequestException("カテゴリが見つかりません");
        }
        paymentCategoryRepository.deleteById(categoryId);
    }
}
