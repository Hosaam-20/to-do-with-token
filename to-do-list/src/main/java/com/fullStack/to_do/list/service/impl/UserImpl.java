package com.fullStack.to_do.list.service.impl;

import com.fullStack.to_do.list.exception.ResourceNotFoundException;
import com.fullStack.to_do.list.model.dto.CreateUserRequestDto;
import com.fullStack.to_do.list.model.dto.PageDto;
import com.fullStack.to_do.list.model.dto.UpdateUserRequestDto;
import com.fullStack.to_do.list.model.dto.UpdateUserResponseDto;
import com.fullStack.to_do.list.model.dto.UserDto;
import com.fullStack.to_do.list.model.entity.Role;
import com.fullStack.to_do.list.model.entity.UserEntity;
import com.fullStack.to_do.list.model.mapper.UserMapper;
import com.fullStack.to_do.list.repository.RoleRepository;
import com.fullStack.to_do.list.repository.UserRepository;
import com.fullStack.to_do.list.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;



@Service @RequiredArgsConstructor @Slf4j
public class UserImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override @Transactional(readOnly = true)
    public PageDto<UserDto> getAllUsers(int pageNo, int pageSize){

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<UserEntity> userPage = this.userRepository.findAll(pageable);

        List<UserDto> userDto= userPage.getContent().stream()
                .map(UserDto::toUserDto)
                .toList();

        PageDto<UserDto> pageDto = new PageDto<>();

        pageDto.setContent(userDto.stream().toList());
        log.info("content {}",pageDto.getContent());
        pageDto.setPageNo(userPage.getNumber());
        pageDto.setPageSize(userPage.getSize());
        pageDto.setTotalElements(userPage.getTotalElements());
        pageDto.setTotalPages(userPage.getTotalPages());
        pageDto.setIsLast(userPage.isLast());

        return pageDto;
    }

    @Override @Transactional(readOnly = true)
    public UserDto getUserById(long id) {

        UserEntity user = this.userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User Not Found!")
        );

        UserDto userDto = UserDto.toUserDto(user);
        return userDto;
    }

    @Override @Transactional(rollbackFor = Exception.class)
    public String createUser(CreateUserRequestDto dto) {

        UserEntity user = this.userMapper.froCreateUserRequestDtoToEntity(dto);

        UserEntity savedUser = new UserEntity();
        savedUser.setUsername(user.getUsername());
        savedUser.setEmail(user.getEmail());
        savedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        savedUser.setCreatedAt(user.getCreatedAt());

        Role role = this.roleRepository.findByRoleName(dto.getRole().getRoleName()).orElseThrow(
                () -> new ResourceNotFoundException("Role Not Found!")
        );

        savedUser.getRoles().add(role);

        this.userRepository.save(savedUser);
        return "User Was Added Successfully";

    }


    @Override @Transactional(rollbackFor = Exception.class)
    public UpdateUserResponseDto updateUser(UpdateUserRequestDto update, long id) {

        UserEntity isUserExists = this.userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User Not Found!")
        );

        isUserExists.setUsername(update.getUsername());
        isUserExists.setEmail(update.getEmail());

        this.userRepository.save(isUserExists);

        return this.userMapper.fromEntityToUpdateUserResponseDto(isUserExists);

    }

    //Soft Delete after build the whole project
    @Override @Transactional(rollbackFor = Exception.class)
    public String deleteUser(long id) {
        UserEntity isUserExists = this.userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User Not Found!")
        );

        this.userRepository.deleteById(id);
        return "User Was deleted Successfully!";
    }


}
