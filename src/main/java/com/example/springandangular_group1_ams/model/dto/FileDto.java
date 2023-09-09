package com.example.springandangular_group1_ams.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FileDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)

    private String fileName;
    private String fileType;
    private String fileUrl;
    private Long size;

    public FileDto(String fileName, String fileType, String fileUrl, Long size) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileUrl = fileUrl;
        this.size = size;
    }

}
