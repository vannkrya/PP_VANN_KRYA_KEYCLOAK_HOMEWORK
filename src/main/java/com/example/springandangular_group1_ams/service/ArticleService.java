package com.example.springandangular_group1_ams.service;


import com.example.springandangular_group1_ams.model.dto.ArticleDto;
import com.example.springandangular_group1_ams.model.request.ArticleRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ArticleService {
    Page<ArticleDto> getAllArticles(Integer page, Integer size);

    ArticleDto addArticle(ArticleRequest articleRequest);

    ArticleDto getArticleById(UUID id);

    ArticleDto updateArticle(UUID id, ArticleRequest articleRequest);

    void deleteArticle(UUID id);

    Page<ArticleDto> getAllPublishedArticles(Integer page, Integer size);
}
