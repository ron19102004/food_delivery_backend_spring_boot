package com.ron.FoodDelivery.services.impls;

import com.ron.FoodDelivery.entities.deliver.DeliverEntity;
import com.ron.FoodDelivery.entities.delivery_charges.DeliveryChargesEntity;
import com.ron.FoodDelivery.entities.food.FoodEntity;
import com.ron.FoodDelivery.entities.order.OrderEntity;
import com.ron.FoodDelivery.entities.order.OrderStatus;
import com.ron.FoodDelivery.entities.order.dto.RequestCancelOrderDto;
import com.ron.FoodDelivery.entities.order.dto.RequestCreateOrderDto;
import com.ron.FoodDelivery.entities.order.dto.ResponseAllOrderSellerDto;
import com.ron.FoodDelivery.entities.order.dto.ResponseInfoOrderDto;
import com.ron.FoodDelivery.entities.process.ProcessEntity;
import com.ron.FoodDelivery.entities.process.ProcessStatus;
import com.ron.FoodDelivery.entities.seller.SellerEntity;
import com.ron.FoodDelivery.entities.user.UserEntity;
import com.ron.FoodDelivery.entities.user.UserRole;
import com.ron.FoodDelivery.entities.voucher.VoucherEntity;
import com.ron.FoodDelivery.exceptions.EntityNotFoundException;
import com.ron.FoodDelivery.exceptions.ServiceException;
import com.ron.FoodDelivery.repositories.*;
import com.ron.FoodDelivery.services.OrderService;
import com.ron.FoodDelivery.services.ProcessService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private DeliverRepository deliverRepository;
    @Autowired
    private DeliveryChargesRepository deliveryChargesRepository;
    @Autowired
    private ProcessService processService;
    @Autowired
    private SellerRepository sellerRepository;

    public double getDistanceBetweenPointsNew(double latitude1, double longitude1, double latitude2, double longitude2) {
        double theta = longitude1 - longitude2;
        double distance = 60 * 1.1515 * (180 / Math.PI) * Math.acos(
                Math.sin(latitude1 * (Math.PI / 180)) * Math.sin(latitude2 * (Math.PI / 180)) +
                        Math.cos(latitude1 * (Math.PI / 180)) * Math.cos(latitude2 * (Math.PI / 180)) * Math.cos(theta * (Math.PI / 180))
        );
        return Math.round((distance * 1.609344) * 100.0) / 100.0;
    }

    @Transactional
    @Override
    public OrderEntity create(Long foodId,
                              String username,
                              RequestCreateOrderDto dto) {
        if (dto.quantity() < 0) throw new ServiceException("Quantity must larger than 0", HttpStatus.BAD_REQUEST);
        FoodEntity food = foodRepository.findByIdAndDeleted(foodId, false);
        if (food == null)
            throw new EntityNotFoundException("Food not found");
        UserEntity user = userRepository.findByUsernameAndIsLocked(username, false)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        double total = dto.quantity() * food.getSale_price();
        VoucherEntity voucher = null;
        if (dto.code_voucher() != null && !dto.code_voucher().isEmpty()) {
            voucher = voucherRepository.findByCodeAndHidden(dto.code_voucher(), false);
            if (voucher == null)
                throw new EntityNotFoundException("Voucher invalid!");
            if(!Objects.equals(voucher.getCategory().getId(), food.getCategory().getId()))
                throw new ServiceException("Voucher invalid!",HttpStatus.BAD_REQUEST);
            if (voucher.getExpired_at().before(new Date()))
                throw new ServiceException("Voucher is expired", HttpStatus.BAD_REQUEST);
            if (voucher.getQuantity_current() == 0)
                throw new ServiceException("Voucher is limited", HttpStatus.BAD_REQUEST);
            total = total - ((voucher.getPercent() / 100) * total);
            voucher.setQuantity_current(voucher.getQuantity_current() - 1);
            entityManager.merge(voucher);
        }
        String code_order = "#" + foodId + "@" + System.currentTimeMillis() + "&" + total;
        OrderEntity order = OrderEntity.builder()
                .user(user)
                .food(food)
                .voucher(voucher)
                .address(dto.address())
                .note(dto.note())
                .quantity(dto.quantity())
                .latitude_receive(dto.latitude_receive())
                .longitude_receive(dto.longitude_receive())
                .latitude_send(food.getSeller().getLatitude())
                .longitude_send(food.getSeller().getLongitude())
                .status(OrderStatus.HANDLING)
                .total(total)
                .code_order(code_order)
                .build();
        double km = getDistanceBetweenPointsNew(
                order.getLatitude_receive(),
                order.getLongitude_receive(),
                order.getLatitude_send(),
                order.getLongitude_send());
        DeliveryChargesEntity deliveryCharges = deliveryChargesRepository.findByKm(km).orElse(null);
        order.setKilometer(km);
        order.setDelivery_charges(deliveryCharges);
        OrderEntity orderEntity = orderRepository.save(order);
        processService.create(orderEntity, dto.note(), ProcessStatus.ORDERED);
        return orderEntity;
    }

    @Transactional
    @Override
    public OrderEntity updateDeliver(Long orderId, String usernameDeliver) {
        OrderEntity order = orderRepository.findByIdAndStatus(orderId, OrderStatus.HANDLING);
        if (order == null)
            throw new EntityNotFoundException("Order not found");
        DeliverEntity deliver = deliverRepository.findByUsername(usernameDeliver, true);
        if (deliver == null)
            throw new EntityNotFoundException("Deliver not found");
        order.setDeliver(deliver);
        entityManager.merge(order);
        return order;
    }

    @Override
    public OrderEntity getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    @Override
    public ResponseInfoOrderDto getInfoOrder(Long order_id, String username) {
        UserEntity user = userRepository.findByUsernameAndIsLocked(username, false)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        OrderEntity orderEntity = this.getOrderById(order_id);
        if (user.getRole().equals(UserRole.USER) && !Objects.equals(user.getId(), orderEntity.getUser().getId()))
            throw new ServiceException("Not permission", HttpStatus.FORBIDDEN);
        if (user.getRole().equals(UserRole.DELIVER) && !Objects.equals(user.getId(), orderEntity.getDeliver().getId()))
            throw new ServiceException("Not permission", HttpStatus.FORBIDDEN);
        if (user.getRole().equals(UserRole.SELLER) && !Objects.equals(user.getId(), orderEntity.getFood().getSeller().getId()))
            throw new ServiceException("Not permission", HttpStatus.FORBIDDEN);
        List<ProcessEntity> processEntities = processService.getAllByOrderId(order_id);
        return new ResponseInfoOrderDto(orderEntity, processEntities);
    }

    @Transactional
    @Override
    public void cancelOrder(String username, RequestCancelOrderDto requestCancelOrderDto) {
        UserEntity user = userRepository.findByUsernameAndIsLocked(username, false)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        OrderEntity order = this.getOrderById(requestCancelOrderDto.order_id());
        if (user.getRole().equals(UserRole.USER) && !Objects.equals(user.getId(), order.getUser().getId()))
            throw new ServiceException("Not permission", HttpStatus.FORBIDDEN);
        if (user.getRole().equals(UserRole.SELLER) && !Objects.equals(user.getId(), order.getFood().getSeller().getId()))
            throw new ServiceException("Not permission", HttpStatus.FORBIDDEN);
        order.setStatus(OrderStatus.CANCELED);
        order.setReason_cancel(requestCancelOrderDto.reason_cancel());
        entityManager.merge(order);
    }

    @Override
    public ResponseAllOrderSellerDto getOrdersBySellerUsername(String username) {
        List<OrderEntity> handling = orderRepository.findAllBySellerUsernameWithStatus(OrderStatus.HANDLING,username);
        List<OrderEntity> canceled = orderRepository.findAllBySellerUsernameWithStatus(OrderStatus.CANCELED,username);
        List<OrderEntity> finished = orderRepository.findAllBySellerUsernameWithStatus(OrderStatus.FINISHED,username);
        return new ResponseAllOrderSellerDto(handling,canceled,finished);
    }

    @Override
    public List<OrderEntity> userOrdersByUsername(String username) {
        return orderRepository.findAllMyOrderByUsername(username,OrderStatus.HANDLING);
    }

    @Override
    public List<OrderEntity> getOrdersForDeliver(String location_code) {
        return orderRepository.findAllMyOrderByLocationCode(location_code,OrderStatus.HANDLING);
    }
}
