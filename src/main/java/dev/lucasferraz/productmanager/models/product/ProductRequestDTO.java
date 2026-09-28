package dev.lucasferraz.productmanager.models.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductRequestDTO(
        @NotBlank(message = "Nome não pode ser nulo")
        String name,

        String description,

        @Positive(message = "Preço não pode ser negativo ou zero")
        @NotNull(message = "Preço não pode ser nulo")
        BigDecimal price,

        @PositiveOrZero(message = "A quantidade não pode ser negativa")
        int stockQuantity,

        @NotNull(message = "Id da categoria não pode ser nulo")
        Long categoryId
        ) {

}
