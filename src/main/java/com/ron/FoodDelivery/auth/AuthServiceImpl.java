package com.ron.FoodDelivery.auth;

import com.ron.FoodDelivery.auth.dto.RequestLoginDto;
import com.ron.FoodDelivery.auth.dto.RequestRegisterDto;
import com.ron.FoodDelivery.auth.dto.RequestVerifyOTPDto;
import com.ron.FoodDelivery.auth.dto.ResponseLoginDto;
import com.ron.FoodDelivery.entities.token.UserAgent;
import com.ron.FoodDelivery.entities.user.UserEntity;
import com.ron.FoodDelivery.entities.user.UserRole;
import com.ron.FoodDelivery.exceptions.EntityNotFoundException;
import com.ron.FoodDelivery.exceptions.ServiceException;
import com.ron.FoodDelivery.jwt.JwtService;
import com.ron.FoodDelivery.mail.MailService;
import com.ron.FoodDelivery.repositories.UserRepository;
import com.ron.FoodDelivery.services.OtpService;
import com.ron.FoodDelivery.services.TokenService;
import com.ron.FoodDelivery.utils.Constant;
import com.ron.FoodDelivery.utils.RegexValid;
import com.ron.FoodDelivery.utils.ResponseLayout;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RegexValid regexValid;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OtpService otpService;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MailService mailService;
    @Autowired
    private TokenService tokenService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    private UserEntity findUserByCase(String input) {
        if (regexValid.isEmail(input)) {
            return userRepository.findByEmailAndIsLocked(input, false);
        }
        if (regexValid.isTel(input)) {
            return userRepository.findByPhoneNumberAndIsLocked(input, false);
        }
        return userRepository.findByUsernameAndIsLocked(input, false).orElse(null);
    }

    @Override
    public ResponseLayout login(RequestLoginDto requestLoginDto, UserAgent userAgent) {
        UserEntity user = findUserByCase(requestLoginDto.username());
        if (user == null) {
            throw new EntityNotFoundException("User not found!", HttpStatus.NOT_FOUND);
        }
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), requestLoginDto.password()));
        if (!authentication.isAuthenticated()) {
            return new ResponseLayout(null, "Password is incorrect!", HttpStatus.BAD_REQUEST);
        }
        //disable two-factor authentication
        if (!user.getEnabled_two_factor_auth()) {
            String access_token = jwtService.generate(user);
            ResponseLoginDto responseLoginDto = new ResponseLoginDto(user, false, access_token);
            tokenService.saveToken(user, access_token, userAgent);
            return new ResponseLayout(responseLoginDto, "Login successfully!", HttpStatus.OK);
        }
        //enable two-factor authentication
        /*
        Use email to set username of user to create access token
         */
        UserEntity userRes = UserEntity.builder()
                .username(user.getEmail())
                .first_name(user.getFirst_name())
                .last_name(user.getLast_name())
                .email(user.getEmail())
                .build();
        String access_token = jwtService.generate(userRes);
        //create otp code
        String otp = otpService.generate_otp(user);
        //send otp by email
        executorService.submit(() -> {
            mailService.send_otp(user, otp);
        });
        ResponseLoginDto responseLoginDto = new ResponseLoginDto(userRes, true, access_token);
        return new ResponseLayout(responseLoginDto, "Check OTP!", HttpStatus.OK);
    }

    @Override
    public ResponseLayout register(RequestRegisterDto requestRegisterDto) {
        //check email
        if (!regexValid.isEmail(requestRegisterDto.email())) {
            return new ResponseLayout(null, "Email is invalid!", HttpStatus.BAD_REQUEST);
        } else {
            UserEntity user = findUserByCase(requestRegisterDto.email());
            if (user != null) return new ResponseLayout(null, "Email is exist!", HttpStatus.BAD_REQUEST);
        }
        //check phone
        if (!regexValid.isTel(requestRegisterDto.phone_number())) {
            return new ResponseLayout(null, "Phone number is invalid!", HttpStatus.BAD_REQUEST);
        } else {
            UserEntity user = findUserByCase(requestRegisterDto.phone_number());
            if (user != null) return new ResponseLayout(null, "Phone number is exist!", HttpStatus.BAD_REQUEST);
        }
        //check username
        UserEntity user = findUserByCase(requestRegisterDto.username());
        if (user != null) return new ResponseLayout(null, "Username is exist!", HttpStatus.BAD_REQUEST);
        user = userRepository.save(UserEntity.builder()
                .first_name(requestRegisterDto.first_name())
                .phone_number(requestRegisterDto.phone_number())
                .last_name(requestRegisterDto.last_name())
                .password(passwordEncoder.encode(requestRegisterDto.password()))
                .username(requestRegisterDto.username())
                .enabled_two_factor_auth(false)
                .email(requestRegisterDto.email())
                .role(UserRole.USER)
                .is_locked(false)
                .avatar(Constant.AVATAR_DEFAULT_URL)
                .build());
        return new ResponseLayout(user, "Register successfully!", HttpStatus.OK);
    }

    @Override
    public ResponseLayout verify_otp(RequestVerifyOTPDto requestVerifyOTPDto, UserAgent userAgent) {
        if (!this.jwtService.isTokenValid(requestVerifyOTPDto.token()))
            throw new ServiceException("Token is expired! Please login with password again!", HttpStatus.FORBIDDEN);
        String email = jwtService.extractUsername(requestVerifyOTPDto.token());
        UserEntity user = userRepository.findByEmailAndIsLocked(email, false);
        ResponseLayout resOtp = otpService.is_valid(user.getId(), requestVerifyOTPDto.code());
        if (resOtp.httpStatus() == HttpStatus.OK) {
            String access_token = jwtService.generate(user);
            ResponseLoginDto responseLoginDto = new ResponseLoginDto(user, user.getEnabled_two_factor_auth(), access_token);
            tokenService.saveToken(user, access_token, userAgent);
            return new ResponseLayout(responseLoginDto, "Login successfully!", HttpStatus.OK);
        }
        return resOtp;
    }

    @Transactional
    @Override
    public ResponseLayout change_tfa(String username) {
        UserEntity user = userRepository.findByUsernameAndIsLocked(username, false)
                .orElseThrow(() -> new ServiceException("Token error!", HttpStatus.FORBIDDEN));
        user.setEnabled_two_factor_auth(!user.getEnabled_two_factor_auth());
        entityManager.merge(user);
        return new ResponseLayout(user.getEnabled_two_factor_auth(), "Change two-factor authentication successfully!", HttpStatus.OK);
    }
}


