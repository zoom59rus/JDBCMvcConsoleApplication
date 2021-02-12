package com.nazarov.javadeveloper.chapter22.repository;

import com.nazarov.javadeveloper.chapter22.ObjectFactory;

import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {

    public static String GET_REGION_BY_ID = "SELECT `id`, `name` FROM regions WHERE id=%d";

    public static String GET_REGION_BY_NAME = "SELECT `id`, `name` FROM regions WHERE name='%s'";

    public static String SAVE_REGION = "INSERT regions VALUES(null, '%s')";

    public static String UPDATE_REGION = "UPDATE `regions` SET name='%s' WHERE id=%d";

    public static String DELETE_REGION = "DELETE FROM regions WHERE id=%d";


    public static String GET_POST_BY_ID = "SELECT * FROM posts WHERE id=%d";

    public static String GET_POST_BY_CONTENT = "SELECT * FROM posts WHERE content='%s'";

    public static String SAVE_POST = "INSERT INTO posts(writers_id, content) VALUES('%d', '%s')";

    public static String UPDATE_POST = "UPDATE posts SET content='%s' WHERE id=%d";

    public static String DELETE_POST = "DELETE FROM posts WHERE id=%d";

    public static String GET_ALL_POST_BY_WRITER_ID = "SELECT * FROM posts WHERE writers_id=%d";


    public static String GET_WRITER_BY_ID = "SELECT writers_id AS id, regions_id, first_name, last_name, regions.`name` AS regionName, posts.id AS postId, `content`, `create`, upgrade\n" +
            "FROM writers INNER JOIN regions ON regions.id=writers.regions_id INNER JOIN posts ON posts.writers_id=writers.id\n" +
            "WHERE writers.id=";

    public static String GET_WRITER_BY_FIRST_NAME = "SELECT writers_id AS id, regions_id, first_name, last_name, regions.`name` AS regionName, posts.id AS postId, `content`, `create`, upgrade\n" +
            "FROM writers INNER JOIN regions ON regions.id=writers.regions_id INNER JOIN posts ON posts.writers_id=writers.id\n" +
            "WHERE writers.first_name=";

    public static String GET_WRITER_BY_LAST_NAME = "SELECT writers_id AS id, regions_id, first_name, last_name, regions.`name` AS regionName, posts.id AS postId, `content`, `create`, upgrade\n" +
            "FROM writers INNER JOIN regions ON regions.id=writers.regions_id INNER JOIN posts ON posts.writers_id=writers.id\n" +
            "WHERE writers.last_name=";

    public static String GET_WRITER_BY_REGION_ID = "SELECT writers_id AS id, regions_id, first_name, last_name, regions.`name` AS regionName, posts.id AS postId, `content`, `create`, upgrade\n" +
            "FROM writers INNER JOIN regions ON regions.id=writers.regions_id INNER JOIN posts ON posts.writers_id=writers.id\n" +
            "WHERE writers.regions_id=";

    public static String UPDATE_WRITER = "UPDATE writers SET regions_id=%d, first_name='%s', last_name='%s' Where id=%d";

    public static String DELETE_WRITER = "DELETE FROM writers WHERE id=%d";

    public static String SAVE_WRITER = "INSERT INTO writers VALUES(null, '%d', '%s', '%s')";


    public static Statement getStatement() throws SQLException {
        return ObjectFactory.getInstance()
                .getStatement();
    }
}
