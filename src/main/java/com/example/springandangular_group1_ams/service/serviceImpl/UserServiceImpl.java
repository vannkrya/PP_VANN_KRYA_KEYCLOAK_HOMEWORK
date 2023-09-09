package com.example.springandangular_group1_ams.service.serviceImpl;


import com.example.springandangular_group1_ams.exception.NotFoundExceptionClass;
import com.example.springandangular_group1_ams.exception.NullExceptionClass;
import com.example.springandangular_group1_ams.model.dto.UserDto;
import com.example.springandangular_group1_ams.model.entity.MyUser;
import com.example.springandangular_group1_ams.model.request.UserRequest;
import com.example.springandangular_group1_ams.model.respone.ApiResponse;
import com.example.springandangular_group1_ams.repository.UserRepository;
import com.example.springandangular_group1_ams.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public ApiResponse<List<UserDto>> getAllUsers(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<MyUser> userPage = userRepository.findAll(pageable);
        List<MyUser> users = userPage.getContent();
        List<UserDto> userDtos = users.stream()
                .map(myUser -> {
                    UserDto userDto = new UserDto();
                    userDto.setId(myUser.getId());
                    userDto.setName(myUser.getName());
                    userDto.setRole(myUser.getRole());
                    return userDto;
                }).toList();

        Integer totalElements = (int) userPage.getTotalElements();
        Integer totalPages = userPage.getTotalPages();

        return ApiResponse.<List<UserDto>>builder()
                .payload(userDtos)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
    }

    @Override
    public Optional<UserDto> getUserById(UUID id) {
        Optional<MyUser> myUser = userRepository.findById(id);
        return myUser
                .map(myUser1 -> {
                    UserDto user = new UserDto();
                    user.setId(myUser.get().getId());
                    user.setName(myUser.get().getName());
                    user.setRole(myUser.get().getRole());
                    return user;
                });
    }

    @Override
    public UserDto postUser(UserRequest userRequest) {
        MyUser myUser = new MyUser();

        if(userRequest.getName().isEmpty()) {
            throw new NullExceptionClass(
                    "field name cannot be null",
                    "name is null"
            );
        }

        myUser.setName(userRequest.getName());
        myUser.setRole(userRequest.getRole());
        return userRepository.save(myUser).toDto();
    }

    @Override
    public Optional<UserDto> updateUserById(UUID id, UserRequest userRequest) {
        Optional<MyUser> findExistingUser = userRepository.findById(id);

        if (!findExistingUser.isPresent()){
            throw new NotFoundExceptionClass("User not found with id: " + id);
        }
        if(userRequest.getName().isEmpty()) {
            throw new NullExceptionClass(
                    "field name cannot be null",
                    "name is null"
            );
        }


//>>>>>>> 9e28036632794b43dac9e5dd3b57b4157f18b7a9
        findExistingUser.get().setName(userRequest.getName());
        findExistingUser.get().setRole(userRequest.getRole());

        MyUser updateUser = userRepository.save(findExistingUser.get());

        return Optional.ofNullable(updateUser.toDto());
    }

    @Override
    public void deleteUserById(UUID id) {
        Optional<UserDto> user = getUserById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new NotFoundExceptionClass("User not found with id: " + id);
        }
    }
}
