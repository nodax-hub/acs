package org.example.jewelry_spring.api.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

@JacksonXmlRootElement(localName = "jewelry")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class JewelryDto {
    private Long id;
    private String title;
    private String material;
    private Integer price;
    private CategoryShortDto category; // может быть null если удалили категорию
}