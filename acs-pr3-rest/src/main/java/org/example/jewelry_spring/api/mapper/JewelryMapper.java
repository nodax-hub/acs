package org.example.jewelry_spring.api.mapper;

import org.example.jewelry_spring.api.dto.CategoryShortDto;
import org.example.jewelry_spring.api.dto.JewelryDto;
import org.example.jewelry_spring.model.Category;
import org.example.jewelry_spring.model.Jewelry;

public class JewelryMapper {

    private JewelryMapper() {}

    public static JewelryDto toDto(Jewelry j) {
        Category c = j.getCategory();

        CategoryShortDto categoryDto = null;
        if (c != null) {
            categoryDto = CategoryShortDto.builder()
                    .id(c.getId())
                    .name(c.getName())
                    .build();
        }

        return JewelryDto.builder()
                .id(j.getId())
                .title(j.getTitle())
                .material(j.getMaterial())
                .price(j.getPrice())
                .category(categoryDto)
                .build();
    }
}