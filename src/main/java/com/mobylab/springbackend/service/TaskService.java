package com.mobylab.springbackend.service;


import com.mobylab.springbackend.exception.entities.TaskException;
import com.mobylab.springbackend.service.dto.entity.task.TaskRequestDto;
import com.mobylab.springbackend.service.dto.entity.task.TaskResponseDto;
import jakarta.transaction.Transactional;
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
@Transactional
public class TaskService {
    @Autowired
    private final RequestService requestService;
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    public TaskService(RequestService requestService) {
        this.requestService = requestService;
    }

    public TaskResponseDto getById(UUID id) {
        ResponseEntity<TaskResponseDto> response = requestService.sendGetRequest(
                "/task/getById",
                Collections.singletonMap("id", id.toString()),
                new ParameterizedTypeReference<>() {});

        if (!response.getStatusCode().is2xxSuccessful()) {
            logger.error("Request to find task by id failed.");
            throw new TaskException("Request to find task by id failed.");
        }

        return response.getBody();
    }

    public List<TaskResponseDto> getByCreatorId(UUID creatorId) {
        ResponseEntity<List<TaskResponseDto>> response = requestService.sendGetRequest(
                "/task/getByCreatorId",
                Collections.singletonMap("creatorId", creatorId.toString()),
                new ParameterizedTypeReference<>() {});

        if (!response.getStatusCode().is2xxSuccessful()) {
            logger.error("Request to find task list by creatorId failed.");
            throw new TaskException("Request to find task list by creatorId failed.");
        }

        return response.getBody();
    }

    public List<TaskResponseDto> getByAssigneeId(UUID assigneeId) {
        ResponseEntity<List<TaskResponseDto>> response = requestService.sendGetRequest(
                "/task/getByAssigneeId",
                Collections.singletonMap("assigneeId", assigneeId.toString()),
                new ParameterizedTypeReference<>() {});

        if (!response.getStatusCode().is2xxSuccessful()) {
            logger.error("Request to find task list by assigneeId failed.");
            throw new TaskException("Request to find task list by assigneeId failed.");
        }

        return response.getBody();
    }

    public TaskResponseDto create(TaskRequestDto taskRequestDto) {
        ResponseEntity<TaskResponseDto> response = requestService.sendPostRequest(
                "/task/create",
                Collections.emptyMap(),
                taskRequestDto,
                new ParameterizedTypeReference<>() {});

        if (!response.getStatusCode().is2xxSuccessful()) {
            logger.error("Request to create task failed.");
            throw new TaskException("Request to create task failed.");
        }

        return response.getBody();
    }

    public TaskResponseDto update(UUID id, TaskRequestDto taskRequestDto) {
        ResponseEntity<TaskResponseDto> response = requestService.sendPutRequest(
                "/task/update",
                Collections.singletonMap("id", id.toString()),
                taskRequestDto,
                new ParameterizedTypeReference<>() {});

        if (!response.getStatusCode().is2xxSuccessful()) {
            logger.error("Request to update task failed.");
            throw new TaskException("Request to update task failed.");
        }

        return response.getBody();
    }

    public TaskResponseDto complete(UUID id) {
        ResponseEntity<TaskResponseDto> response = requestService.sendPutRequest(
                "/task/complete",
                Collections.singletonMap("id", id.toString()),
                null,
                new ParameterizedTypeReference<>() {});

        if (!response.getStatusCode().is2xxSuccessful()) {
            logger.error("Request to complete task failed.");
            throw new TaskException("Request to complete task failed.");
        }

        return response.getBody();
    }

    public void delete(UUID id) {
        ResponseEntity<Void> response = requestService.sendDeleteRequest(
                "/task/delete",
                Collections.singletonMap("id", id.toString()),
                new ParameterizedTypeReference<>() {});

        if (!response.getStatusCode().is2xxSuccessful()) {
            logger.error("Request to delete task failed.");
            throw new TaskException("Request to delete task failed.");
        }
    }
}
