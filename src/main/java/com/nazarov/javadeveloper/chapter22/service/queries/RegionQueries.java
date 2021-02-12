package com.nazarov.javadeveloper.chapter22.service.queries;

public final class RegionQueries {
    public static String GET_BY_ID = "SELECT `id`, `name` FROM regions WHERE id=%d";

    public static String GET_BY_NAME = "SELECT `id`, `name` FROM regions WHERE name='%s'";

    public static String SAVE = "INSERT regions VALUES(null, '%s')";

    public static String UPDATE = "UPDATE `regions` SET name='%s' WHERE id=%d";

    public static String DELETE = "DELETE FROM regions WHERE id=%d";
}
