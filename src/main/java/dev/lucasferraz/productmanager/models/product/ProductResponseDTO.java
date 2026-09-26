package dev.lucasferraz.productmanager.models.product;

import dev.lucasferraz.productmanager.models.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductResponseDTO(String name,
                                 Long categoryId,
                                 BigDecimal price,
                                 String description,
                                 int stockQuantity) {
}
