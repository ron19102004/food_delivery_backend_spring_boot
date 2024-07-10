package com.ron.FoodDelivery.services.impls;

import com.ron.FoodDelivery.auth.dto.ResponseLoginDto;
import com.ron.FoodDelivery.entities.otp.OtpEntity;
import com.ron.FoodDelivery.entities.user.UserEntity;
import com.ron.FoodDelivery.exceptions.EntityNotFoundException;
import com.ron.FoodDelivery.repositories.OtpRepository;
import com.ron.FoodDelivery.services.OtpService;
import com.ron.FoodDelivery.utils.ResponseLayout;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class OtpServiceImpl implements OtpService {
    @Autowired
    private OtpRepository otpRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private final long EXPIRATION_TIME_MS = 60000;

    private String generateOTP(int length) {
        StringBuilder otp = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    @Transactional
    @Override
    public String generate_otp(UserEntity user) {
        String otp = generateOTP(6);
        OtpEntity otpEntity = otpRepository.findById(user.getId()).orElse(null);
        if (otpEntity != null) {
            otpEntity.setCode(otp);
            otpEntity.setExpired_at(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS));
            entityManager.merge(otpEntity);
            return otp;
        }
        otpRepository.save(OtpEntity.builder()
                .user(user)
                .code(otp)
                .expired_at(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                .build());
        return otp;
    }

    @Override
    public ResponseLayout<ResponseLoginDto> is_valid(Long user_id, String otp) {
        OtpEntity otpEntity = otpRepository.findById(user_id).orElse(null);
        if (otpEntity == null) throw new EntityNotFoundException("OTP not found");
        if (is_expired(otpEntity.getExpired_at())) {
            return new ResponseLayout<>(null, "OTP is expired!",false);
        }
        if (!otpEntity.getCode().equals(otp)) {
            return new ResponseLayout<>(null, "OTP incorrect!", false);
        }
        return new ResponseLayout<>(null, "OTP is verified!",true);
    }

    private boolean is_expired(Date expired) {
        return expired.before(new Date());
    }
}
