package com.ron.FoodDelivery.services.impls;

import com.ron.FoodDelivery.entities.request_role_account.RequestRoleAccount;
import com.ron.FoodDelivery.entities.request_role_account.RequestStatus;
import com.ron.FoodDelivery.entities.request_role_account.dto.RequestCreateRequestRoleAccDto;
import com.ron.FoodDelivery.entities.user.UserEntity;
import com.ron.FoodDelivery.entities.user.UserRole;
import com.ron.FoodDelivery.exceptions.EntityNotFoundException;
import com.ron.FoodDelivery.repositories.RequestRoleAccountRepository;
import com.ron.FoodDelivery.services.DeliveryService;
import com.ron.FoodDelivery.services.RequestRoleAccountService;
import com.ron.FoodDelivery.services.SellerService;
import com.ron.FoodDelivery.services.UserService;
import com.ron.FoodDelivery.utils.ResponseLayout;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RequestRoleAccountServiceImpl implements RequestRoleAccountService {
    @Autowired
    private RequestRoleAccountRepository requestRoleAccountRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private UserService userService;


    @Override
    public ResponseLayout<Object> createRequestRoleAcc(UserEntity user, RequestCreateRequestRoleAccDto requestCreateRequestRoleAccDto) {
        RequestRoleAccount _requestRoleAccount = RequestRoleAccount.builder()
                .user(user)
                .role(requestCreateRequestRoleAccDto.role())
                .is_accepted(false)
                .requested_at(new Date())
                .status(RequestStatus.WAITING)
                .build();
        RequestRoleAccount requestRoleAccount = requestRoleAccountRepository.save(_requestRoleAccount);
        switch (requestCreateRequestRoleAccDto.role()) {
            case DELIVER -> deliveryService.init_account(user, requestCreateRequestRoleAccDto.data());
            case SELLER -> sellerService.init_account(user, requestCreateRequestRoleAccDto.data());
        }
        return new ResponseLayout<>(requestRoleAccount, "Create successfully!", true);
    }

    @Override
    public void cancelAccount(UserEntity user) {
        switch (user.getRole()) {
            case SELLER -> sellerService.set_enable_account(user.getId(), false);
            case DELIVER -> deliveryService.set_enable_account(user.getId(), false);
        }
    }

    @Transactional
    @Override
    public void handle(Long requestRoleAccountId, boolean is_accepted) {
        RequestRoleAccount requestRoleAccount = requestRoleAccountRepository
                .findById(requestRoleAccountId).orElseThrow(() -> new EntityNotFoundException("Request role account not found"));
        switch (requestRoleAccount.getRole()) {
            case SELLER -> {
                userService.changeRole(requestRoleAccount.getUser().getId(), UserRole.SELLER);
                sellerService.set_enable_account(requestRoleAccount.getUser().getId(), true);
            }
            case DELIVER -> {
                userService.changeRole(requestRoleAccount.getUser().getId(), UserRole.DELIVER);
                deliveryService.set_enable_account(requestRoleAccount.getUser().getId(), true);
            }
        }
        requestRoleAccount.setHandled_at(new Date());
        requestRoleAccount.setIs_accepted(is_accepted);
        requestRoleAccount.setStatus(RequestStatus.HANDLED);
        entityManager.merge(requestRoleAccount);
    }
}
