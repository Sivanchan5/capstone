SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS adoptions;
DROP TABLE IF EXISTS dogs;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS shelters;

SET FOREIGN_KEY_CHECKS = 1;

-- Create Shelter Table
CREATE TABLE IF NOT EXISTS shelters (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(150) NOT NULL,
    address VARCHAR(255) NOT NULL,
    contact_number VARCHAR(20) NOT NULL
    );

-- Create Dog Table
CREATE TABLE IF NOT EXISTS dogs (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR(100) NOT NULL,
    breed VARCHAR(100),
    age INT,
    shelter_id BIGINT,
    image_url VARCHAR(255),
    FOREIGN KEY(shelter_id) REFERENCES shelters(id) ON DELETE SET NULL
    );

-- Create User Table
CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     user_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'USER'
    );

-- Create Adoption Table
CREATE TABLE IF NOT EXISTS adoptions (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         user_id BIGINT NOT NULL,
                                         dog_id BIGINT NOT NULL,
                                         adoption_date DATE,
                                         status VARCHAR(20) DEFAULT 'PENDING',
    FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY(dog_id) REFERENCES dogs(id) ON DELETE CASCADE
    );
