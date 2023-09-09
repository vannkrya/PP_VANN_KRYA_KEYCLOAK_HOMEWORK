package com.example.springandangular_group1_ams.controller;

import com.example.springandangular_group1_ams.exception.NotFoundExceptionClass;
import com.example.springandangular_group1_ams.model.dto.ArticleDto;
import com.example.springandangular_group1_ams.model.dto.CategoryDto;
import com.example.springandangular_group1_ams.model.dto.CommentDto;
import com.example.springandangular_group1_ams.model.entity.Article;
import com.example.springandangular_group1_ams.model.request.ArticleRequest;
import com.example.springandangular_group1_ams.model.request.CommentRequest;
import com.example.springandangular_group1_ams.model.respone.ApiResponse;
import com.example.springandangular_group1_ams.service.ArticleService;
import com.example.springandangular_group1_ams.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/article")

public class ArticleController {
    private final ArticleService articleService;
    private final CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleById(@PathVariable UUID id) {
        try {
            ArticleDto article = articleService.getArticleById(id);

            ApiResponse<?> response = ApiResponse.<ArticleDto>builder()
                    .payload(article)
                    .build();
            return ResponseEntity.ok(response);
        } catch (NotFoundExceptionClass e) {
            ApiResponse<String> response = ApiResponse.<String>builder()
                    .message("Can not found article with id: " + id)
                    .status("201")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateArticle(@RequestBody ArticleRequest articleRequest, @PathVariable UUID id) {
        return ResponseEntity.ok().body(
                new ApiResponse<>("Update successfully.", "200", articleService.updateArticle(id, articleRequest))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable UUID id) {
        try {
            articleService.deleteArticle(id);
            ApiResponse<?> response = ApiResponse.<ArticleDto>builder()
                    .message("Deleted successfully")
                    .status("200")
                    .build();
            return ResponseEntity.ok(response);
        } catch (NotFoundExceptionClass e) {
            ApiResponse<String> response = ApiResponse.<String>builder()
                    .message("Can not found article with id: " + id)
                    .status("206")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllArticles(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        var articles = articleService.getAllArticles((page - 1), size);
        ApiResponse<?> response = ApiResponse.<List<ArticleDto>>builder()
                .message("successfully fetched article")
                .status("200")
                .payload(articles.getContent())
                .page(page)
                .size(size)
                .totalElements((int) articles.getTotalElements())
                .totalPages(articles.getTotalPages())
                .build();
        return ResponseEntity.ok().body(
                response
        );
    }

    @PostMapping
    public ResponseEntity<?> addArticle(@RequestBody ArticleRequest articleRequest) {
        try {
            ArticleDto article = articleService.addArticle(articleRequest);

            return ResponseEntity.ok().body(
                    new ApiResponse<>("Post successfully", "200", article)
            );
        } catch (NotFoundExceptionClass e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Only the teacher is allowed to post an article!", "201"));

        }

    }

    @PostMapping("/comment/{id}")
    public ResponseEntity<?> addComment(@PathVariable UUID id, @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok().body(
                new ApiResponse<>("Added Successfully", "200", commentService.addComment(id, commentRequest))
        );
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<?> getCommentByArticleId(@PathVariable UUID id) {
        List<CommentDto> comments = commentService.getAllCommentByArticleId(id);
        if (comments != null) {
            return ResponseEntity.ok().body(
                    new ApiResponse<>("success fetch comment by article id: " + id, "200", commentService.getAllCommentByArticleId(id))
            );
        }
        return ResponseEntity.ok().body(
                new ApiResponse<>("no comment by article id: " + id, "400")
        );
    }

    @GetMapping("/isPublished")
    public ResponseEntity<?> getAllPublishArticles(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        var articles = articleService.getAllPublishedArticles((page - 1), size);
        ApiResponse<?> response = ApiResponse.<List<ArticleDto>>builder()
                .message("successfully fetched article")
                .status("200")
                .payload(articles.getContent())
                .page(page)
                .size(size)
                .totalElements((int) articles.getTotalElements())
                .totalPages(articles.getTotalPages())
                .build();
        return ResponseEntity.ok().body(response
        );
    }


}
