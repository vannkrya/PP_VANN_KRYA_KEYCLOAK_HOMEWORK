package com.example.springandangular_group1_ams.repository;

import com.example.springandangular_group1_ams.model.dto.ArticleDto;
import com.example.springandangular_group1_ams.model.dto.BookmarkDto;
import com.example.springandangular_group1_ams.model.entity.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, UUID> {


    List<Bookmark> findByUserId(UUID userId);
//    @Query(value = "select * from bookmarks inner join articles a on a.id = bookmarks.article_id  where article_id = :articleId ", nativeQuery = true)
//    Bookmark findByArticleId(UUID articleId);


//    void deleteByUserIdAndArticleId(UUID userId, UUID articleId);



        @Query("select b from Bookmark b where b.user.id= :userId  ")
    List<Bookmark> findAllByUserIdAndPageable(UUID userId, Pageable pageable);


    @Query("select count (b) from Bookmark b where b.user.id= :userId  ")
    Double getTotalElementByUserId(UUID userId);

//    @Query(value = "SELECT bookmarks.*, articles.*, users.*\n" +
//            "FROM bookmarks\n" +
//            "         INNER JOIN articles ON bookmarks.article_id = articles.id\n" +
//            "         INNER JOIN users ON bookmarks.user_id = users.id\n" +
//            "WHERE bookmarks.article_id = '25d9b86e-8865-429b-a856-f0ee9bfd47d0' ", nativeQuery = true)
    Bookmark findByArticleId(UUID articleId);


}
