package com.fullStack.to_do.list.model.mapper;

import com.fullStack.to_do.list.model.dto.CreateUserRequestDto;
import com.fullStack.to_do.list.model.dto.UpdateUserResponseDto;
import com.fullStack.to_do.list.model.entity.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-03T16:34:27+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserEntity froCreateUserRequestDtoToEntity(CreateUserRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.username( dto.getUsername() );
        userEntity.email( dto.getEmail() );
        userEntity.password( dto.getPassword() );

        return userEntity.build();
    }

    @Override
    public UpdateUserResponseDto fromEntityToUpdateUserResponseDto(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UpdateUserResponseDto updateUserResponseDto = new UpdateUserResponseDto();

        updateUserResponseDto.setUserId( entity.getUserId() );
        updateUserResponseDto.setUsername( entity.getUsername() );
        updateUserResponseDto.setEmail( entity.getEmail() );
        updateUserResponseDto.setUpdatedAt( entity.getUpdatedAt() );

        return updateUserResponseDto;
    }
}
