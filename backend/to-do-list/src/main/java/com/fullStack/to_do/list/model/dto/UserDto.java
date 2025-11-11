package com.fullStack.to_do.list.model.dto;


import com.fullStack.to_do.list.model.entity.Role;
import com.fullStack.to_do.list.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private Long userId;

    private String username;

    private String email;
    private Set<Role> roles;
    private LocalDateTime createdAt;


    public static UserDto toUserDto(UserEntity entity){

       return UserDto.builder()
                .userId(entity.getUserId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .roles(entity.getRoles())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
