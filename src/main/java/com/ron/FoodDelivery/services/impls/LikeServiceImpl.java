package com.ron.FoodDelivery.services.impls;

import com.ron.FoodDelivery.entities.food.FoodEntity;
import com.ron.FoodDelivery.entities.like.LikeEntity;
import com.ron.FoodDelivery.entities.user.UserEntity;
import com.ron.FoodDelivery.exceptions.EntityNotFoundException;
import com.ron.FoodDelivery.exceptions.ServiceException;
import com.ron.FoodDelivery.repositories.FoodRepository;
import com.ron.FoodDelivery.repositories.LikeRepository;
import com.ron.FoodDelivery.repositories.UserRepository;
import com.ron.FoodDelivery.services.LikeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FoodRepository foodRepository;

    @Override
    public void like(String username, Long foodId) {
        UserEntity user = userRepository.findByUsernameAndIsLocked(username, false)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        FoodEntity food = foodRepository.findByIdAndDeleted(foodId,false);
        LikeEntity like = LikeEntity.builder()
                .food(food)
                .user(user)
                .build();
        likeRepository.save(like);
    }
    @Transactional
    @Override
    public void unlike(String username, Long foodId) {
        LikeEntity like = likeRepository.findByUsernameAndFoodId(username,foodId);
        if (like == null)
            throw new ServiceException("Like not found", HttpStatus.BAD_REQUEST);
        likeRepository.delete(like);
    }

    @Override
    public int totalLike(Long foodId) {
        return likeRepository.totalLikeByFoodId(foodId);
    }
}
