package com.example.springandangular_group1_ams.repository;

import com.example.springandangular_group1_ams.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    @Query(value = "SELECT c FROM Comment c WHERE c.article.id = :articleId")
    List<Comment> findCommentByArticleId(@Param("articleId") UUID articleId);
}
