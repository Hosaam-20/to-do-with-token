package com.fullStack.to_do.list.service.impl;


import com.fullStack.to_do.list.exception.ResourceNotFoundException;
import com.fullStack.to_do.list.model.dto.CategoryResponseDto;
import com.fullStack.to_do.list.model.dto.CreateCategoryRequestDto;
import com.fullStack.to_do.list.model.dto.PageDto;
import com.fullStack.to_do.list.model.entity.Category;
import com.fullStack.to_do.list.model.entity.UserEntity;
import com.fullStack.to_do.list.model.mapper.CategoryMapper;
import com.fullStack.to_do.list.repository.CategoryRepository;
import com.fullStack.to_do.list.repository.UserRepository;
import com.fullStack.to_do.list.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service @RequiredArgsConstructor @Slf4j
public class CategoryImpl implements CategoryService {


    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final UserRepository userRepository;

    @Override @Transactional(readOnly = true)
    public PageDto<CategoryResponseDto> getAllCategory(long userId, int pageNo,
                                                       int pageSize) {

        UserEntity isUserExist = this.userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User Not Found!")
        );

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Category> pageCategory = this.categoryRepository.getAllCategoryForSpecificUser(userId, pageable);


        List<CategoryResponseDto> responseDto = pageCategory.getContent()
                .stream()
                .map(c -> {
                    CategoryResponseDto dto = new CategoryResponseDto();
                    dto.setCategoryId(c.getCategoryId());
                    dto.setCategoryName(c.getCategoryName());
                    return dto;
                })
                .toList();

        PageDto<CategoryResponseDto> pageDto = new PageDto<>();
        pageDto.setContent(responseDto.stream().toList());
        pageDto.setPageNo(pageCategory.getNumber());
        pageDto.setPageSize(pageCategory.getSize());
        pageDto.setTotalPages(pageCategory.getTotalPages());
        pageDto.setTotalElements(pageCategory.getTotalElements());
        pageDto.setIsLast(pageCategory.isLast());

        return pageDto;
    }



    @Override @Transactional(rollbackFor = Exception.class)
    public CategoryResponseDto addCategory(CreateCategoryRequestDto dto, long userId) {

        UserEntity isUserExists = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));

        Category category = this.categoryMapper.toEntity(dto);
        Category savedCategory = this.categoryRepository.save(category);
        isUserExists.getCategories().add(savedCategory);

        return this.categoryMapper.toCategoryResponseDto(category);
    }

    @Override
    public CategoryResponseDto updateCategory(CreateCategoryRequestDto dto, long categoryId, long userId) {
        return null;
    }

    @Override
    public String deleteCategory(long categoryId, long userId) {
        return "";
    }

//    @Override @Transactional(rollbackFor = Exception.class)
//    public CategoryResponseDto updateCategory(CreateCategoryRequestDto dto, long categoryId, long userId) {
//
//        UserEntity isUserExists = this.userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));
//
//
//        Category isExistsCategory = this.categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found!"));
//
//
//        if(!isExistsCategory.getUserEntity().getUserId().equals(userId)){
//            new ResponseEntity<>("You hav Not The right for access", HttpStatus.FORBIDDEN);
//        }
//        isExistsCategory.setCategoryName(dto.getCategoryName());
//        this.categoryRepository.save(isExistsCategory);
//        return this.categoryMapper.toCategoryResponseDto(isExistsCategory);
//    }
//
//    @Override @Transactional(rollbackFor = Exception.class)
//    public String deleteCategory(long categoryId, long userId) {
//
//        UserEntity isUserExists = this.userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));
//
//        Category isExistsCategory = this.categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found!"));
//
//        this.categoryRepository.deleteById(categoryId);
//        return "Category "+ isExistsCategory.getCategoryName() +" Was Deleted Successfully";
//    }

}
