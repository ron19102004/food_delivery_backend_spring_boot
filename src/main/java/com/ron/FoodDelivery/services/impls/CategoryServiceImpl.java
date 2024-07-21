package com.ron.FoodDelivery.services.impls;

import com.ron.FoodDelivery.aws.AwsConfiguration;
import com.ron.FoodDelivery.aws.AwsS3Service;
import com.ron.FoodDelivery.entities.category.CategoryEntity;
import com.ron.FoodDelivery.entities.category.dto.RequestCreateCategoryDto;
import com.ron.FoodDelivery.exceptions.EntityNotFoundException;
import com.ron.FoodDelivery.repositories.CategoryRepository;
import com.ron.FoodDelivery.services.CategoryService;
import com.ron.FoodDelivery.utils.Constant;
import com.ron.FoodDelivery.utils.LogUtil;
import com.ron.FoodDelivery.utils.RegexValid;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AwsS3Service awsS3Service;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private RegexValid regexValid;
    private final ExecutorService executorService = Executors.newFixedThreadPool(6);

    @Override
    public CategoryEntity create(RequestCreateCategoryDto requestCreateCategoryDto) {
        CategoryEntity category = CategoryEntity.builder()
                .name(requestCreateCategoryDto.name())
                .deleted(false)
                .image(Constant.CATEGORY_DEFAULT_URL)
                .build();
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public CategoryEntity updateImage(Long categoryId, MultipartFile file) {
        CategoryEntity category = categoryRepository.findByIdAndDeleted(categoryId,false);
        if (category == null) throw new EntityNotFoundException("Category not found to update");
        String urlOld = category.getImage();
        String urlNew = awsS3Service.upload(file, AwsConfiguration.CATEGORY_FOLDER);
        category.setImage(urlNew);
        entityManager.merge(category);
        if (regexValid.isAwsS3Url(urlOld)) {
            executorService.submit(() -> {
                try {
                    awsS3Service.delete(urlOld, AwsConfiguration.CATEGORY_FOLDER);
                } catch (Exception e) {
                    LogUtil.log(CategoryServiceImpl.class, e, LogUtil.Status.ERROR);
                }
            });
        }
        return category;
    }
    @Transactional
    @Override
    public void remove(Long categoryId) {
        CategoryEntity category = categoryRepository.findByIdAndDeleted(categoryId,false);
        if (category == null) throw new EntityNotFoundException("Category not found to remove");
        category.setDeleted(true);
        entityManager.merge(category);
    }
}
