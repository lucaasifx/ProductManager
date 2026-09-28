package dev.lucasferraz.productmanager.config;

import dev.lucasferraz.productmanager.models.category.Category;
import dev.lucasferraz.productmanager.repositories.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    public DataInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    public void run(String... args) {
        // Categoria 1: Ativa (para testarmos o caso de sucesso)
        Category catAtiva = new Category();
        catAtiva.setName("Eletrônicos");
        catAtiva.setDescription("Aparelhos e dispositivos eletrônicos");
        catAtiva.setActive(true);
        categoryRepository.save(catAtiva);
        // Categoria 2: Inativa (para testarmos a sua regra de negócio!)
        Category catInativa = new Category();
        catInativa.setName("Móveis Antigos");
        catInativa.setDescription("Móveis fora de linha");
        catInativa.setActive(false);
        categoryRepository.save(catInativa);
        System.out.println(">>> BANCO INICIALIZADO COM SUCESSO! Categoria 1 (Ativa) e Categoria 2 (Inativa) <<<");
    }
}
