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

import java.time.Instant;
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



    public ProductResponseDTO getProductById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Produto com id %s not found", id))
        );
        return ProductResponseDTO.from(product);
    }


    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductResponseDTO::from).toList();
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
        return ProductResponseDTO.from(productRepository.save(product));
    }


    public ProductResponseDTO updateProduct(UUID id, ProductRequestDTO productRequestDTO) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Produto com id %s não encontrado", id))
        );
        product = Product.builder()
                .id(id)
                .name(productRequestDTO.name()!= null ? productRequestDTO.name() : product.getName())
                .description(productRequestDTO.description() != null ? productRequestDTO.description() : product.getDescription())
                .price(productRequestDTO.price() != null ? productRequestDTO.price() : product.getPrice())
                // nao é permitido atualizar o estoque nessa rota
                .stockQuantity(product.getStockQuantity())
                .category(product.getCategory())
                .createdAt(product.getCreatedAt())
                // atualiza o estado do update
                .updatedAt(Instant.now())
                .build();
        product = productRepository.save(product);
        return ProductResponseDTO.from(product);
    }


    public void deleteProduct(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException(String.format("Produto com id %s não encontrado", id))
        );
        productRepository.delete(product);
    }





}
