package org.example.jewelry_spring.api.mapper;

import org.example.jewelry_spring.api.dto.CategoryDto;
import org.example.jewelry_spring.model.Category;

public class CategoryMapper {

    private CategoryMapper() {}

    public static CategoryDto toDto(Category c) {
        return CategoryDto.builder()
                .id(c.getId())
                .name(c.getName())
                .build();
    }
}