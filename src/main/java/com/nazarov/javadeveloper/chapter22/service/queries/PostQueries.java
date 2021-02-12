package com.nazarov.javadeveloper.chapter22.service.queries;

public final class PostQueries {
    public static String GET_BY_ID = "SELECT * FROM posts WHERE id=%d";
    public static String GET_BY_NAME = "SELECT * FROM posts WHERE content='%s'";
    public static String SAVE = "INSERT INTO posts(writers_id, content) VALUES('%d', '%s')";
    public static String UPDATE = "UPDATE posts SET content='%s' WHERE id=%d";
    public static String DELETE = "DELETE FROM posts WHERE id=%d";
    public static String GET_ALL_BY_WRITER_ID = "SELECT * FROM posts WHERE writers_id=%d";
}
