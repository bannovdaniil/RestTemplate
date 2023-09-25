DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS departments CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS users_departments CASCADE;
DROP TABLE IF EXISTS phone_numbers CASCADE;

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
    department_id        BIGINT REFERENCES departments (department_id),
    CONSTRAINT unique_link UNIQUE (user_id, department_id)
);

CREATE TABLE IF NOT EXISTS phone_numbers
(
    phonenumber_id     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    phonenumber_number VARCHAR(255) NOT NULL UNIQUE,
    user_id            BIGINT REFERENCES users (user_id)
);

INSERT INTO roles (role_name)
VALUES ('Администратор'),        -- 1
       ('Технический директор'), -- 2
       ('Программист Java'),     -- 3
       ('Программист React'),    -- 4
       ('HR'); -- 5

INSERT INTO departments (department_name)
VALUES ('Администрация'),       -- 1
       ('BackEnd разработка'),  -- 2
       ('Frontend разработка'), -- 3
       ('HR менеджмент'); -- 4

INSERT INTO users (user_firstName, user_lastName, role_id)
VALUES ('Иван', 'Субботин', 1),      -- 1
       ('Петр', 'Понедельников', 2), -- 2
       ('Игнат', 'Вторников', 3),    -- 3
       ('Иван', 'Середец', 3),       -- 4
       ('Максим', 'Четверкин', 3),   -- 5
       ('Вера', 'Пятницкая', 4),     -- 6
       ('Ольга', 'Воскресенская', 5); -- 7

INSERT INTO users_departments (user_id, department_id)
VALUES (1, 1), -- 1
       (2, 1), -- 2
       (3, 2), -- 3
       (4, 2), -- 4
       (5, 2), -- 5
       (6, 1), -- 6
       (6, 3), -- 6
       (7, 4); -- 7

INSERT INTO phone_numbers (phonenumber_number, user_id)
VALUES ('+1(123)123 1111', 1), -- 1
       ('+1(123)123 2222', 1), -- 2
       ('+1(123)123 3333', 2), -- 3
       ('+1(123)123 4444', 2), -- 4
       ('+1(123)123 5555', 3), -- 5
       ('+1(123)123 6666', 4), -- 6
       ('+1(123)123 7777', 5), -- 7
       ('+1(123)123 8888', 6), -- 8
       ('+1(123)123 9995', 7); -- 9

