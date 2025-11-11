package com.fullStack.to_do.list.model.mapper;

import com.fullStack.to_do.list.model.dto.CategoryResponseDto;
import com.fullStack.to_do.list.model.dto.CreateCategoryRequestDto;
import com.fullStack.to_do.list.model.entity.Category;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponseDto toCategoryResponseDto(Category entity);

    Category toEntity(CreateCategoryRequestDto dto);
}
