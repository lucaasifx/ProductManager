package dev.lucasferraz.productmanager.models.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDTO(
        @NotBlank
        String name,
        String description,
        boolean active) {
}
