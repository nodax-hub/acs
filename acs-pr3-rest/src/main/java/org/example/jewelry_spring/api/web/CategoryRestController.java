package org.example.jewelry_spring.api.web;

import org.example.jewelry_spring.api.dto.*;
import org.example.jewelry_spring.api.mapper.CategoryMapper;
import org.example.jewelry_spring.api.mapper.JewelryMapper;
import org.example.jewelry_spring.api.support.XmlWithXsltService;
import org.example.jewelry_spring.model.Category;
import org.example.jewelry_spring.service.CategoryService;
import org.example.jewelry_spring.service.JewelryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
@Tag(name = "Categories REST", description = "CRUD категорий + получение ювелирки категории. JSON и XML поддерживаются.")
public class CategoryRestController {

    private final CategoryService categoryService;
    private final JewelryService jewelryService;
    private final XmlWithXsltService xml;

    private boolean wantsXml(String format, HttpServletRequest request) {
        if (format != null && format.equalsIgnoreCase("xml")) return true;

        String accept = request.getHeader("Accept");
        return accept != null && accept.toLowerCase().contains("xml");
    }

    @Operation(summary = "Список категорий")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoriesXml.class)),
                    @Content(mediaType = "application/xml",
                            schema = @Schema(type = "string", description = "XML строка с XSL processing-instruction"))
            })
    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> list(
            @Parameter(in = ParameterIn.QUERY, name = "format", example = "xml")
            @RequestParam(value = "format", required = false) String format,
            HttpServletRequest request
    ) {
        List<CategoryDto> categories = categoryService.findAll().stream()
                .map(CategoryMapper::toDto)
                .toList();

        CategoriesXml payload = new CategoriesXml(categories);

        if (wantsXml(format, request)) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_XML)
                    .body(xml.toXmlWithXslt(payload, "/xsl/categories.xsl"));
        }
        return ResponseEntity.ok(payload);
    }

    @Operation(summary = "Получить категорию по id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDto.class)),
                    @Content(mediaType = "application/xml",
                            schema = @Schema(type = "string", description = "XML строка с XSL processing-instruction"))
            }),
            @ApiResponse(responseCode = "404", description = "Категория не найдена")
    })
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> get(
            @PathVariable Long id,
            @Parameter(in = ParameterIn.QUERY, name = "format", example = "xml")
            @RequestParam(required = false) String format,
            HttpServletRequest request
    ) {
        Category category = categoryService.getById(id);
        CategoryDto dto = CategoryMapper.toDto(category);
        if (wantsXml(format, request)) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_XML)
                    .body(xml.toXmlWithXslt(dto, "/xsl/category.xsl"));
        }
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Ювелирка категории")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JewelryXml.class)),
                    @Content(mediaType = "application/xml",
                            schema = @Schema(type = "string", description = "XML строка с XSL processing-instruction"))
            }),
            @ApiResponse(responseCode = "404", description = "Категория не найдена")
    })
    @GetMapping(value = "/{id}/jewelry", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> jewelryOfCategory(
            @PathVariable Long id,
            @Parameter(in = ParameterIn.QUERY, name = "format", example = "xml")
            @RequestParam(required = false) String format,
            HttpServletRequest request
    ) {
        categoryService.getById(id); // проверка 404

        List<JewelryDto> jewelry = jewelryService.findAllByCategoryIdWithCategory(id).stream()
                .map(JewelryMapper::toDto)
                .toList();

        JewelryXml payload = new JewelryXml(jewelry);

        if (wantsXml(format, request)) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_XML)
                    .body(xml.toXmlWithXslt(payload, "/xsl/jewelry-list.xsl"));
        }
        return ResponseEntity.ok(payload);
    }

    @Operation(summary = "Создать категорию")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDto.class)),
                    @Content(mediaType = "application/xml",
                            schema = @Schema(type = "string", description = "XML строка с XSL processing-instruction"))
            }),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    })
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> create(
            @Valid @RequestBody CategoryUpsertDto body,
            @Parameter(in = ParameterIn.QUERY, name = "format",
                    description = "Если указать format=xml — отдаём XML с XSL (для браузера)",
                    example = "xml")
            @RequestParam(value = "format", required = false) String format,
            HttpServletRequest request
    ) {
        Category toCreate = Category.builder()
                .name(body.getName())
                .build();

        Category created = categoryService.create(toCreate);
        CategoryDto dto = CategoryMapper.toDto(created);
        URI location = URI.create("/api/categories/" + created.getId());

        if (wantsXml(format, request)) {
            return ResponseEntity.created(location)
                    .contentType(MediaType.APPLICATION_XML)
                    .body(xml.toXmlWithXslt(dto, "/xsl/category.xsl"));
        }
        return ResponseEntity.created(location).body(dto);
    }

    @Operation(summary = "Обновить категорию")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDto.class)),
                    @Content(mediaType = "application/xml",
                            schema = @Schema(type = "string", description = "XML строка с XSL processing-instruction"))
            }),
            @ApiResponse(responseCode = "404", description = "Категория не найдена"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    })
    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryUpsertDto body,
            @RequestParam(required = false) String format,
            HttpServletRequest request
    ) {
        Category patch = Category.builder()
                .name(body.getName())
                .build();

        Category updated = categoryService.update(id, patch);
        CategoryDto dto = CategoryMapper.toDto(updated);

        if (wantsXml(format, request)) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_XML)
                    .body(xml.toXmlWithXslt(dto, "/xsl/category.xsl"));
        }
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Удалить категорию")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "Категория не найдена")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}