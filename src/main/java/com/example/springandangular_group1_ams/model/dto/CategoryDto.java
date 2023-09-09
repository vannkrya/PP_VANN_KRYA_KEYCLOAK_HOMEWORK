package com.example.springandangular_group1_ams.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Long id;
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ArticleDto> articles;

    public CategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }


}
