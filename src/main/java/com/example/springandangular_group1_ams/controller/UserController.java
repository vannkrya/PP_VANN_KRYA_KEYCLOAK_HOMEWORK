package com.example.springandangular_group1_ams.controller;
import com.example.springandangular_group1_ams.model.dto.UserDto;
import com.example.springandangular_group1_ams.model.request.UserRequest;
import com.example.springandangular_group1_ams.model.respone.ApiResponse;
import com.example.springandangular_group1_ams.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    ) {
        ApiResponse<List<UserDto>> users = userService.getAllUsers((page - 1), size);
        ApiResponse<List<UserDto>> response;
        if (users.getPayload().isEmpty()) {
            response = ApiResponse.<List<UserDto>>builder()
                    .message("no users to fetched")
                    .status("404")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            response = ApiResponse.<List<UserDto>>builder()
                    .message("successfully fetched users")
                    .status("200")
                    .payload(users.getPayload())
                    .page(page)
                    .size(size)
                    .totalElements(users.getTotalElements())
                    .totalPages(users.getTotalPages())
                    .build();
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {
        Optional<UserDto> userDto = userService.getUserById(id);
        ApiResponse<Optional<UserDto>> response;
        if  (userDto.isEmpty() || !id.equals(userDto.get().getId()))  {
            response = ApiResponse.<Optional<UserDto>>builder()
                    .message("user not found with id: " + id)
                    .status("404")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            response = ApiResponse.<Optional<UserDto>>builder()
                    .message("user found")
                    .status("200")
                    .payload(userDto)
                    .build();
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping
    public ResponseEntity<?> postUser(@RequestBody UserRequest userRequest) {
        UserDto userDto = userService.postUser(userRequest);
        ApiResponse<UserDto> response = ApiResponse.<UserDto>builder()
                .message("insert user successfully")
                .status("200")
                .payload(userDto)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(
            @PathVariable UUID id,
            @RequestBody UserRequest userRequest
    ) {
        Optional<UserDto> userDto = userService.updateUserById(id, userRequest);
        ApiResponse<Optional<UserDto>> response = ApiResponse.<Optional<UserDto>>builder()
                .message("Update user successfully")
                .status("200")
                .payload(userDto)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable UUID id) {
        userService.deleteUserById(id);
        ApiResponse<UserDto> response = ApiResponse.<UserDto>builder()
                .message("Delete user successfully with id: " + id)
                .status("200")
                .build();
        return ResponseEntity.ok(response);
    }
}
