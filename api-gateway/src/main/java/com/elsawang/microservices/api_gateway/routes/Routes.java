package com.elsawang.microservices.api_gateway.routes;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

/**
 * Configuration class for API Gateway routes.
 * Defines routing rules and circuit breakers for microservices.
 * This class implements a centralized routing system with:
 * - Service-specific API routes with circuit breakers
 * - Swagger documentation routes
 * - Fallback handling for service failures
 */
@Configuration(proxyBeanMethods = false)
public class Routes {

    // Constants for common values to improve maintainability and reduce duplication
    private static final String FALLBACK_URI = "forward:/fallbackRoute";
    private static final String API_DOCS_PATH = "/api-docs";
    private static final String AGGREGATE_API_DOCS_FORMAT = "/aggregate/%s/v3/api-docs";
    
    // Service configuration constants
    private static final ServiceConfig[] SERVICES = {
        new ServiceConfig("product", "product-service", 8080),
        new ServiceConfig("order", "order-service", 8081),
        new ServiceConfig("inventory", "inventory-service", 8082)
    };

    /**
     * Creates routes for all microservices dynamically based on configuration
     */
    @SuppressWarnings("unchecked")
    @Bean
    public RouterFunction<ServerResponse>[] createAllRoutes() {
        RouterFunction<ServerResponse>[] routes = new RouterFunction[SERVICES.length * 2];
        for (int i = 0; i < SERVICES.length; i++) {
            ServiceConfig config = SERVICES[i];
            routes[i*2] = createServiceRoute(config.servicePath, config.port);
            routes[i*2 + 1] = createSwaggerRoute(config.swaggerName, config.port);
        }
        return routes;
    }

    /**
     * Fallback route handler for circuit breaker.
     * Provides a consistent error response when services are unavailable.
     */
    @Bean
    public RouterFunction<ServerResponse> fallbackRoute() {
        return route("fallbackRoute")
                .GET("/fallbackRoute", request -> 
                    ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body("Service Unavailable, please try again later"))
                .build();
    }

    /**
     * Helper method to create service routes with consistent configuration.
     * Implements circuit breaker pattern for fault tolerance.
     * 
     * @param serviceName name of the service (e.g., "product", "order")
     * @param port port number for the service
     */
    private RouterFunction<ServerResponse> createServiceRoute(String serviceName, int port) {
        String baseUrl = String.format("http://localhost:%d", port);
        return route(serviceName + "_service")
                .route(RequestPredicates.path("/api/" + serviceName), 
                      http(baseUrl))
                .filter(circuitBreaker(serviceName + "ServiceCircuitBreaker", 
                        URI.create(FALLBACK_URI)))
                .build();
    }

    /**
     * Helper method to create Swagger documentation routes with consistent configuration.
     * Provides API documentation access with circuit breaker protection.
     * 
     * @param serviceName name of the service (e.g., "product-service", "order-service")
     * @param port port number for the service
     */
    private RouterFunction<ServerResponse> createSwaggerRoute(String serviceName, int port) {
        String baseUrl = String.format("http://localhost:%d", port);
        return route(serviceName + "_swagger")
                .route(RequestPredicates.path(String.format(AGGREGATE_API_DOCS_FORMAT, serviceName)), 
                      http(baseUrl))
                .filter(circuitBreaker(serviceName + "SwaggerCircuitBreaker", 
                        URI.create(FALLBACK_URI)))
                .filter(setPath(API_DOCS_PATH))
                .build();
    }

    /**
     * Internal record to hold service configuration details.
     * Improves code organization and maintainability.
     */
    private record ServiceConfig(String servicePath, String swaggerName, int port) {}
}
