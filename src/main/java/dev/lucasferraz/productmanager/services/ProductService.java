package dev.lucasferraz.productmanager.services;

import dev.lucasferraz.productmanager.exceptions.BusinessRuleException;
import dev.lucasferraz.productmanager.exceptions.ResourceNotFoundException;
import dev.lucasferraz.productmanager.models.category.Category;
import dev.lucasferraz.productmanager.models.product.Product;
import dev.lucasferraz.productmanager.models.product.ProductRequestDTO;
import dev.lucasferraz.productmanager.models.product.ProductResponseDTO;
import dev.lucasferraz.productmanager.repositories.CategoryRepository;
import dev.lucasferraz.productmanager.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO getProductById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Produto com id %s not found", id))
        );
        return ProductResponseDTO.from(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductResponseDTO::from).toList();
    }

    @Transactional
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
        return ProductResponseDTO.from(productRepository.save(product));
    }

    @Transactional
    public ProductResponseDTO updateProduct(UUID id, ProductRequestDTO productRequestDTO) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Produto com id %s não encontrado", id))
        );

        Category category = null;
        if (productRequestDTO.categoryId() != null) {
            category = categoryRepository.findById(productRequestDTO.categoryId()).orElseThrow(
                    () -> new ResourceNotFoundException(String.format("Categoria com id %s não encontrada", productRequestDTO.categoryId()))
            );
            if (!category.isActive()) {
                throw new BusinessRuleException(String.format("Não é possível associar o produto à categoria %s pois ela está inativa.", category.getName()));
            }
        }

        product.updateDetails(
                productRequestDTO.name(),
                productRequestDTO.description(),
                category
        );

        return ProductResponseDTO.from(product);
    }

    @Transactional
    public ProductResponseDTO updateProductPrice(UUID id, BigDecimal newPrice) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Produto com id %s não encontrado", id))
        );
        product.updatePrice(newPrice);
        return ProductResponseDTO.from(product);
    }

    @Transactional
    public ProductResponseDTO updateProductStock(UUID id, int newQuantity) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Produto com id %s não encontrado", id))
        );
        product.updateStock(newQuantity);
        return ProductResponseDTO.from(product);
    }

    @Transactional
    public void deleteProduct(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Produto com id %s não encontrado", id))
        );
        productRepository.delete(product);
    }
}
