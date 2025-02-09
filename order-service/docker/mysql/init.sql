-- Create databases for microservices if they don't exist
-- order_service: Handles order management and processing
-- inventory_service: Manages product inventory and stock levels
CREATE DATABASE IF NOT EXISTS order_service
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS inventory_service 
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;