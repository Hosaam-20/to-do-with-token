package com.fullStack.to_do.list.model.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class ErrorDto {
    private String path;
    private int status;
    private String errorMessage;
    private LocalDateTime timestamp;
}
