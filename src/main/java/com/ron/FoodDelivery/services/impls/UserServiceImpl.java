package com.ron.FoodDelivery.services.impls;

import com.ron.FoodDelivery.auth.dto.RequestRegisterDto;
import com.ron.FoodDelivery.entities.user.UserEntity;
import com.ron.FoodDelivery.entities.user.UserRole;
import com.ron.FoodDelivery.exceptions.EntityNotFoundException;
import com.ron.FoodDelivery.repositories.UserRepository;
import com.ron.FoodDelivery.services.UserService;
import com.ron.FoodDelivery.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
                .avatar(Constant.AVATAR_DEFAULT_URL)
                .build());
    }
}
