package dev.lucasferraz.productmanager.repositories;

import dev.lucasferraz.productmanager.models.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
