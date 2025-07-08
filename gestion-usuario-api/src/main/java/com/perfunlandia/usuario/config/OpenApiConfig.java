package com.perfunlandia.usuario.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestión de Usuarios - Perfunlandia")
                        .description("Microservicio para la gestión de usuarios (clientes y vendedores) con autenticación JWT")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo Perfunlandia")
                                .email("desarrollo@perfunlandia.com")
                                .url("https://perfunlandia.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8082").description("Servidor de desarrollo"),
                        new Server().url("https://api.perfunlandia.com").description("Servidor de producción")
                ));
    }
} 