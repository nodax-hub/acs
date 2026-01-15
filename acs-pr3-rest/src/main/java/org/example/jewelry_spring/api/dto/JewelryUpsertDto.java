package org.example.jewelry_spring.api.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@JacksonXmlRootElement(localName = "jewelry")
@Getter
@Setter
public class JewelryUpsertDto {

    @NotBlank(message = "Введите название изделия")
    @Size(max = 300, message = "Максимум 300 символов")
    private String title;

    private String material;

    private Integer price;

    @NotNull(message = "Выберите категорию")
    private Long categoryId;
}