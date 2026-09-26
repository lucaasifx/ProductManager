package dev.lucasferraz.productmanager.repositories;

import dev.lucasferraz.productmanager.models.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
