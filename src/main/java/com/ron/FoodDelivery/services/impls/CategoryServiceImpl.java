package com.ron.FoodDelivery.services.impls;

import com.ron.FoodDelivery.aws.AwsConfiguration;
import com.ron.FoodDelivery.aws.AwsS3Service;
import com.ron.FoodDelivery.entities.category.CategoryEntity;
import com.ron.FoodDelivery.entities.category.dto.RequestCreateCategoryDto;
import com.ron.FoodDelivery.entities.category.dto.RequestUpdateCategoryDto;
import com.ron.FoodDelivery.exceptions.EntityNotFoundException;
import com.ron.FoodDelivery.repositories.CategoryRepository;
import com.ron.FoodDelivery.services.CategoryService;
import com.ron.FoodDelivery.utils.ConsoleUtil;
import com.ron.FoodDelivery.utils.ImageUrlDriveUtil;
import com.ron.FoodDelivery.utils.RegexValid;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Value("${constant.images.default.category}")
    private String CATEGORY_DEFAULT_URL;
    @Autowired
    private CategoryRepository categoryRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private RegexValid regexValid;
    private final ExecutorService executorService = Executors.newFixedThreadPool(6);
    private final ConsoleUtil log = ConsoleUtil.newConsoleLog(this.getClass());


    @Override
    public CategoryEntity create(RequestCreateCategoryDto requestCreateCategoryDto) {
        CategoryEntity category = CategoryEntity.builder()
                .name(requestCreateCategoryDto.name())
                .deleted(false)
                .image(CATEGORY_DEFAULT_URL)
                .build();
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public CategoryEntity updateImage(Long categoryId, RequestUpdateCategoryDto dto) {
        CategoryEntity category = categoryRepository.findByIdAndDeleted(categoryId, false);
        if (category == null) throw new EntityNotFoundException("Category not found to update");
        String urlNew = ImageUrlDriveUtil.toUrlCanRead(dto.image_url_drive());
        category.setImage(urlNew);
        category.setName(dto.name());
        entityManager.merge(category);
        return category;
    }

    @Transactional
    @Override
    public void remove(Long categoryId) {
        CategoryEntity category = categoryRepository.findByIdAndDeleted(categoryId, false);
        if (category == null) throw new EntityNotFoundException("Category not found to remove");
        category.setDeleted(true);
        entityManager.merge(category);
    }

    @Override
    public List<CategoryEntity> getAll() {
        return categoryRepository.findAllByDeleted(false);
    }
}
