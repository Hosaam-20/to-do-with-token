package com.fullStack.to_do.list.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserResponseDto {

    private Long userId;

    private String username;

    private String email;

    private LocalDateTime updatedAt;

}
