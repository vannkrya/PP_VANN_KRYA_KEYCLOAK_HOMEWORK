package com.example.springandangular_group1_ams.repository;

import com.example.springandangular_group1_ams.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>{
//@Query("SELECT c FROM Category c WHERE c.name LIKE %:character%")
Page<Category> findByNameContainingIgnoreCase(Pageable pageable, String name);
    Category findByName(String name);
}
