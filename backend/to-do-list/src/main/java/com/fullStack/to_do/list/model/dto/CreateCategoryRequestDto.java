package com.fullStack.to_do.list.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCategoryRequestDto {

    @NotNull(message = "Category Must Not Be Null!")
    @NotBlank(message = "Category Must Not Be Blank!")
    private String categoryName;

}
