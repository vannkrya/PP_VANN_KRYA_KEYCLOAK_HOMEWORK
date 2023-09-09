package com.example.springandangular_group1_ams.model.request;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor@NoArgsConstructor
public class BookmarkRequest {

    private UUID articleId;
}
