package com.mobylab.springbackend.service;

import com.mobylab.springbackend.service.dto.auth.LoginRequestDto;
import com.mobylab.springbackend.service.dto.auth.RegisterRequestDto;
import com.mobylab.springbackend.service.dto.auth.RegisterWithFamilyIdRequestDto;
import com.mobylab.springbackend.exception.auth.AuthException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.Collections;

@Service
@Transactional
public class AuthService {
    @Autowired
    private final RequestService requestService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthService(RequestService requestService) {
        this.requestService = requestService;
    }

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public void registerWithoutFamilyId(RegisterRequestDto registerRequestDto) {
        ResponseEntity<Void> response = requestService.sendPostRequest(
                "/authproxy/register",
                Collections.emptyMap(),
                registerRequestDto,
                new ParameterizedTypeReference<>(){});

        if (!response.getStatusCode().is2xxSuccessful()) {
            logger.error("Register request failed.");
            throw new AuthException("Register request failed.");
        }

        System.out.println(response.getBody());
    }

    public void registerWithFamilyId(RegisterWithFamilyIdRequestDto registerRequestDto) {
        ResponseEntity<Void> response = requestService.sendPostRequest(
                "/authproxy/register/family-id",
                Collections.emptyMap(),
                registerRequestDto,
                new ParameterizedTypeReference<>(){});

        if (!response.getStatusCode().is2xxSuccessful()) {
            logger.error("Register with family id request failed.");
            throw new AuthException("Register with family id request failed.");
        }
    }

    public void login(LoginRequestDto loginRequestDto) {
        ResponseEntity<Void> response = requestService.sendPostRequest(
                "/authproxy/login",
                Collections.emptyMap(),
                loginRequestDto,
                new ParameterizedTypeReference<>(){});

        if (!response.getStatusCode().is2xxSuccessful()) {
            logger.error("Login request failed.");
            throw new AuthException("Login request failed.");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
