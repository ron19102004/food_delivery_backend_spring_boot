package com.ron.FoodDelivery.services.impls;

import com.ron.FoodDelivery.entities.token.TokenEntity;
import com.ron.FoodDelivery.entities.token.UserAgent;
import com.ron.FoodDelivery.entities.user.UserEntity;
import com.ron.FoodDelivery.jwt.JwtService;
import com.ron.FoodDelivery.repositories.TokenRepository;
import com.ron.FoodDelivery.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private JwtService jwtService;
    private final int MAX_TOKEN_LOGIN = 5;

    @Override
    public void saveToken(UserEntity user, String token, UserAgent userAgent) {
        List<TokenEntity> tokenEntities = tokenRepository.findAllByUserId(user.getId());
        if (tokenEntities.size() == MAX_TOKEN_LOGIN) {
            int index = 0;
            TokenEntity tokenToDelete = tokenEntities.getFirst();
            while (index < MAX_TOKEN_LOGIN) {
                TokenEntity item = tokenEntities.get(index);
                if (!jwtService.isTokenValid(item.getAccess_token())){
                    tokenRepository.delete(item);
                    index++;
                    continue;
                }
                if ((index == MAX_TOKEN_LOGIN - 1) && item.getUser_agent() == UserAgent.MOBILE) {
                    tokenToDelete = tokenEntities.getFirst();
                    break;
                }
                if (item.getUser_agent() == UserAgent.MOBILE) {
                    tokenToDelete = tokenEntities.get(index + 1);
                }
                index++;
            }
            tokenRepository.delete(tokenToDelete);
        }
        TokenEntity tokenEntity = TokenEntity.builder()
                .user(user)
                .access_token(token)
                .expired_at(jwtService.getExpired(token))
                .user_agent(userAgent)
                .build();
        tokenRepository.save(tokenEntity);
    }

    @Override
    public void deleteByToken(String token) {
        TokenEntity tokenEntity = findByToken(token);
        tokenRepository.delete(tokenEntity);
    }

    @Override
    public TokenEntity findByToken(String token) {
        return tokenRepository.findByAccessToken(token);
    }

}
