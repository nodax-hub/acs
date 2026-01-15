package org.example.jewelry_spring.web.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JewelryForm {
    @NotBlank(message = "Введите название украшения")
    @Size(max = 255, message = "Максимум 255 символов")
    private String title;

    @Size(max = 100, message = "Максимум 100 символов")
    private String material;

    @Min(value = 0, message = "Цена не может быть отрицательной")
    private Double price;

    private Long categoryId;
}