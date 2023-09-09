package com.example.springandangular_group1_ams.model.respone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class BookmarkResponseWithPage <T>{
    private String message;
    private Integer status;
    private T payload;
    private Integer page;
    private Integer size;
    private Double totalElement;
    private Double totalPages;


}
