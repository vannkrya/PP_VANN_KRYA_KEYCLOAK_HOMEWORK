package com.example.springandangular_group1_ams.model.request;


import com.example.springandangular_group1_ams.model.entity.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    private String name ;
   public Category toEntity(){
    return new Category(null,this.name);
   }
}
