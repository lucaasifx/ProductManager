package dev.lucasferraz.productmanager.controllers;

import dev.lucasferraz.productmanager.models.product.ProductPriceUpdateDTO;
import dev.lucasferraz.productmanager.models.product.ProductRequestDTO;
import dev.lucasferraz.productmanager.models.product.ProductResponseDTO;
import dev.lucasferraz.productmanager.models.product.ProductStockUpdateDTO;
import dev.lucasferraz.productmanager.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Produtos", description = "Endpoints para gerenciamento do catálogo de produtos")
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Busca produto por ID", description = "Retorna os detalhes de um produto existente pelo seu UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(summary = "Lista todos os produtos", description = "Retorna uma lista com todos os produtos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(summary = "Cadastra um novo produto", description = "Cria um produto associado a uma categoria ativa. Rejeita se a categoria estiver inativa.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Categoria associada não encontrada"),
            @ApiResponse(responseCode = "422", description = "Violação de regra de negócio: categoria inativa")
    })
    @PostMapping
    public ResponseEntity<ProductResponseDTO> addProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(productRequestDTO));
    }

    @Operation(summary = "Atualiza dados cadastrais de um produto", description = "Atualiza nome, descrição e categoria. Preço e estoque são protegidos contra alteração indevida e possuem endpoints PATCH dedicados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Produto ou nova categoria não encontrados"),
            @ApiResponse(responseCode = "422", description = "Violação de regra de negócio: categoria inativa")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable UUID id,
                                                            @Valid @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productResponseDTO = productService.updateProduct(id, productRequestDTO);
        return ResponseEntity.ok(productResponseDTO);
    }

    @Operation(summary = "Atualiza o preço de um produto", description = "Endpoint exclusivo para reajuste de preço. Requer valor maior que zero.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Preço atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "422", description = "Violação de regra de negócio: preço deve ser positivo")
    })
    @PatchMapping("/{id}/price")
    public ResponseEntity<ProductResponseDTO> updateProductPrice(@PathVariable UUID id,
                                                                 @Valid @RequestBody ProductPriceUpdateDTO priceDto) {
        return ResponseEntity.ok(productService.updateProductPrice(id, priceDto.price()));
    }

    @Operation(summary = "Atualiza o estoque de um produto", description = "Endpoint exclusivo para movimentação e ajuste de estoque. Impede valores negativos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estoque atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "422", description = "Violação de regra de negócio: estoque não pode ser negativo")
    })
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductResponseDTO> updateProductStock(@PathVariable UUID id,
                                                                 @Valid @RequestBody ProductStockUpdateDTO quantityDto) {
        return ResponseEntity.ok(productService.updateProductStock(id, quantityDto.quantity()));
    }

    @Operation(summary = "Remove um produto", description = "Exclui permanentemente o produto correspondente ao UUID informado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
