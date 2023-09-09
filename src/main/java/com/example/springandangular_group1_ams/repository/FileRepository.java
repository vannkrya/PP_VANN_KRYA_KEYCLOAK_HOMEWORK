package com.example.springandangular_group1_ams.repository;

import com.example.springandangular_group1_ams.model.entity.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileDB,String> {

}

