package com.ron.FoodDelivery.services.impls;

import com.ron.FoodDelivery.auth.dto.RequestRegisterDto;
import com.ron.FoodDelivery.entities.user.UserEntity;
import com.ron.FoodDelivery.entities.user.UserRole;
import com.ron.FoodDelivery.entities.user.dto.ResponseTotalUserDto;
import com.ron.FoodDelivery.exceptions.EntityNotFoundException;
import com.ron.FoodDelivery.repositories.DeliverRepository;
import com.ron.FoodDelivery.repositories.SellerRepository;
import com.ron.FoodDelivery.repositories.UserRepository;
import com.ron.FoodDelivery.services.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Value("${constant.images.default.avatar}")
    private String AVATAR_DEFAULT_URL;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private DeliverRepository deliverRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsernameAndIsLocked(username, false)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public UserEntity save(UserRole role, RequestRegisterDto requestRegisterDto) {
        UserEntity user = userRepository.findByUsernameAndIsLocked(requestRegisterDto.username(), false).orElse(null);
        if (user != null) return null;
        return userRepository.save(UserEntity.builder()
                .first_name(requestRegisterDto.first_name())
                .phone_number(requestRegisterDto.phone_number())
                .last_name(requestRegisterDto.last_name())
                .password(passwordEncoder.encode(requestRegisterDto.password()))
                .username(requestRegisterDto.username())
                .enabled_two_factor_auth(false)
                .email(requestRegisterDto.email())
                .role(role)
                .is_locked(false)
                .avatar(AVATAR_DEFAULT_URL)
                .build());
    }
    @Transactional
    @Override
    public void changeRole(Long userId, UserRole role) {
        UserEntity user = userRepository.findByIdAndIsLocked(userId,false);
        user.setRole(role);
        entityManager.merge(user);
    }
    @Transactional
    @Override
    public void resetRole(Long userId) {
        UserEntity user = userRepository.findByIdAndIsLocked(userId,false);
        user.setRole(UserRole.USER);
        entityManager.merge(user);
    }

    @Override
    public ResponseTotalUserDto totalsUser() {
        Long totalsDeliver = deliverRepository.totalsDeliver();
        Long totalsSeller = sellerRepository.totalsSeller();
        Long totalsUser = userRepository.totalsUsers();
        return new ResponseTotalUserDto(totalsUser,totalsDeliver,totalsSeller);
    }
}
