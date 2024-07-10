package com.ron.FoodDelivery.services.impls;

import com.ron.FoodDelivery.entities.request_role_account.dto.RequestCreateRequestRoleAccDataDto;
import com.ron.FoodDelivery.entities.seller.SellerEntity;
import com.ron.FoodDelivery.entities.user.UserEntity;
import com.ron.FoodDelivery.exceptions.EntityNotFoundException;
import com.ron.FoodDelivery.repositories.SellerRepository;
import com.ron.FoodDelivery.services.SellerService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerRepository sellerRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void init_account(UserEntity user, RequestCreateRequestRoleAccDataDto requestCreateRequestRoleAccDataDto) {
        SellerEntity sellerEntity = SellerEntity.builder()
                .email(requestCreateRequestRoleAccDataDto.email())
                .phone_number(requestCreateRequestRoleAccDataDto.phone_number())
                .enabled(false)
                .name(requestCreateRequestRoleAccDataDto.name())
                .user(user)
                .build();
        sellerRepository.save(sellerEntity);
    }

    @Transactional
    @Override
    public void disable_account(Long id) {
        SellerEntity sellerEntity = sellerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Seller account not found"));
        sellerEntity.setEnabled(false);
        entityManager.merge(sellerEntity);
    }
}
