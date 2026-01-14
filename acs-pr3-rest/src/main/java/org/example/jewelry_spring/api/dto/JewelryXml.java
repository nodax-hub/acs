package org.example.jewelry_spring.api.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

import java.util.List;

@JacksonXmlRootElement(localName = "jewelryList")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class JewelryXml {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "jewelry")
    private List<JewelryDto> jewelry;
}