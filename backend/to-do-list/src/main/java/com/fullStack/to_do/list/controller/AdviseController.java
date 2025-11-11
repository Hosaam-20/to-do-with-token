package com.fullStack.to_do.list.controller;

import com.fullStack.to_do.list.exception.ResourceNotFoundException;
import com.fullStack.to_do.list.model.dto.ErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
public class AdviseController {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> handleNotFound(ResourceNotFoundException ex,
                                                   HttpServletRequest request) {
        ErrorDto error = new ErrorDto();
        error.setPath(request.getRequestURI());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setErrorMessage(ex.getMessage());
        error.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


}
