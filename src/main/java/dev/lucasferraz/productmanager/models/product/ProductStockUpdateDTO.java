package dev.lucasferraz.productmanager.models.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductStockUpdateDTO(
        @NotNull(message = "A quantidade é obrigatória")
        @Min(value = 0, message = "A quantidade de estoque não pode ser negativa")
        Integer quantity
        ) {
}
