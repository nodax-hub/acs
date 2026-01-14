package org.example.jewelry_spring.api.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.*;
import lombok.*;

import java.util.List;

@JacksonXmlRootElement(localName = "categories")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CategoriesXml {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "category")
    private List<CategoryDto> categories;
}