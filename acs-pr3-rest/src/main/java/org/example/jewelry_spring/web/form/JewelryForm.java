package org.example.jewelry_spring.web.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JewelryForm {

    @NotBlank(message = "Введите название украшения")
    @Size(max = 300, message = "Максимум 300 символов")
    private String title;

    private Integer price;

    @Size(max = 100, message = "Максимум 100 символов")
    private String material;

    private Long categoryId; // nullable
}