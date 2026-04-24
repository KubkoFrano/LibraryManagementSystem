CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'MEMBER'
);

CREATE TABLE books (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL,
    available BOOLEAN DEFAULT TRUE
);

CREATE TABLE loans (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    book_id BIGINT,
    due_date DATE,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books(id)
);


INSERT INTO users (username, password, role) VALUES 
('admin', 'admin', 'ADMIN'),
('user', 'user', 'MEMBER');

INSERT INTO books (title, author, available) VALUES 
('The Great Gatsby', 'F. Scott Fitzgerald', TRUE),
('1984', 'George Orwell', TRUE),
('The Hobbit', 'J.R.R. Tolkien', FALSE);

INSERT INTO loans (user_id, book_id, due_date) VALUES 
(2, 3, '2026-04-23');