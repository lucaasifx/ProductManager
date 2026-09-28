package dev.lucasferraz.productmanager.services;

import dev.lucasferraz.productmanager.exceptions.ResourceNotFoundException;
import dev.lucasferraz.productmanager.models.category.Category;
import dev.lucasferraz.productmanager.models.category.CategoryRequestDTO;
import dev.lucasferraz.productmanager.models.category.CategoryResponseDTO;
import dev.lucasferraz.productmanager.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDTO findCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Categoria com id %d não encontrada", id))
        );
        return CategoryResponseDTO.from(category);
    }

    public CategoryResponseDTO addCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = new Category(categoryRequestDTO.name(), categoryRequestDTO.description(), categoryRequestDTO.active());
        return  CategoryResponseDTO.from(categoryRepository.save(category));
    }

    public List<CategoryResponseDTO> findAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryResponseDTO::from).toList();
    }

    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Categoria com id %d não encontrada", id))
        );
        category = Category.builder()
                .id(id)
                .name(categoryRequestDTO.name() != null ? categoryRequestDTO.name() : category.getName())
                .description(categoryRequestDTO.description() != null ? categoryRequestDTO.description() : category.getDescription())
                .active(categoryRequestDTO.active())
                .createdAt(category.getCreatedAt())
                .updatedAt(Instant.now())
                .build();
        return CategoryResponseDTO.from(categoryRepository.save(category));

    }

    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }




}
