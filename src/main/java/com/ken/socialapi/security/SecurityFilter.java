package com.ken.socialapi.security;

import com.ken.socialapi.exceptions.AuthorizationFailException;
import com.ken.socialapi.repository.UserRepository;
import com.ken.socialapi.security.jwt.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserRepository userRepository;

    public SecurityFilter(JWTService jwtService, UserRepository userRepository){
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("X-Authorization");
        if(token != null){
            String email;
            try {
                email = jwtService.getSubject(token);
                if(email != null){
                    UserDetails user = userRepository.findUserByEmail(email);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (AuthorizationFailException e) {
                throw new RuntimeException("The token is not valid.");
            }
        }
        filterChain.doFilter(request, response);
    }
}
