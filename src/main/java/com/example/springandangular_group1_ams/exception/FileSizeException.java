package com.example.springandangular_group1_ams.exception;

public class FileSizeException extends RuntimeException{
    public FileSizeException(String message){
        super(message);
    }
}
