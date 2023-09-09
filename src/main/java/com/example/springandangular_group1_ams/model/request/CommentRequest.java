package com.example.springandangular_group1_ams.model.request;

import com.example.springandangular_group1_ams.model.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentRequest {
    private String caption;

    public Comment toEntity(){
        return new Comment(this.caption);
    }
}
