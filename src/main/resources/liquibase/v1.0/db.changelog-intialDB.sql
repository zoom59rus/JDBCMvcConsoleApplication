--liquibase formatted sql
--changeset nazarov:1

CREATE TABLE regions
(
    id   BIGINT         NOT NULL AUTO_INCREMENT UNIQUE,
    name varchar(50) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE writers
(
    id         BIGINT      NOT NULL AUTO_INCREMENT UNIQUE,
    regions_id BIGINT,
    firstName  varchar(50) NOT NULL,
    lastName   varchar(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_writers_regions
        FOREIGN KEY (regions_id) REFERENCES regions (id)
            ON DELETE SET NULL
);

CREATE TABLE posts
(
    id         BIGINT       NOT NULL AUTO_INCREMENT UNIQUE,
    writers_id BIGINT       NOT NULL,
    content    varchar(255) NOT NULL,
    `create`   DATETIME,
    `upgrade`  DATETIME,
    PRIMARY KEY (id),
    CONSTRAINT fk_posts_writers
        FOREIGN KEY (writers_id) REFERENCES writers (id)
            ON DELETE CASCADE
);