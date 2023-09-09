package com.example.springandangular_group1_ams.service;

import com.example.springandangular_group1_ams.model.dto.UserDto;
import com.example.springandangular_group1_ams.model.request.UserRequest;
import com.example.springandangular_group1_ams.model.respone.ApiResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    ApiResponse<List<UserDto>> getAllUsers(Integer page, Integer size);

    Optional<UserDto> getUserById(UUID id);

    UserDto postUser(UserRequest userRequest);

    Optional<UserDto> updateUserById(UUID id, UserRequest userRequest);

    void deleteUserById(UUID id);
}
