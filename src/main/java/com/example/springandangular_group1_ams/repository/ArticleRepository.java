package com.example.springandangular_group1_ams.repository;

import com.example.springandangular_group1_ams.model.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID>  {
    @Query(value = "SELECT a FROM Article a WHERE a.published = true")
    Page<Article> findAllPublishedArticle(Pageable pageable);
}
