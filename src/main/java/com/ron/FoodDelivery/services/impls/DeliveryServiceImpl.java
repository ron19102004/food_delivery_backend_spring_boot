package com.ron.FoodDelivery.services.impls;

import com.ron.FoodDelivery.entities.deliver.DeliverEntity;
import com.ron.FoodDelivery.entities.deliver.dto.RequestUpdateInformationDeliveryDto;
import com.ron.FoodDelivery.entities.request_role_account.dto.RequestCreateRequestRoleAccDataDto;
import com.ron.FoodDelivery.entities.seller.SellerEntity;
import com.ron.FoodDelivery.entities.user.UserEntity;
import com.ron.FoodDelivery.exceptions.EntityNotFoundException;
import com.ron.FoodDelivery.exceptions.ServiceException;
import com.ron.FoodDelivery.repositories.DeliveryRepository;
import com.ron.FoodDelivery.services.DeliveryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    @Autowired
    private DeliveryRepository deliveryRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void set_enable_account(Long id,Boolean enable) {
        DeliverEntity deliverEntity = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Delivery account not found"));
        deliverEntity.setEnabled(enable);
        entityManager.merge(deliverEntity);
    }

    @Transactional
    @Override
    public void update_information(String username, RequestUpdateInformationDeliveryDto requestUpdateInformationDeliveryDto) {
        DeliverEntity deliver = deliveryRepository.findByUsername(username);
        if (deliver == null) throw new ServiceException("Deliver not found!", HttpStatus.NOT_FOUND);
        deliver.setPhone_number(requestUpdateInformationDeliveryDto.phone_number());
        deliver.setEmail(requestUpdateInformationDeliveryDto.email());
        deliver.setName(requestUpdateInformationDeliveryDto.name());
        entityManager.merge(deliver);
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
