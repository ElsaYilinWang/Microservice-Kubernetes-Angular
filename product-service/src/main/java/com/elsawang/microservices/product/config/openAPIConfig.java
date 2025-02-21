package com.elsawang.microservices.product.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * OpenAPI (Swagger) Configuration for Product Service
 * This configuration class provides a centralized setup for API documentation and specifications
 * following the OpenAPI 3.0 standard.
 *
 * Features:
 * - Comprehensive API documentation
 * - Multiple environment support (dev/prod)
 * - JWT-based security configuration
 * - Logical API grouping with tags
 * - External documentation links
 */
@Configuration
@OpenAPIDefinition
public class OpenAPIConfig {

    // API Constants
    private static final String API_TITLE = "Product Service API";
    private static final String API_VERSION = "1.0.0";
    private static final String API_DESCRIPTION = "RESTful API for managing products in the microservices ecosystem";
    private static final String SECURITY_SCHEME_NAME = "bearer-jwt";
    
    // Documentation URLs
    private static final String GITHUB_REPO_URL = "https://github.com/ElsaYilinWang/Microservice-Kubernetes-Angular";
    private static final String WIKI_URL = GITHUB_REPO_URL + "/wiki";
    private static final String LICENSE_URL = "https://www.apache.org/licenses/LICENSE-2.0.html";
    private static final String TERMS_URL = "https://example.com/terms";

    @Value("${openapi.dev-url:http://localhost:8080}")
    private String devUrl;

    @Value("${openapi.prod-url:https://api.product-service.com}")
    private String prodUrl;

    /**
     * Primary configuration bean for OpenAPI documentation.
     * Assembles all components of the API documentation.
     *
     * @return Fully configured OpenAPI object
     */
    @Bean
    public OpenAPI productServiceAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(servers())
                .components(securityComponents())
                .security(securityRequirements())
                .tags(apiTags())
                .externalDocs(externalDocs());
    }

    /**
     * Configures API metadata including version, license, and contact details.
     * This information appears in the Swagger UI header.
     */
    private Info apiInfo() {
        return new Info()
                .title(API_TITLE)
                .description(API_DESCRIPTION)
                .version(API_VERSION)
                .contact(new Contact()
                        .name("Product Service Team")
                        .email("product-team@example.com")
                        .url(GITHUB_REPO_URL))
                .license(new License()
                        .name("Apache 2.0")
                        .url(LICENSE_URL))
                .termsOfService(TERMS_URL);
    }

    /**
     * Configures server environments for API testing.
     * Allows users to switch between development and production endpoints.
     */
    private List<Server> servers() {
        return Arrays.asList(
                new Server()
                        .url(devUrl)
                        .description("Development Server"),
                new Server()
                        .url(prodUrl)
                        .description("Production Server")
        );
    }

    /**
     * Configures JWT-based security components.
     * Sets up bearer token authentication for API endpoints.
     */
    private Components securityComponents() {
        SecurityScheme jwtScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("JWT token authentication")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        return new Components()
                .addSecuritySchemes(SECURITY_SCHEME_NAME, jwtScheme);
    }

    /**
     * Applies security requirements globally.
     * Makes JWT authentication mandatory for all endpoints unless explicitly overridden.
     */
    private List<SecurityRequirement> securityRequirements() {
        return Arrays.asList(
                new SecurityRequirement().addList(SECURITY_SCHEME_NAME)
        );
    }

    /**
     * Defines logical groupings for API endpoints.
     * These tags are used to organize endpoints in the Swagger UI.
     */
    private List<Tag> apiTags() {
        return Arrays.asList(
                createTag("Products", "Product management operations"),
                createTag("Categories", "Product category operations"),
                createTag("Inventory", "Product inventory operations")
        );
    }

    /**
     * Helper method to create API tags with consistent formatting
     */
    private Tag createTag(String name, String description) {
        return new Tag()
                .name(name)
                .description(description);
    }

    /**
     * Configures links to external documentation resources.
     * Provides additional references for API consumers.
     */
    private ExternalDocumentation externalDocs() {
        return new ExternalDocumentation()
                .description("Product Service Documentation")
                .url(WIKI_URL)
                .extension("x-api-version", API_VERSION);
    }
}
