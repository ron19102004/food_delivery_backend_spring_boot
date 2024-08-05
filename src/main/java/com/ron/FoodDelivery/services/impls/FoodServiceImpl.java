package com.ron.FoodDelivery.services.impls;

import com.ron.FoodDelivery.aws.AwsConfiguration;
import com.ron.FoodDelivery.aws.AwsS3Service;
import com.ron.FoodDelivery.entities.category.CategoryEntity;
import com.ron.FoodDelivery.entities.comment.CommentEntity;
import com.ron.FoodDelivery.entities.food.FoodEntity;
import com.ron.FoodDelivery.entities.food.dto.RequestCreateFoodDto;
import com.ron.FoodDelivery.entities.food.dto.RequestUpdateFoodDto;
import com.ron.FoodDelivery.entities.food.dto.ResponseDetailsFoodDto;
import com.ron.FoodDelivery.entities.seller.SellerEntity;
import com.ron.FoodDelivery.exceptions.EntityNotFoundException;
import com.ron.FoodDelivery.exceptions.ServiceException;
import com.ron.FoodDelivery.repositories.CategoryRepository;
import com.ron.FoodDelivery.repositories.CommentRepository;
import com.ron.FoodDelivery.repositories.FoodRepository;
import com.ron.FoodDelivery.repositories.SellerRepository;
import com.ron.FoodDelivery.services.FoodService;
import com.ron.FoodDelivery.utils.RegexValid;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class FoodServiceImpl implements FoodService {
    @Value("${constant.images.default.food}")
    private String FOOD_POSTER_DEFAULT_URL;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private RegexValid regexValid;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public FoodEntity create(String usernameSeller, RequestCreateFoodDto dto) {
        //dto check
        StringBuilder errStr = new StringBuilder();
        if (dto.price() < 0) errStr.append("Price must larger than 0.");
        if (dto.category_id() < 0) errStr.append("Category id must larger than 0.");
        if (!errStr.isEmpty())
            throw new ServiceException(errStr.toString(), HttpStatus.BAD_REQUEST);
        SellerEntity seller = sellerRepository.findByUsernameAndEnabled(usernameSeller, true);
        if (seller == null)
            throw new EntityNotFoundException("Seller not found");
        CategoryEntity category = categoryRepository.findByIdAndDeleted(dto.category_id(), false);
        if (category == null)
            throw new EntityNotFoundException("Category not found with id: " + dto.category_id());
        FoodEntity food = FoodEntity.builder()
                .name(dto.name())
                .deleted(false)
                .price(dto.price())
                .description(dto.description())
                .category(category)
                .seller(seller)
                .poster(dto.poster_url())
                .sold(0L)
                .sale_off(0f)
                .sale_price(dto.price())
                .build();
        return foodRepository.save(food);
    }

    @Transactional
    @Override
    public FoodEntity update(String usernameSeller, Long food_id, RequestUpdateFoodDto dto) {
        //dto check
        StringBuilder errStr = new StringBuilder();
        if (dto.price() < 0) errStr.append("Price must larger than 0.");
        if (dto.category_id() < 0) errStr.append("Category id must larger than 0.");
        if (dto.sale_off() < 0 || dto.sale_off() > 100) errStr.append("Sale off must be between 0 and 100");
        if (!errStr.isEmpty())
            throw new ServiceException(errStr.toString(), HttpStatus.BAD_REQUEST);
        //permission check
        SellerEntity seller = sellerRepository.findByUsernameAndEnabled(usernameSeller, true);
        if (seller == null)
            throw new EntityNotFoundException("Seller not found");
        FoodEntity food = foodRepository.findByIdAndDeleted(food_id, false);
        if (food == null)
            throw new EntityNotFoundException("Food not found");
        if (!Objects.equals(food.getSeller().getId(), seller.getId()))
            throw new ServiceException("Seller is forbidden", HttpStatus.FORBIDDEN);
        //update
        if (!Objects.equals(food.getCategory().getId(), dto.category_id())) {
            CategoryEntity category = categoryRepository.findByIdAndDeleted(dto.category_id(), false);
            if (category == null)
                throw new EntityNotFoundException("Category not found with id: " + dto.category_id());
            food.setCategory(category);
        }
        food.setName(dto.name());
        food.setDescription(dto.description());
        food.setPrice(dto.price());
        food.setSale_off(dto.sale_off());
        food.setPoster(dto.poster_url());

        Double sale_price = food.getPrice() - (food.getPrice() * (food.getSale_off() / 100));
        food.setSale_price(sale_price);
        entityManager.merge(food);
        return food;
    }

    @Override
    public Page<FoodEntity> findByCategoryIdWithPage(Long category_id, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("id").descending());
        return foodRepository.findAllByCategoryId(category_id, false, pageable);
    }

    @Override
    public Page<FoodEntity> findBySellerIdWithPage(Long sellerId, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("id").descending());
        return foodRepository.findAllBySellerId(sellerId, false, pageable);
    }

    @Override
    public ResponseDetailsFoodDto getDetailsFood(Long food_id) {
        FoodEntity food = foodRepository.findByIdAndDeleted(food_id, false);
        List<CommentEntity> comments = commentRepository.findByFoodId(food_id);
        return new ResponseDetailsFoodDto(food, comments);
    }
}
