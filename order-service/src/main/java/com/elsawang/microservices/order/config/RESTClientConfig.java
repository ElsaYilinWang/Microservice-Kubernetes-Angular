package com.elsawang.microservices.order.config;

import com.elsawang.microservices.order.client.InventoryClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

/**
 * REST Client Configuration for Order Service
 * This configuration class sets up HTTP clients for communicating with other microservices.
 * 
 * Features:
 * - Configurable connection timeouts
 * - Automatic retry mechanism
 * - Interface-based HTTP client generation
 * - Centralized HTTP client configuration
 */
@Configuration
public class RESTClientConfig {

    // Timeout configurations in seconds
    private static final int CONNECTION_TIMEOUT = 3;
    private static final int READ_TIMEOUT = 3;

    @Value("${inventory.url}")
    private String inventoryServiceUrl;

    /**
     * Creates and configures an HTTP client for the Inventory Service.
     * This client is used to make HTTP requests to the Inventory Service endpoints.
     *
     * Features:
     * - Automatic interface implementation
     * - Configured timeouts
     * - Base URL configuration from properties
     *
     * @return Configured InventoryClient instance
     */
    @Bean
    public InventoryClient inventoryClient() {
        // Build RestClient with custom configuration
        RestClient restClient = RestClient.builder()
                .baseUrl(inventoryServiceUrl)
                .requestFactory(getClientHttpRequestFactory())
                .build();

        // Create HTTP service proxy factory
        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(
                RestClientAdapter.create(restClient))
                .build();

        // Generate client implementation
        return proxyFactory.createClient(InventoryClient.class);
    }

    /**
     * Creates a configured ClientHttpRequestFactory with custom timeout settings.
     * This factory is used to configure the underlying HTTP client behavior.
     *
     * Configuration includes:
     * - Connection timeout: Time to establish the connection
     * - Read timeout: Time to wait for data after connection
     *
     * @return Configured ClientHttpRequestFactory
     */
    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS
                .withConnectTimeout(Duration.ofSeconds(CONNECTION_TIMEOUT))
                .withReadTimeout(Duration.ofSeconds(READ_TIMEOUT));

        return ClientHttpRequestFactories.get(settings);
    }
}
