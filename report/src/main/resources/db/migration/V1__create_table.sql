CREATE TABLE movements (
    id CHAR(36) NOT NULL PRIMARY KEY,
    service_type VARCHAR(50) NOT NULL,
    payment_type VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    location_id CHAR(36) NOT NULL,
    location_name VARCHAR(150) NOT NULL,
    created_at DATETIME NOT NULL
);
