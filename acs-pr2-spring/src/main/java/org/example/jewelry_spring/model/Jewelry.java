package org.example.jewelry_spring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "jewelry")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Jewelry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Введите название украшения")
    @Size(max = 255, message = "Максимум 255 символов")
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Size(max = 100, message = "Максимум 100 символов")
    @Column(name = "material", length = 100)
    private String material;

    @Min(value = 0, message = "Цена не может быть отрицательной")
    @Column(name = "price")
    private Double price;

    // Категория может быть null (если категорию удалили, украшение остается в БД)
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;
}