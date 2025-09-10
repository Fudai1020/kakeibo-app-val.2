CREATE DATABASE kakeibo_db;
USE kakeibo_db;
CREATE TABLE users(
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(50),
    user_email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(60) NOT NULL,
    user_memo VARCHAR(255),
    created_at DATETIME NOT NULL
);
CREATE TABLE incomes(
    income_id INT PRIMARY KEY AUTO_INCREMENT,
    income_name VARCHAR(50) NOT NULL,
    income_amount DECIMAL(10,2) NOT NULL,
    income_memo VARCHAR(255),
    is_private BOOLEAN NOT NULL,
    income_date DATETIME NOT NULL,
    created_at DATETIME NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
CREATE TABLE payment_categories(
    payment_category_id INT PRIMARY KEY AUTO_INCREMENT,
    payment_category_name VARCHAR(50) NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
CREATE TABLE payments(
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    payment_name VARCHAR(50) NOT NULL,
    payment_amount DECIMAL(10,2) NOT NULL,
    payment_memo VARCHAR(255),
    is_private BOOLEAN NOT NULL,
    payment_date DATETIME NOT NULL,
    created_at DATETIME NOT NULL,
    user_id INT,
    payment_category_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (payment_category_id) REFERENCES payment_categories(payment_category_id)
);
CREATE TABLE savings(
    saving_id INT PRIMARY KEY AUTO_INCREMENT,
    savings_name VARCHAR(50) NOT NULL,
    created_at DATETIME NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
CREATE TABLE saving_allocations(
    allocation_id INT PRIMARY KEY AUTO_INCREMENT,
    allocation_amount DECIMAL(10,2) NOT NULL,
    allocation_date DATE NOT NULL,
    created_at DATETIME NOT NULL,
    saving_id INT,
    FOREIGN KEY (saving_id) REFERENCES savings(saving_id)
 );
CREATE TABLE shared(
    share_id INT PRIMARY KEY AUTO_INCREMENT,
    owner_id INT NOT NULL,
    partner_id INT NOT NULL,
    started_at DATETIME NOT NULL,
    is_active BOOLEAN NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (partner_id) REFERENCES users(user_id) ON DELETE CASCADE
);
CREATE TABLE income_categories(
    income_category_id INT PRIMARY KEY AUTO_INCREMENT,
    income_category_name VARCHAR(50) NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
CREATE TABLE share_code(
    code_id INT PRIMARY KEY AUTO_INCREMENT,
    owner_id INT NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(owner_id) REFERENCES users(user_id) ON DELETE CASCADE
);