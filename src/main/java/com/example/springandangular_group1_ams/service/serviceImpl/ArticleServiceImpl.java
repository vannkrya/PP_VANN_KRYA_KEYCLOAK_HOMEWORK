package com.example.springandangular_group1_ams.service.serviceImpl;

import com.example.springandangular_group1_ams.exception.NotFoundExceptionClass;
import com.example.springandangular_group1_ams.model.dto.ArticleDto;
import com.example.springandangular_group1_ams.model.entity.Article;
import com.example.springandangular_group1_ams.model.entity.Category;
import com.example.springandangular_group1_ams.model.entity.MyUser;
import com.example.springandangular_group1_ams.model.entity.Role;
import com.example.springandangular_group1_ams.model.request.ArticleRequest;
import com.example.springandangular_group1_ams.repository.ArticleRepository;
import com.example.springandangular_group1_ams.repository.CategoryRepository;
import com.example.springandangular_group1_ams.repository.UserRepository;
import com.example.springandangular_group1_ams.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<ArticleDto> getAllArticles(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return articleRepository.findAll(pageable).map(Article::toDto);
    }

    @Override
    public ArticleDto addArticle(ArticleRequest articleRequest) {
        return userRepository.findById(articleRequest.getTeacherId()).map(myUser -> {
            if (myUser.getRole().name().equals(Role.TEACHER.name())) {
                Category category = categoryRepository.findByName(articleRequest.getCategories().get(0).getName());
                if (category == null) {
                    throw new IllegalArgumentException("Category not found");
                }
                var bookEntity = articleRequest.toEntity();
                bookEntity.setUser(myUser);
                bookEntity.setCategories(List.of(category));
                bookEntity.setPublished(articleRequest.getPublished());
                return articleRepository.save(bookEntity).toDto();
            }else {
                throw new IllegalArgumentException("You are not allow to post the article");
            }
        }).orElseThrow(()-> new NotFoundExceptionClass("This article doesn't exist!"));
//        MyUser user = userRepository.findById(articleRequest.getTeacherId())
//                .orElseThrow(() -> new NotFoundExceptionClass("This user doesn't exist"));
//        if (user.getRole().name().equals(Role.TEACHER.name())) {
//
//            Category category = categoryRepository.findByName(articleRequest.getCategories().get(0).getName());
//            var bookEntity = articleRequest.toEntity();
//            bookEntity.setUser(user);
//            bookEntity.setCategories(List.of(category));
//            bookEntity.setPublished(articleRequest.getPublished());
//            return articleRepository.save(bookEntity).toDto();
//        }
//        return null;
    }

    @Override
    public ArticleDto getArticleById(UUID id) {
        return articleRepository.findById(id)
                .map(Article::toDto)
                .orElseThrow(() -> new NotFoundExceptionClass("This article doesn't exit!"));
    }

    @Override
    public ArticleDto updateArticle(UUID id, ArticleRequest articleRequest) {

        Article article = articleRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("This article doesn't exist!"));
        MyUser user = userRepository.findById(articleRequest.getTeacherId()).orElseThrow(() -> new NotFoundExceptionClass("This user doesn't exist!"));
        Category category = categoryRepository.findByName(articleRequest.getCategories().get(0).getName());
        article.setPublished(articleRequest.getPublished());
        article.setDescription(articleRequest.getDescription());
        article.setTitle(article.getTitle());
        article.setUser(user);
        article.setCategories(List.of(category));
        return article.toDto();
    }

    @Override
    public void deleteArticle(UUID id) {
        if (articleRepository.findById(id).isPresent()) {
            articleRepository.deleteById(id);
        } else {
            throw new NotFoundExceptionClass("This article doesn't exist!");
        }
    }

    @Override
    public Page<ArticleDto> getAllPublishedArticles(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return articleRepository.findAllPublishedArticle(pageable).map(Article::toDto);
    }
}
