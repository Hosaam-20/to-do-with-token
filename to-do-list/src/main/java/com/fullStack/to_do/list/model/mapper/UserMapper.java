package com.fullStack.to_do.list.model.mapper;

import com.fullStack.to_do.list.model.dto.CreateUserRequestDto;
import com.fullStack.to_do.list.model.dto.UpdateUserResponseDto;

import com.fullStack.to_do.list.model.entity.UserEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {


     UserEntity froCreateUserRequestDtoToEntity(CreateUserRequestDto dto);

     UpdateUserResponseDto fromEntityToUpdateUserResponseDto(UserEntity entity);
}

