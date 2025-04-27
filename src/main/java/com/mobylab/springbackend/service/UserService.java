package com.mobylab.springbackend.service;

import com.mobylab.springbackend.exception.entities.UserException;
import com.mobylab.springbackend.service.dto.entity.user.UserResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private final RequestService requestService;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(RequestService requestService) {
        this.requestService = requestService;
    }

    public UserResponseDto getById(UUID id) {
        ResponseEntity<UserResponseDto> response = requestService.sendGetRequest(
                "/user/getById",
                Collections.singletonMap("id", id.toString()),
                new ParameterizedTypeReference<>() {});

        if (!response.getStatusCode().is2xxSuccessful()) {
            logger.error("Request to find user by id failed.");
            throw new UserException("Request to find user by id failed.");
        }

        return response.getBody();
    }

    public UserResponseDto getByEmail(String email) {
        ResponseEntity<UserResponseDto> response = requestService.sendGetRequest(
                "/user/getByEmail",
                Collections.singletonMap("email", email),
                new ParameterizedTypeReference<>() {});

        if (!response.getStatusCode().is2xxSuccessful()) {
            logger.error("Request to find user by email failed.");
            throw new UserException("Request to find user by email failed.");
        }

        return response.getBody();
    }

    public List<UserResponseDto> getByFamilyId(UUID familyId) {
        ResponseEntity<List<UserResponseDto>> response = requestService.sendGetRequest(
                "/user/getByFamilyId",
                Collections.singletonMap("familyId", familyId.toString()),
                new ParameterizedTypeReference<>() {});

        if (!response.getStatusCode().is2xxSuccessful()) {
            logger.error("Request to find user list by familyId failed.");
            throw new UserException("Request to find user list by familyId failed.");
        }

        return response.getBody();
    }
}
