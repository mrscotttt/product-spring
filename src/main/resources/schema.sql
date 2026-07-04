DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    phone    VARCHAR(255),
    website  VARCHAR(255)
);
