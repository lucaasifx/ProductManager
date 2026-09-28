package dev.lucasferraz.productmanager.models.category;

import dev.lucasferraz.productmanager.models.product.ProductRequestDTO;

public record CategoryResponseDTO(Long id,
                                  String name,
                                  String description,
                                  boolean active) {

    public static CategoryResponseDTO from(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.isActive()
        );
    }

}
