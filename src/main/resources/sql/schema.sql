DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS departments CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS users_departments CASCADE;
DROP TABLE IF EXISTS phonenumbers CASCADE;

CREATE TABLE IF NOT EXISTS roles
(
    role_id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS departments
(
    department_id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    department_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users
(
    user_id        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_firstName VARCHAR(255) NOT NULL,
    user_lastName  VARCHAR(255) NOT NULL,
    role_id        BIGINT REFERENCES roles (role_id)
);

CREATE TABLE IF NOT EXISTS users_departments
(
    users_departments_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id              BIGINT REFERENCES users (user_id),
    department_id        BIGINT REFERENCES departments (department_id)
);

CREATE TABLE IF NOT EXISTS phonenumbers
(
    phonenumber_id     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    phonenumber_number VARCHAR(255) NOT NULL UNIQUE,
    user_id            BIGINT REFERENCES users (user_id)
);
