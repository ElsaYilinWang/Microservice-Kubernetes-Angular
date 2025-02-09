-- Initial inventory data population
-- Adding popular smartphone models with initial stock of 100 units each
INSERT INTO t_inventory (quantity, sku_code) 
VALUES 
    -- Apple's latest iPhone
    (100, 'iphone_15'),
    -- Google's Pixel phone  
    (100, 'pixel_8'),
    -- Samsung's Galaxy phone
    (100, 'galaxy_24'), 
    -- OnePlus flagship phone
    (100, 'oneplus_12');