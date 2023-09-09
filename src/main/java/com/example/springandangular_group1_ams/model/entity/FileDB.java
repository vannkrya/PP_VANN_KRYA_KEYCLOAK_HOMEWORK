package com.example.springandangular_group1_ams.model.entity;

import com.example.springandangular_group1_ams.model.dto.FileDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "file_tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDB {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String fileName;
    private String fileType;


    private String fileUrl;

    private Long size;

    public FileDto toDto(){
        return new FileDto(this.fileName,this.fileType,fileUrl,this.size );
    }

}