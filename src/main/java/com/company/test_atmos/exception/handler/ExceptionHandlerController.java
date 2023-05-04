package com.company.test_atmos.exception.handler;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class,
            FileUploadException.class, SQLException.class})
    public ResponseEntity<?> handleBadRequestException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
