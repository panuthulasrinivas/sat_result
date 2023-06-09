package com.srinu.example.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResultException extends RuntimeException {


    HttpStatus httpStatus;
    String message;

    public ResultException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
