package com.example.springandangular_group1_ams.service.serviceImpl;

import com.example.springandangular_group1_ams.exception.NullExceptionClass;
import com.example.springandangular_group1_ams.model.dto.CommentDto;
import com.example.springandangular_group1_ams.model.entity.Article;
import com.example.springandangular_group1_ams.model.entity.Comment;
import com.example.springandangular_group1_ams.model.request.CommentRequest;
import com.example.springandangular_group1_ams.repository.ArticleRepository;
import com.example.springandangular_group1_ams.repository.CommentRepository;
import com.example.springandangular_group1_ams.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    @Override
    public CommentDto addComment(UUID id, CommentRequest commentRequest) {
        Article article = articleRepository.findById(id).get();
        if (articleRepository.findById(id).isPresent()){
            if(commentRequest.getCaption().isEmpty()){
                throw new NullExceptionClass("This shouldn't be null", "The field is null");
            }
            var comment = commentRequest.toEntity();
            comment.setArticle(article);
            return commentRepository.save(comment).toDto();
        }
//               orElseThrow(()-> new NotFoundExceptionClass("This article doesn't exist!"));
        return null;
    }

    @Override
    public List<CommentDto> getAllCommentByArticleId(UUID id) {
        if(articleRepository.findById(id).isPresent()){
           return commentRepository.findCommentByArticleId(id).stream().map(Comment::toDto).toList();
        }
        return null;
    }
}
