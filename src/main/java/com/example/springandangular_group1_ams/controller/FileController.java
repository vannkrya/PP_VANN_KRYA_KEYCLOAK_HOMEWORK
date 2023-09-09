package com.example.springandangular_group1_ams.controller;

import com.example.springandangular_group1_ams.model.dto.FileDto;
import com.example.springandangular_group1_ams.service.serviceImpl.FileServiceImp;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/file")
public class FileController {
    private final FileServiceImp fileServiceImp;

    public FileController(FileServiceImp fileServiceImp) {
        this.fileServiceImp = fileServiceImp;
    }


    @PostMapping(value = "file-upload" ,consumes = {"multipart/form-data"})
    public ResponseEntity<FileDto> uploadFile(@RequestPart("file") MultipartFile file) throws IOException {
            FileDto fileDto = fileServiceImp.uploadFile(file);
        return ResponseEntity.ok().body(fileDto);
    }


    @GetMapping("/downloadFile/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileName") String fileName) throws Exception {
        Resource file = fileServiceImp.downloadFiles(fileName);

        String contentDisposition = "attachment; filename=\"" + file.getFilename() + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }

    @PostMapping(value = "uploadMultipleFiles", consumes = {"multipart/form-data"})
    public ResponseEntity<List<FileDto>> uploadMultipleFiles(@RequestPart("multipartFiles") MultipartFile[] multipartFiles) throws IOException {
        List<FileDto> listFile = fileServiceImp.multipleUploadFile(multipartFiles);
        return ResponseEntity.ok().body(listFile);
    }


}