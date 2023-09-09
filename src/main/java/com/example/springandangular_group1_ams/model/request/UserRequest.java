package com.example.springandangular_group1_ams.model.request;


import com.example.springandangular_group1_ams.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String name;
    private Role role;
}
