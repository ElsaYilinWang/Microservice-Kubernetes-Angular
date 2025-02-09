-- Create orders table with optimized column definitions and constraints
CREATE TABLE `t_orders` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,                    -- Primary key using BIGINT for large number of orders
    `order_number` VARCHAR(50) NOT NULL,                    -- Required field with reasonable length for order numbers
    `sku_code` VARCHAR(100) NOT NULL,                       -- Required field for product identification
    `price` DECIMAL(10,2) NOT NULL,                         -- Money values typically don't need more than 10 digits
    `quantity` INT UNSIGNED NOT NULL DEFAULT 1,             -- Positive integers only, default to 1
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Track when order was created
    PRIMARY KEY (`id`),
    INDEX `idx_order_number` (`order_number`),             -- Index for faster order lookups
    INDEX `idx_sku_code` (`sku_code`)                      -- Index for product-based queries
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;