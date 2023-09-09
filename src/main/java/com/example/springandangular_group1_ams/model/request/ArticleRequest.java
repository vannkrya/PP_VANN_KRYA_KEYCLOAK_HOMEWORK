package com.example.springandangular_group1_ams.model.request;

import com.example.springandangular_group1_ams.model.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleRequest {
    private String title;
    private String description;

    private List<CategoryRequest> categories;
    private UUID teacherId;
    private Boolean published = false;

    public Article toEntity(){
        Article article = new Article();
        article.setTitle(this.title);
        article.setDescription(this.description);
        article.setPublished(this.published);
        article.setCategories(categories.stream().map(CategoryRequest::toEntity).toList());
        return article;
    }


}
