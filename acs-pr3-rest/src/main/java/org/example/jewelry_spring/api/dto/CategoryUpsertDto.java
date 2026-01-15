package org.example.jewelry_spring.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@JsonRootName("category")
@JacksonXmlRootElement(localName = "category")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CategoryUpsertDto {

    @NotBlank(message = "Введите название категории")
    @Size(max = 200, message = "Максимум 200 символов")
    private String name;
}