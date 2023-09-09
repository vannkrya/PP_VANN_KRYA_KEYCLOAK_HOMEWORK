package com.example.springandangular_group1_ams.service.serviceImpl;

import com.example.springandangular_group1_ams.exception.FileSizeException;
import com.example.springandangular_group1_ams.exception.NotFoundExceptionClass;
import com.example.springandangular_group1_ams.model.dto.FileDto;
import com.example.springandangular_group1_ams.model.entity.FileDB;
import com.example.springandangular_group1_ams.repository.FileRepository;
import com.example.springandangular_group1_ams.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImp implements FileService {

    private final FileRepository fileRepository;

    private final Path root = Paths.get("src/main/resources/images");


    private static long maxSize = 1024 * 1024;
    @Value("${storeFile}")
    private String storeFile;

    /* Upload File */
    @Override
    public FileDto uploadFile(MultipartFile file) throws IOException {
        try{
            String fileName = file.getOriginalFilename();
            if(fileName !=null ){

                if(!Files.exists(root)){
                    Files.createDirectories(root);
                }
                try{
                    if(file.getSize()>maxSize){
                        throw new FileSizeException("File is too large");
                    }
                }catch(FileSizeException ex){
                    ex.printStackTrace();
                    throw ex;
                }
                FileDB fileDB = new FileDB();
                fileDB.setFileName(fileName);
                fileDB.setFileType(file.getContentType());
                fileDB.setFileUrl(storeFile + StringUtils.cleanPath(file.getOriginalFilename()));
                fileDB.setSize(file.getSize());

                Files.copy(file.getInputStream(), root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                return fileRepository.save(fileDB).toDto();
            }else{
                throw new IOException("File not be empty");
            }
        }catch(IOException ex){
            ex.printStackTrace();
            throw ex;
        }
    }

    /* Upload Multiple File */
    @Override
    public List<FileDto> multipleUploadFile(MultipartFile[] files) throws IOException {
        List<FileDto> fileDBList = new ArrayList<>();
        for (MultipartFile file : files) {
           fileDBList.add(uploadFile(file));
        }
        return fileDBList;
    }

    /* Download File */
    @Override
    public Resource downloadFiles(String fileName) throws RuntimeException {
        try {
            Path filePath = this.root.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new NotFoundExceptionClass("File not found");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
