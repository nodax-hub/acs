package org.example.jewelry_spring.api.web;

import org.example.jewelry_spring.api.dto.JewelryDto;
import org.example.jewelry_spring.api.dto.JewelryUpsertDto;
import org.example.jewelry_spring.api.dto.JewelryXml;
import org.example.jewelry_spring.api.mapper.JewelryMapper;
import org.example.jewelry_spring.api.support.XmlWithXsltService;
import org.example.jewelry_spring.model.Category;
import org.example.jewelry_spring.model.Jewelry;
import org.example.jewelry_spring.service.CategoryService;
import org.example.jewelry_spring.service.JewelryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jewelry")
@Tag(name = "Jewelry REST", description = "CRUD для ювелирных изделий. JSON и XML поддерживаются.")
public class JewelryRestController {

    private final JewelryService jewelryService;
    private final CategoryService categoryService;
    private final XmlWithXsltService xml;

    private boolean wantsXml(String format, HttpServletRequest request) {
        if (format != null && format.equalsIgnoreCase("xml")) return true;

        String accept = request.getHeader("Accept");
        return accept != null && accept.toLowerCase().contains("xml");
    }

    @Operation(
            summary = "Список ювелирных изделий",
            description = """
                    Возвращает список изделий с категорией.
                    - JSON: объект-обёртка (JewelryXml)
                    - XML: XML + XSL (?format=xml)
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = JewelryXml.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(
                            type = "string",
                            description = "XML строка с XSL processing-instruction"
                    ))
            })
    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> list(
            @Parameter(in = ParameterIn.QUERY, name = "format", example = "xml",
                    description = "Если format=xml — отдаём XML + XSL")
            @RequestParam(value = "format", required = false) String format,
            HttpServletRequest request
    ) {
        List<JewelryDto> items = jewelryService.findAllWithCategory().stream()
                .map(JewelryMapper::toDto)
                .toList();

        JewelryXml payload = new JewelryXml(items);

        if (wantsXml(format, request)) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_XML)
                    .body(xml.toXmlWithXslt(payload, "/xsl/jewelry-list.xsl"));
        }
        return ResponseEntity.ok(payload);
    }

    @Operation(
            summary = "Получить изделие по id",
            description = "Возвращает JewelryDto или XML + XSL"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = JewelryDto.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(
                            type = "string",
                            description = "XML строка с XSL processing-instruction"
                    ))
            }),
            @ApiResponse(responseCode = "404", description = "Изделие не найдено")
    })
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> get(
            @PathVariable Long id,
            @Parameter(in = ParameterIn.QUERY, name = "format", example = "xml")
            @RequestParam(value = "format", required = false) String format,
            HttpServletRequest request
    ) {
        Jewelry entity = jewelryService.getByIdWithCategory(id);
        JewelryDto dto = JewelryMapper.toDto(entity);

        if (wantsXml(format, request)) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_XML)
                    .body(xml.toXmlWithXslt(dto, "/xsl/jewelry.xsl"));
        }
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Создать изделие")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = JewelryDto.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(
                            type = "string",
                            description = "XML строка с XSL processing-instruction"
                    ))
            }),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
            @ApiResponse(responseCode = "404", description = "Категория не найдена")
    })
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> create(
            @Valid @RequestBody JewelryUpsertDto body,
            @RequestParam(value = "format", required = false) String format,
            HttpServletRequest request
    ) {
        Category category = categoryService.getById(body.getCategoryId());

        Jewelry toCreate = Jewelry.builder()
                .title(body.getTitle())
                .price(body.getPrice())
                .material(body.getMaterial())
                .category(category)
                .build();

        Jewelry created = jewelryService.create(toCreate);
        JewelryDto dto = JewelryMapper.toDto(jewelryService.getByIdWithCategory(created.getId()));

        URI location = URI.create("/api/jewelry/" + created.getId());

        if (wantsXml(format, request)) {
            return ResponseEntity.created(location)
                    .contentType(MediaType.APPLICATION_XML)
                    .body(xml.toXmlWithXslt(dto, "/xsl/jewelry.xsl"));
        }
        return ResponseEntity.created(location).body(dto);
    }

    @Operation(summary = "Обновить изделие")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = JewelryDto.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(
                            type = "string",
                            description = "XML строка с XSL processing-instruction"
                    ))
            }),
            @ApiResponse(responseCode = "404", description = "Изделие или категория не найдены"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    })
    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody JewelryUpsertDto body,
            @RequestParam(value = "format", required = false) String format,
            HttpServletRequest request
    ) {
        Category category = categoryService.getById(body.getCategoryId());

        Jewelry patch = Jewelry.builder()
                .title(body.getTitle())
                .price(body.getPrice())
                .material(body.getMaterial())
                .category(category)
                .build();

        jewelryService.update(id, patch);

        JewelryDto dto = JewelryMapper.toDto(jewelryService.getByIdWithCategory(id));

        if (wantsXml(format, request)) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_XML)
                    .body(xml.toXmlWithXslt(dto, "/xsl/jewelry.xsl"));
        }
        return ResponseEntity.ok(dto);
    }@Operation(summary = "Удалить изделие")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "Изделие не найдено")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        jewelryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}