package org.example.jewelry_spring.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

@JsonRootName("category")
@JacksonXmlRootElement(localName = "category")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CategoryDto {
    private Long id;
    private String name;
}