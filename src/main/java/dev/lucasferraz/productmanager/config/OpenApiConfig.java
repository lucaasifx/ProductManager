package dev.lucasferraz.productmanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ProductManager API")
                        .version("v1.0")
                        .description("API RESTful para gestão de produtos e categorias desenvolvida com Spring Boot, PostgreSQL no Docker, Flyway Migrations e RFC 7807.")
                        .contact(new Contact()
                                .name("Lucas Ferraz")));
    }
}
