-- Table for managing product inventory
-- Stores SKU codes and their corresponding quantities
CREATE TABLE `t_inventory` (
    -- Unique identifier for each inventory record
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    
    -- Stock Keeping Unit code - unique product identifier
    -- NOT NULL constraint added since SKU is essential
    `sku_code` VARCHAR(100) NOT NULL,
    
    -- Current quantity in stock
    -- NOT NULL with default 0 to prevent null quantities
    -- CHECK constraint ensures non-negative quantities
    `quantity` INT NOT NULL DEFAULT 0 CHECK (`quantity` >= 0),
    
    PRIMARY KEY (`id`),
    
    -- Index on sku_code for faster lookups
    -- UNIQUE constraint prevents duplicate SKUs
    UNIQUE INDEX `idx_sku_code` (`sku_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;