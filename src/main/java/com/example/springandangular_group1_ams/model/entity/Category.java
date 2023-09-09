package com.example.springandangular_group1_ams.model.entity;

import com.example.springandangular_group1_ams.model.dto.CategoryDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, name = "name",length = 100, nullable = true)
    private String name;
    @ManyToMany(mappedBy = "categories")
    private List<Article> books;
    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public CategoryDto toDto(){
        return new CategoryDto(this.id, this.name);
    }

    public Category(String name) {
        this.name = name;
    }
}
