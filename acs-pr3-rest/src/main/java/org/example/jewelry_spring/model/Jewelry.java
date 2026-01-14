package org.example.jewelry_spring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Size(max = 300, message = "Максимум 300 символов")
    @Column(name = "title", nullable = false, length = 300)
    private String title;

    @Column(name = "price")
    private Integer price;

    @Size(max = 100, message = "Максимум 100 символов")
    @Column(name = "material", length = 100)
    private String material;

    // category может быть null
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;
}