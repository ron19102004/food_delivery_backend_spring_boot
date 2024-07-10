package com.ron.FoodDelivery.services.impls;

import com.ron.FoodDelivery.entities.deliver.DeliverEntity;
import com.ron.FoodDelivery.entities.request_role_account.dto.RequestCreateRequestRoleAccDataDto;
import com.ron.FoodDelivery.entities.seller.SellerEntity;
import com.ron.FoodDelivery.entities.user.UserEntity;
import com.ron.FoodDelivery.exceptions.EntityNotFoundException;
import com.ron.FoodDelivery.repositories.DeliveryRepository;
import com.ron.FoodDelivery.services.DeliveryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    @Autowired
    private DeliveryRepository deliveryRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void disable_account(Long id) {
        DeliverEntity deliverEntity = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Delivery account not found"));
        deliverEntity.setEnabled(false);
        entityManager.merge(deliverEntity);
    }

    @Override
    public void init_account(UserEntity user, RequestCreateRequestRoleAccDataDto requestCreateRequestRoleAccDataDto) {
        DeliverEntity sellerEntity = DeliverEntity.builder()
                .email(requestCreateRequestRoleAccDataDto.email())
                .phone_number(requestCreateRequestRoleAccDataDto.phone_number())
                .enabled(false)
                .name(requestCreateRequestRoleAccDataDto.name())
                .user(user)
                .build();
        deliveryRepository.save(sellerEntity);
    }
}
