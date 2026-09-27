package dev.lucasferraz.productmanager.services;

import dev.lucasferraz.productmanager.exceptions.BusinessRuleException;
import dev.lucasferraz.productmanager.exceptions.ResourceNotFoundException;
import dev.lucasferraz.productmanager.models.Category;
import dev.lucasferraz.productmanager.models.product.Product;
import dev.lucasferraz.productmanager.models.product.ProductRequestDTO;
import dev.lucasferraz.productmanager.models.product.ProductResponseDTO;
import dev.lucasferraz.productmanager.repositories.CategoryRepository;
import dev.lucasferraz.productmanager.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductResponseDTO getProductById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Produto com id %s not found", id))
        );
        return ProductResponseDTO.from(product);
    }

//    Regra de Negócio 1: Buscar a categoria por dto.categoryId(). Se não existir, lançar uma exceção de recurso não encontrado.
//    Regra de Negócio 2: Se a categoria existir mas category.isActive() == false, lançar uma exceção de regra de negócio (não pode criar produto com categoria inativa).
//    Persistir o produto e mapear para ProductResponseDTO.
    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) {
        Category category = categoryRepository.findById(productRequestDTO.categoryId()).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Categoria com id %s não encontrada", productRequestDTO.categoryId()))
        );
        if (!category.isActive()) {
            throw new BusinessRuleException(String.format("Não foi possível criar o produto, categoria %s está inativa.", category.getName()));
        }
        Product product = new Product(
                productRequestDTO.name(),
                productRequestDTO.description(),
                productRequestDTO.price(),
                productRequestDTO.stockQuantity(),
                category
        );
        product = productRepository.save(product);
        return ProductResponseDTO.from(product);
    }


}
