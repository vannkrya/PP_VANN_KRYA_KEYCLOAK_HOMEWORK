package com.example.springandangular_group1_ams.model.entity;

import com.example.springandangular_group1_ams.model.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue
    @UuidGenerator

    private UUID id;
    private String caption;
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
    public Comment(String caption) {
        this.caption = caption;
    }

    public CommentDto toDto(){
        return new CommentDto(this.id, this.caption);
    }

}
