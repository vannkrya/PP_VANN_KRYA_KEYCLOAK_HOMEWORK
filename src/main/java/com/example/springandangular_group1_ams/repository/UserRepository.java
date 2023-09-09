package com.example.springandangular_group1_ams.repository;

import com.example.springandangular_group1_ams.model.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<MyUser, UUID> {
}
