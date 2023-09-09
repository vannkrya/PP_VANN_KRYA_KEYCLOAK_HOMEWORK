package com.example.springandangular_group1_ams.model.entity;

import com.example.springandangular_group1_ams.model.dto.ArticleDto;
import com.example.springandangular_group1_ams.model.dto.BookmarkDto;
import com.example.springandangular_group1_ams.repository.ArticleRepository;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "bookmarks")
public class Bookmark {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private MyUser user;

    @ManyToOne
    @JoinColumn(name = "article_id")
    @OrderColumn
    private Article article;

    public Bookmark( UUID userId, UUID articleId) {
        this.article= new Article(articleId);
        this.user=new MyUser(userId);

    }

    public BookmarkDto toDto(){
        return new BookmarkDto( this.article.toDto() );
    }



}