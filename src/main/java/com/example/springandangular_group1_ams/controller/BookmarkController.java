package com.example.springandangular_group1_ams.controller;

import com.example.springandangular_group1_ams.model.dto.ArticleDto;
import com.example.springandangular_group1_ams.model.dto.BookmarkDto;
import com.example.springandangular_group1_ams.model.request.BookmarkRequest;
import com.example.springandangular_group1_ams.model.respone.ApiResponse;
import com.example.springandangular_group1_ams.model.respone.BookmarkResponseWithPage;
import com.example.springandangular_group1_ams.service.BookmarkService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static java.lang.Math.ceil;

@RestController
@RequestMapping("/api/v1/bookmark")
@AllArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping("/bookmark/{userId}")
    public ResponseEntity<?> bookmarkArticle(@PathVariable UUID userId, @RequestBody BookmarkRequest bookmarkRequest){

        ApiResponse<ArticleDto> response = ApiResponse.<ArticleDto>builder()
                .message("Successfully create new bookmark of user: "+userId )
                .status("200")
                .payload(bookmarkService.createBookmark(userId, bookmarkRequest.getArticleId()))
                .build();


        return ResponseEntity.ok(response);
    }

    @GetMapping("/bookmark/{userId}")
    public ResponseEntity<?> getBookmark(@PathVariable UUID userId,
                                         @RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "size", defaultValue = "5") int size
                                         ){
        BookmarkResponseWithPage<List<BookmarkDto>> response = BookmarkResponseWithPage.<List<BookmarkDto>>builder()
                .message("Successfully fetched bookmarks of user: "+userId )
                .status(200)
                .payload(bookmarkService.getAllBookmarkByUserIdWithPage(userId,page,size))
                .page(page)
                .size(size)
                .totalElement(bookmarkService.countAllBookmarkByUserId(userId))
                .totalPages( ceil(bookmarkService.countAllBookmarkByUserId(userId) / size))
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/bookmark/{userId}")
    public ResponseEntity<String> deleteBookmark(@PathVariable UUID userId, @RequestBody BookmarkRequest bookmarkRequest) {
        bookmarkService.deleteBookmark(userId, bookmarkRequest.getArticleId());
        return ResponseEntity.ok("Bookmark is successfully deleted.");
    }


}
