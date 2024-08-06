package com.ron.FoodDelivery.services.impls;

import com.ron.FoodDelivery.entities.deliver.DeliverEntity;
import com.ron.FoodDelivery.entities.order.OrderEntity;
import com.ron.FoodDelivery.entities.order.OrderStatus;
import com.ron.FoodDelivery.entities.process.ProcessEntity;
import com.ron.FoodDelivery.entities.process.ProcessStatus;
import com.ron.FoodDelivery.entities.process.dto.RequestCreateProcessDto;
import com.ron.FoodDelivery.exceptions.EntityNotFoundException;
import com.ron.FoodDelivery.exceptions.ServiceException;
import com.ron.FoodDelivery.repositories.DeliverRepository;
import com.ron.FoodDelivery.repositories.OrderRepository;
import com.ron.FoodDelivery.repositories.ProcessRepository;
import com.ron.FoodDelivery.services.ProcessService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProcessServiceImpl implements ProcessService {
    @Autowired
    private ProcessRepository processRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DeliverRepository deliverRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProcessEntity> getAllByOrderId(Long orderId) {
        return processRepository.findAllByOrderId(orderId);
    }
    @Override
    public ProcessEntity create(OrderEntity order, String note, ProcessStatus processStatus) {
        ProcessEntity processEntityCheck = processRepository.findByOrderIdAndStatus(order.getId(), processStatus);
        if (processEntityCheck != null)
            throw new ServiceException("Process is exist.", HttpStatus.BAD_REQUEST);
        ProcessEntity processEntity = ProcessEntity.builder()
                .note(note)
                .order(order)
                .status(processStatus)
                .build();

        return processRepository.save(processEntity);
    }
    @Transactional
    @Override
    public ProcessEntity create(String username, Long order_id, RequestCreateProcessDto requestCreateProcessDto) {
        OrderEntity orderEntity = orderRepository.findByIdAndStatus(order_id, OrderStatus.HANDLING);
        if (orderEntity == null)
            throw new ServiceException("Order is terminal.", HttpStatus.BAD_REQUEST);
        if (orderEntity.getDeliver() == null)
            throw new EntityNotFoundException("Deliver is missing");
        DeliverEntity deliver = deliverRepository.findByUsername(username, true);
        if (deliver == null)
            throw new EntityNotFoundException("Deliver not found");
        if (!Objects.equals(deliver.getId(), orderEntity.getDeliver().getId()))
            throw new ServiceException("Not permission", HttpStatus.FORBIDDEN);
        if (requestCreateProcessDto.status().equals(ProcessStatus.FINISHED)){
            orderEntity.setStatus(OrderStatus.FINISHED);
            entityManager.merge(orderEntity);
        }
        return create(orderEntity, requestCreateProcessDto.note(), requestCreateProcessDto.status());
    }
}
