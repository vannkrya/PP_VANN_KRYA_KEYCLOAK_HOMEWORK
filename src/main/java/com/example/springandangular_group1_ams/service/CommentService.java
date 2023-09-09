package com.example.springandangular_group1_ams.service;
import com.example.springandangular_group1_ams.model.dto.CommentDto;
import com.example.springandangular_group1_ams.model.request.CommentRequest;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    CommentDto addComment(UUID id, CommentRequest commentRequest);

    List<CommentDto> getAllCommentByArticleId(UUID id);
}
