package com.nazarov.javadeveloper.chapter22.repository.impl;

import com.nazarov.javadeveloper.chapter22.ObjectFactory;
import com.nazarov.javadeveloper.chapter22.entity.Post;
import com.nazarov.javadeveloper.chapter22.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostRepositoryImpl implements PostRepository {
    private final Logger log = LoggerFactory.getLogger("PostRepositoryImpl");
    private final Connection conn;

    public PostRepositoryImpl() {
        this.conn = ObjectFactory.getInstance().getConnection();
    }

    @Override
    public Post save(Post entity) {
        String sqlQuery = String.format("INSERT INTO posts(writers_id, content) VALUES('%d', '%s')", entity.getWritersId(), entity.getContent());
        try (Statement st = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {
            int row = st.executeUpdate(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            if(row == 0){
                log.warn("IN save - Запись " + entity + " не сохранена.");
                return null;
            }
            if(row > 1){
                log.warn("IN save - Сохранение " + entity + " затронуло другие записи.");
            }
            try(ResultSet rs = st.getGeneratedKeys()){
                if(rs.next()){
                    return get(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Post get(Long id) {
        String sqlQuery = String.format("SELECT * FROM posts WHERE id=%d", id);
        Post post = null;
        try(Statement st = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)){
            st.executeQuery(sqlQuery);
            try(ResultSet rs = st.getResultSet()){
                while (rs.next()){

                    post = new Post(rs.getLong("id"),
                                    rs.getLong("writers_id"),
                                    rs.getString("content"),
                                    rs.getTimestamp("create"),
                                    rs.getTimestamp("upgrade")
                    );
                }
                return post;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public Post get(String content) {
        String sqlQuery = String.format("Select * From posts Where content='%s'", content);
        Post post = null;
        try(Statement st = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)){
            st.executeQuery(sqlQuery);
            try(ResultSet rs = st.getResultSet()){
                while (rs.next()){

                    post = new Post(rs.getLong("id"),
                            rs.getLong("writers_id"),
                            rs.getString("content"),
                            rs.getTimestamp("create"),
                            rs.getTimestamp("upgrade")
                    );
                }
                return post;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public Post update(Post post) {
        String sqlQuery = String.format("UPDATE `posts` SET content='%s' WHERE id=%d", post.getContent(), post.getId());
        try(Statement st = conn.createStatement()){
            int row = st.executeUpdate(sqlQuery);
            if(row == 0){
                log.warn("IN update - Не удалось обновить запись  " + post + ".");
                return null;
            }
            if(row > 1){
                log.warn("IN update - Обновление записи  " + post + " затронуло другие записи.");
            }

            return post;
        } catch (SQLException e) {
            e.getMessage();
        }
        return null;
    }

    @Override
    public void remove(Long id) {
        String sqlString = String.format("DELETE FROM posts WHERE id=%d", id);
        try(Statement st = conn.createStatement()){
            int row = st.executeUpdate(sqlString);
            if(row == 0){
                log.warn("IN remove - Запись с id" + id + " не удалена.");
            }
            if(row > 1){
                log.warn("IN remove - Удаление записи с id  " + id + " затронуло другие записи.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Post> getAllByWriterId(Long writerId){
        String sqlQuery = String.format("Select * From posts Where writers_id=%d", writerId);
        List<Post> posts = new ArrayList<>();
        try(Statement st = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sqlQuery);
        ){
            while (rs.next()){
                posts.add(new Post(
                   rs.getLong("id"),
                   rs.getLong("writers_id"),
                   rs.getString("content"),
                   rs.getTimestamp("create"),
                   rs.getTimestamp("upgrade")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }
}