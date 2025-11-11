package com.fullStack.to_do.list.service;

import com.fullStack.to_do.list.model.dto.CategoryResponseDto;
import com.fullStack.to_do.list.model.dto.CreateCategoryRequestDto;
import com.fullStack.to_do.list.model.dto.PageDto;


public interface CategoryService {

    PageDto<CategoryResponseDto> getAllCategory(long userId, int pageNo, int pageSize);
    CategoryResponseDto addCategory(CreateCategoryRequestDto dto, long userId);
    CategoryResponseDto updateCategory(CreateCategoryRequestDto dto, long categoryId, long userId);
    String deleteCategory(long categoryId, long userId);
}
