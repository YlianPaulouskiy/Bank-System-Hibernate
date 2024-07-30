DROP TABLE IF EXISTS accounts CASCADE;
DROP TABLE IF EXISTS banks CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    id         bigint PRIMARY KEY AUTO_INCREMENT,
    name       varchar(128) NOT NULL,
    last_name  varchar(128) NOT NULL,
    user_type  varchar(1)   NOT NULL,
    start_work date,
    end_work   date
);


CREATE TABLE IF NOT EXISTS banks
(
    id    bigint PRIMARY KEY AUTO_INCREMENT,
    title varchar(128) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS accounts
(
    id      bigint PRIMARY KEY AUTO_INCREMENT,
    number  varchar(15)   NOT NULL,
    cash    decimal(7, 2) NOT NULL DEFAULT 0.00,
    user_id bigint        NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    bank_id bigint        NOT NULL REFERENCES banks (id) ON DELETE CASCADE
);