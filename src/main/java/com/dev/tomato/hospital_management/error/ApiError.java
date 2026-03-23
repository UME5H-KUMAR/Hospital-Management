package com.dev.tomato.hospital_management.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiError {

    private String error;
    private LocalDateTime timestamp;
    private HttpStatus statusCode;


    public ApiError(LocalDateTime timestamp){
        this.timestamp=LocalDateTime.now();
    }
    public ApiError(String error, HttpStatus statusCode){
        this.error= error;
        this.statusCode= statusCode;
    }
}
