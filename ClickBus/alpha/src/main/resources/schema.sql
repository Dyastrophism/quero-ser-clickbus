CREATE TABLE IF NOT EXISTS place (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL
);