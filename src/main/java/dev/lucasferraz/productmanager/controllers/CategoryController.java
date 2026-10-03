package dev.lucasferraz.productmanager.controllers;

import dev.lucasferraz.productmanager.models.category.CategoryRequestDTO;
import dev.lucasferraz.productmanager.models.category.CategoryResponseDTO;
import dev.lucasferraz.productmanager.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Categorias", description = "Endpoints para gerenciamento de categorias de produtos")
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Lista todas as categorias", description = "Retorna uma lista com todas as categorias cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> findAllCategories() {
        return ResponseEntity.ok(categoryService.findAllCategories());
    }

    @Operation(summary = "Busca categoria por ID", description = "Retorna os detalhes de uma categoria pelo seu identificador numérico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> findCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findCategoryById(id));
    }

    @Operation(summary = "Cadastra uma nova categoria", description = "Cria uma nova categoria. O nome deve ser único no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "409", description = "Conflito: já existe uma categoria cadastrada com este nome")
    })
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> addCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.addCategory(categoryRequestDTO));
    }

    @Operation(summary = "Atualiza uma categoria existente", description = "Atualiza nome, descrição e status ativo de uma categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflito: nome já em uso por outra categoria")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id,
                                                              @Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryRequestDTO));
    }

    @Operation(summary = "Remove uma categoria por ID", description = "Exclui a categoria caso ela não possua produtos vinculados a ela")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Categoria removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflito de integridade: a categoria possui produtos vinculados e não pode ser excluída")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }
}
