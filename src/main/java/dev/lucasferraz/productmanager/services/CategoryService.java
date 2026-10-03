package dev.lucasferraz.productmanager.services;

import dev.lucasferraz.productmanager.exceptions.ResourceNotFoundException;
import dev.lucasferraz.productmanager.models.category.Category;
import dev.lucasferraz.productmanager.models.category.CategoryRequestDTO;
import dev.lucasferraz.productmanager.models.category.CategoryResponseDTO;
import dev.lucasferraz.productmanager.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public CategoryResponseDTO findCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Categoria com id %d não encontrada", id))
        );
        return CategoryResponseDTO.from(category);
    }

    @Transactional
    public CategoryResponseDTO addCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = new Category(categoryRequestDTO.name(), categoryRequestDTO.description(), categoryRequestDTO.active());
        return CategoryResponseDTO.from(categoryRepository.save(category));
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryResponseDTO::from).toList();
    }

    @Transactional
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Categoria com id %d não encontrada", id))
        );
        category.updateDetails(categoryRequestDTO.name(), categoryRequestDTO.description(), categoryRequestDTO.active());
        return CategoryResponseDTO.from(category);
    }

    @Transactional
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}
