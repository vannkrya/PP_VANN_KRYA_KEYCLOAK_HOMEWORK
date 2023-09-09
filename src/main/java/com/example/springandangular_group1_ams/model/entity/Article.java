package com.example.springandangular_group1_ams.model.entity;

import com.example.springandangular_group1_ams.model.dto.ArticleDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "articles")

public class Article {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    @Column(name = "title")

    private String title;
    @Column(name = "description")

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private MyUser user;

    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks;

    @ManyToMany
    @JoinTable(
        name = "article_categories",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();
    @Column(name = "published")

    private Boolean published = false;

    public Article(UUID articleId) {
        this.id=articleId;
    }

    public ArticleDto toDto(){
        return new ArticleDto(
                this.id,
                this.title,
                this.description,
                this.user,
                this.comments,
                this.categories,
        this.published
        );

    }



}
