package com.example.springandangular_group1_ams.service;

import com.example.springandangular_group1_ams.model.dto.ArticleDto;
import com.example.springandangular_group1_ams.model.dto.BookmarkDto;
import com.example.springandangular_group1_ams.model.entity.Article;
import com.example.springandangular_group1_ams.model.entity.Bookmark;

import java.util.List;
import java.util.UUID;

public interface BookmarkService {


    ArticleDto createBookmark(UUID userId, UUID articleId);

//    List<BookmarkDto> getAllBookmarkByUserId (UUID userId);

    void deleteBookmark(UUID userId, UUID articleId);

    List<BookmarkDto> getAllBookmarkByUserIdWithPage(UUID userId, int page, int size);


    Double countAllBookmarkByUserId(UUID userId);
}
