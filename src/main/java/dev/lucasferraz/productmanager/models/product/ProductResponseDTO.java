package dev.lucasferraz.productmanager.models.product;

import dev.lucasferraz.productmanager.models.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponseDTO(UUID id,
                                 String name,
                                 Long categoryId,
                                 BigDecimal price,
                                 String description,
                                 int stockQuantity) {

    public static ProductResponseDTO from(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getCategory().getId(),
                product.getPrice(),
                product.getDescription(),
                product.getStockQuantity()
        );
    }
}
