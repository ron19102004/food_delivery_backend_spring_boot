package com.ron.FoodDelivery.services.impls;

import com.ron.FoodDelivery.entities.delivery_charges.DeliveryChargesEntity;
import com.ron.FoodDelivery.entities.delivery_charges.dto.RequestCreateDeliveryChargesDto;
import com.ron.FoodDelivery.exceptions.EntityNotFoundException;
import com.ron.FoodDelivery.repositories.DeliveryChargesRepository;
import com.ron.FoodDelivery.services.DeliveryChargesService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryChargesServiceImpl implements DeliveryChargesService {
    @Autowired
    private DeliveryChargesRepository deliveryChargesRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DeliveryChargesEntity create(RequestCreateDeliveryChargesDto requestCreateDeliveryChargesDto) {
        DeliveryChargesEntity deliveryCharges = DeliveryChargesEntity.builder()
                .price(requestCreateDeliveryChargesDto.price())
                .is_active(requestCreateDeliveryChargesDto.is_active())
                .kilometer(requestCreateDeliveryChargesDto.kilometer())
                .build();
        return deliveryChargesRepository.save(deliveryCharges);
    }

    @Transactional
    @Override
    public void changeActive(Long deliveryId) {
        DeliveryChargesEntity deliveryCharges = deliveryChargesRepository.findById(deliveryId)
                .orElseThrow(() -> new EntityNotFoundException("Delivery charges not found!"));
        deliveryCharges.setIs_active(!deliveryCharges.getIs_active());
        entityManager.merge(deliveryCharges);
    }

    @Override
    public List<DeliveryChargesEntity> getAll() {
        return deliveryChargesRepository.findAllByIsActive(true);
    }
}
