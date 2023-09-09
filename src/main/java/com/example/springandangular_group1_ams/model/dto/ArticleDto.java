package com.example.springandangular_group1_ams.model.dto;

import com.example.springandangular_group1_ams.model.entity.Category;
import com.example.springandangular_group1_ams.model.entity.Comment;
import com.example.springandangular_group1_ams.model.entity.MyUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    private UUID id;
    private String title;
    private String description;
    private UserDto user;
private List<CommentDto> comments = new ArrayList<>();
    private List<CategoryDto> categories = new ArrayList<>();
    private Boolean published = false;

    public ArticleDto(UUID id, String title, String description, MyUser user,
                      List<Comment> comments, List<Category> categories, Boolean published ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.user = user.toDto();
        if (comments != null) {
            this.comments = comments.stream().map(Comment::toDto).toList();
        } else {
            this.comments = new ArrayList<>();
        }
        this.categories = categories.stream().map(Category::toDto).toList();
        this.published = published;
    }



}
