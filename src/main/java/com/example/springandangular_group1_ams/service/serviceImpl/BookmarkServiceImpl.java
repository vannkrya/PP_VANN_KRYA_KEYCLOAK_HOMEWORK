package com.example.springandangular_group1_ams.service.serviceImpl;

import com.example.springandangular_group1_ams.exception.NotFoundExceptionClass;
import com.example.springandangular_group1_ams.model.dto.ArticleDto;
import com.example.springandangular_group1_ams.model.dto.BookmarkDto;
import com.example.springandangular_group1_ams.model.entity.Article;
import com.example.springandangular_group1_ams.model.entity.Bookmark;
import com.example.springandangular_group1_ams.model.entity.MyUser;
import com.example.springandangular_group1_ams.repository.ArticleRepository;
import com.example.springandangular_group1_ams.repository.BookmarkRepository;
import com.example.springandangular_group1_ams.repository.UserRepository;
import com.example.springandangular_group1_ams.service.BookmarkService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    @Override
    public ArticleDto createBookmark(UUID userId, UUID articleId) {

//        try {
//            //check weather article is uuid or not
//            UUID validArticleId = UUID.fromString(String.valueOf(articleId));
//            // If the above line doesn't throw an exception, articleId is a valid UUID
//
//
//        } catch (IllegalArgumentException e) {
//            // articleId is not a valid UUID
//            System.out.println("sdsadsadas");
//            throw new NotFoundExceptionClass("Oops, make sure you enter the valid UUID");
//        }

        if(articleRepository.findById(articleId).isEmpty()){
            throw new NotFoundExceptionClass("Oops, article cannot be found with ID: " + articleId);
        }

        if(userRepository.findById(userId).isEmpty()){
            throw new NotFoundExceptionClass("Oops, user cannot be found with ID: " + userId);
        }

        if(bookmarkRepository.findByArticleId(articleId)!= null){
            throw new NotFoundExceptionClass("Oops, bookmark is already exists for this article.");
        }




        Bookmark bookmark = new Bookmark(userId ,articleId) ;

        bookmarkRepository.save(bookmark);


        return articleRepository.findById(articleId).orElseThrow().toDto();
    }


    @Override
    public void deleteBookmark(UUID userId, UUID articleId) {

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new NotFoundExceptionClass("Oops, article cannot be found with ID: " + articleId));
        MyUser user= userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundExceptionClass("Oops, user cannot be found with ID: " + userId));


        List<Bookmark> allBookmarkByUserId=  bookmarkRepository.findByUserId(userId);
        boolean flag = false;
        for(Bookmark bookmark: allBookmarkByUserId){
            if(bookmark.getArticle().getId()==articleId){
                bookmarkRepository.delete(bookmark);
                flag=true;
            }
        }
        if(!flag){
            throw  new NotFoundExceptionClass("Oops, bookmark cannot be found." ) ;
        }
    }

    @Override
    public List<BookmarkDto> getAllBookmarkByUserIdWithPage(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of (page, size);

        if (userRepository.findById(userId).isEmpty()){
            throw new NotFoundExceptionClass("Oops, user cannot be found with ID: " + userId);
        }

        List<BookmarkDto> bookmarkDtosList =  bookmarkRepository.findAllByUserIdAndPageable(userId,pageable).stream().map(Bookmark::toDto).collect(Collectors.toList());
        if(bookmarkDtosList.isEmpty()){
            throw new NotFoundExceptionClass("Oops, there is no bookmark found. ");
        }


        return bookmarkDtosList;
    }

    //for getting total element in pages
    @Override
    public Double countAllBookmarkByUserId(UUID userId) {
        return bookmarkRepository.getTotalElementByUserId(userId);
    }


}
