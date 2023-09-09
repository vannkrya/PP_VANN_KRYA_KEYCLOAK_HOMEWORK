package com.example.springandangular_group1_ams.service;

import com.example.springandangular_group1_ams.model.dto.FileDto;
import com.example.springandangular_group1_ams.model.entity.FileDB;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FileService {
    FileDto uploadFile(MultipartFile file) throws IOException;

    List<FileDto> multipleUploadFile(MultipartFile[] files) throws IOException;

    Resource downloadFiles(String fileName) throws IOException;
}
