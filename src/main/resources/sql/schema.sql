DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS departments CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS users_departments CASCADE;
DROP TABLE IF EXISTS phonenumbers CASCADE;

CREATE TABLE IF NOT EXISTS roles
(
    role_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name    VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS departments
(
    department_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name          VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    user_uuid uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    firstName VARCHAR(255) NOT NULL,
    lastName  VARCHAR(255) NOT NULL,
    role_id   BIGINT REFERENCES roles (role_id)
);

CREATE TABLE IF NOT EXISTS users_departments
(
    user_uuid     uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    department_id BIGINT REFERENCES departments (department_id)
);

CREATE TABLE IF NOT EXISTS phonenumbers
(
    phonenumber_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    number         VARCHAR(255) NOT NULL,
    user_id        uuid REFERENCES users (user_uuid)
);
