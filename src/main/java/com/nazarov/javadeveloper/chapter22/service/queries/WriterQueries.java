package com.nazarov.javadeveloper.chapter22.service.queries;

public final class WriterQueries {
    public static String GET_BY_ID = "SELECT writers_id AS id, regions_id, first_name, last_name, regions.`name` AS regionName, posts.id AS postId, `content`, `create`, upgrade\n" +
                                     "FROM writers INNER JOIN regions ON regions.id=writers.regions_id INNER JOIN posts ON posts.writers_id=writers.id\n" +
                                     "WHERE writers.id=";

    public static String GET_BY_FIRST_NAME = "SELECT writers_id AS id, regions_id, first_name, last_name, regions.`name` AS regionName, posts.id AS postId, `content`, `create`, upgrade\n" +
                                             "FROM writers INNER JOIN regions ON regions.id=writers.regions_id INNER JOIN posts ON posts.writers_id=writers.id\n" +
                                             "WHERE writers.first_name=";

    public static String GET_BY_LAST_NAME = "SELECT writers_id AS id, regions_id, first_name, last_name, regions.`name` AS regionName, posts.id AS postId, `content`, `create`, upgrade\n" +
                                            "FROM writers INNER JOIN regions ON regions.id=writers.regions_id INNER JOIN posts ON posts.writers_id=writers.id\n" +
                                            "WHERE writers.last_name=";

    public static String GET_BY_REGION_NAME = "SELECT writers_id AS id, regions_id, first_name, last_name, regions.`name` AS regionName, posts.id AS postId, `content`, `create`, upgrade\n" +
                                              "FROM writers INNER JOIN regions ON regions.id=writers.regions_id INNER JOIN posts ON posts.writers_id=writers.id\n" +
                                              "WHERE writers.regions_id=";

    public static String UPDATE = "UPDATE writers SET regions_id=%d, first_name='%s', last_name='%s' Where id=%d";

    public static String DELETE = "DELETE FROM writers WHERE id=%d";

    public static String SAVE = "INSERT INTO writers VALUES(null, '%d', '%s', '%s')";
}