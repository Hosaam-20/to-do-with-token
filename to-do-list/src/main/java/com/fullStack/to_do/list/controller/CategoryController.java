package com.fullStack.to_do.list.controller;

import com.fullStack.to_do.list.config.CustomUserDetails;
import com.fullStack.to_do.list.model.dto.CategoryResponseDto;
import com.fullStack.to_do.list.model.dto.CreateCategoryRequestDto;
import com.fullStack.to_do.list.model.dto.PageDto;
import com.fullStack.to_do.list.service.impl.CategoryImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryImpl categoryImpl;

    @GetMapping(path = "/")
    public ResponseEntity<PageDto<CategoryResponseDto>> getAllCategories(@RequestParam(value="pageNo", required = false, defaultValue = "0") @PositiveOrZero int pageNo,
                                                                         @RequestParam(value="pageSize", required = false, defaultValue = "5") @PositiveOrZero int pageSize,
                                                                         Authentication authentication){

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        long userId = userDetails.getId();
        System.out.println(userId);
        return ResponseEntity.ok(categoryImpl.getAllCategory(userId, pageNo, pageSize));
    }

    @PostMapping(path = "/")
    public ResponseEntity<CategoryResponseDto> addCategory(@RequestBody @Valid CreateCategoryRequestDto dto,
                                                           Authentication authentication){

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        long userId = userDetails.getId();
        return new ResponseEntity<>(this.categoryImpl.addCategory(dto, userId), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{categoryId}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@RequestBody @Valid CreateCategoryRequestDto dto,
                                                           @PathVariable("categoryId") long categoryId,
                                                           Authentication authentication){

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();
        long userId = userDetails.getId();


        return ResponseEntity
                .ok(categoryImpl.updateCategory(dto, categoryId, userId));
    }

    @DeleteMapping(path = "/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") long categoryId,
                                                              Authentication authentication){

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();
        long userId = userDetails.getId();

        return ResponseEntity
                .ok(categoryImpl.deleteCategory(categoryId, userId));
    }
}
