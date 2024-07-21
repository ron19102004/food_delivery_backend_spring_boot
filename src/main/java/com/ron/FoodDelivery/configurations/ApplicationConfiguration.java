package com.ron.FoodDelivery.configurations;

import com.ron.FoodDelivery.repositories.UserRepository;
import com.ron.FoodDelivery.utils.RegexValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfiguration {
    public final static String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public final static String TEL_REGEX = "^(\\+[0-9]{1,3})?[0-9]{10,}$";
    public final static String AWS_S3_URL_REGEX = "";
    @Autowired
    private UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository
                .findByUsernameAndIsLocked(username, false)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RegexValid regexValid() {
        return new RegexValid() {
            @Override
            public boolean isEmail(String email) {
                return check(EMAIL_REGEX, email);
            }

            @Override
            public boolean isTel(String tel) {
                return check(TEL_REGEX, tel);
            }

            @Override
            public boolean isAwsS3Url(String url) {
                return check(AWS_S3_URL_REGEX,url);
            }
        };
    }
}
