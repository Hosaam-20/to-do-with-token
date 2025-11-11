package com.fullStack.to_do.list.model.mapper;

import com.fullStack.to_do.list.model.dto.CategoryResponseDto;
import com.fullStack.to_do.list.model.dto.CreateCategoryRequestDto;
import com.fullStack.to_do.list.model.entity.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-03T16:34:27+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryResponseDto toCategoryResponseDto(Category entity) {
        if ( entity == null ) {
            return null;
        }

        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();

        categoryResponseDto.setCategoryId( entity.getCategoryId() );
        categoryResponseDto.setCategoryName( entity.getCategoryName() );

        return categoryResponseDto;
    }

    @Override
    public Category toEntity(CreateCategoryRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.categoryName( dto.getCategoryName() );

        return category.build();
    }
}
