CREATE TABLE IF NOT EXISTS regions
(
    id   BIGINT      NOT NULL AUTO_INCREMENT UNIQUE,
    name varchar(50) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS writers
(
    id         BIGINT      NOT NULL AUTO_INCREMENT UNIQUE,
    regions_id BIGINT,
    first_name varchar(50) NOT NULL,
    last_name  varchar(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS posts
(
    id         BIGINT       NOT NULL AUTO_INCREMENT UNIQUE,
    writers_id BIGINT       NOT NULL,
    content    varchar(255) NOT NULL,
    `create`   DATETIME  DEFAULT NOW(),
    `upgrade`  TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);