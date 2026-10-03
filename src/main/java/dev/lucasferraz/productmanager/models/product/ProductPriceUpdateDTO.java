package dev.lucasferraz.productmanager.models.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductPriceUpdateDTO(
        @NotNull(message = "O preço é obrigatório")
        @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
        BigDecimal price
        ) {
}
