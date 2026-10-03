package dev.lucasferraz.productmanager.models.product;


import dev.lucasferraz.productmanager.exceptions.BusinessRuleException;
import dev.lucasferraz.productmanager.models.category.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull()
    private BigDecimal price;

    private int stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    public Product(String name, String description, BigDecimal price, int stockQuantity, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }

    public void updateDetails(String name, String description, Category category) {
        if (name != null && !name.isBlank())
            this.name = name;
        if (description != null)
            this.description = description;
        if (category != null)
            this.category = category;
    }

    public void updatePrice(BigDecimal newPrice) {
        if (newPrice == null || newPrice.compareTo(BigDecimal.ZERO) <= 0)
            throw new BusinessRuleException("O preço do produto deve ser maior que zero.");
        this.price = newPrice;
    }

    public void updateStock(int newQuantity) {
        if (newQuantity < 0)
            throw new BusinessRuleException("A quantidade em estoque não pode ser negativa.");
        this.stockQuantity = newQuantity;
    }
}
