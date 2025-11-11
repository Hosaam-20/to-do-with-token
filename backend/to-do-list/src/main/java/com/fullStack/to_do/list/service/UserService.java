package com.fullStack.to_do.list.service;

import com.fullStack.to_do.list.model.dto.CreateUserRequestDto;
import com.fullStack.to_do.list.model.dto.PageDto;
import com.fullStack.to_do.list.model.dto.UpdateUserRequestDto;
import com.fullStack.to_do.list.model.dto.UpdateUserResponseDto;
import com.fullStack.to_do.list.model.dto.UserDto;

public interface UserService {

    PageDto<UserDto> getAllUsers(int pageNo, int pageSize);
    UserDto getUserById(long id);
    String  createUser(CreateUserRequestDto dto);
    UpdateUserResponseDto updateUser(UpdateUserRequestDto update, long id);
    String deleteUser(long id);
}
