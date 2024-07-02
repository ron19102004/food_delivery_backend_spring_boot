package com.ron.FoodDelivery.jwt;

import com.ron.FoodDelivery.entities.token.TokenEntity;
import com.ron.FoodDelivery.entities.token.UserAgent;
import com.ron.FoodDelivery.exceptions.ServiceException;
import com.ron.FoodDelivery.services.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String token = header.substring(7);
        try {
            TokenEntity tokenEntity = tokenService.findByToken(token);
            if (tokenEntity == null) {
                throw new ServiceException("Token is not exist!", HttpStatus.FORBIDDEN);
            }
            if (!this.jwtService.isTokenValid(token)) {
                tokenService.deleteByToken(token);
                filterChain.doFilter(request, response);
                return;
            }
            String username = this.jwtService.extractUsername(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        } catch (UsernameNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
        } catch (ExpiredJwtException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Expired token!");
        }
    }
}
