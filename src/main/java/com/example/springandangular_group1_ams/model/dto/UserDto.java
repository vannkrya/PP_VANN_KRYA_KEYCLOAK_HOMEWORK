package com.example.springandangular_group1_ams.model.dto;

import com.example.springandangular_group1_ams.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private UUID id;
    private String name;
    private Role role;
}
