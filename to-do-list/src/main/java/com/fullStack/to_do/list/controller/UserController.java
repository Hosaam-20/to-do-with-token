package com.fullStack.to_do.list.controller;

import com.fullStack.to_do.list.config.CustomUserDetails;
import com.fullStack.to_do.list.model.dto.CreateUserRequestDto;
import com.fullStack.to_do.list.model.dto.PageDto;
import com.fullStack.to_do.list.model.dto.UpdateUserRequestDto;
import com.fullStack.to_do.list.model.dto.UpdateUserResponseDto;

import com.fullStack.to_do.list.service.impl.UserImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(path = "api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserImpl userImpl;

//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/")
    public ResponseEntity<PageDto> getAllUsers(
            @RequestParam(value="pageNo", required = false, defaultValue = "0") @PositiveOrZero int pageNo,
            @RequestParam(value="pageSize", required = false, defaultValue = "5") @PositiveOrZero int pageSize){

        return ResponseEntity.ok(this.userImpl.getAllUsers(pageNo, pageSize));
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") @Min(1) long id){
        return ResponseEntity.ok(this.userImpl.getUserById(id));
    }

    //    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path = "/")
    public ResponseEntity<String> createUser(@RequestBody @Valid CreateUserRequestDto dto){

        return new ResponseEntity<>(this.userImpl.createUser(dto), HttpStatus.CREATED);
    }

//    @PreAuthorize("hasAuthority('USER')")
    @PutMapping(path = "/")
    public ResponseEntity<UpdateUserResponseDto> updateUser(@RequestBody @Valid UpdateUserRequestDto dto,
                                                             Authentication authentication){

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        long userId = userDetails.getId();

        return new ResponseEntity<>(this.userImpl.updateUser(dto, userId), HttpStatus.OK);
    }

//    //    @PreAuthorize("hasAuthority('ADMIN')")
//    @PutMapping(path = "/{id}")
//    public ResponseEntity<UpdateUserResponseDto> updateUsers(@RequestBody @Valid UpdateUserRequestDto dto,
//                                                            @PathVariable("id") @Min(1) long id){
//
//        return new ResponseEntity<>(this.userImpl.updateUser(dto, id), HttpStatus.OK);
//    }
//
//    //    @PreAuthorize("hasAuthority('ADMIN')")
//    @DeleteMapping(path = "/{id}")
//    public ResponseEntity<String> deleteUser(@PathVariable("id") @Min(1) long id){
//        return ResponseEntity.ok(this.userImpl.deleteUser(id));
//    }

}
