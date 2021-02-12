package com.nazarov.javadeveloper.chapter22.repository.impl;

import com.nazarov.javadeveloper.chapter22.ObjectFactory;
import com.nazarov.javadeveloper.chapter22.entity.Post;
import com.nazarov.javadeveloper.chapter22.repository.PostRepository;
import com.nazarov.javadeveloper.chapter22.service.queries.PostQueries;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class PostRepositoryImpl implements PostRepository {
    private final Logger log = LoggerFactory.getLogger("PostRepositoryImpl");

    @Override
    public Post save(Post entity) {
        try (
                Statement st = ObjectFactory.getInstance()
                        .getConnection()
                        .createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
        ) {
            int row = st.executeUpdate(String.format(PostQueries.SAVE, entity.getWritersId(), entity.getContent()), Statement.RETURN_GENERATED_KEYS);
            if (row == 0) {
                log.warn("IN save - Запись " + entity + " не сохранена.");
                return null;
            }
            if (row > 1) {
                log.warn("IN save - Сохранение " + entity + " затронуло другие записи.");
            }
            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
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
        Post post = null;
        try (
                Statement st = ObjectFactory.getInstance()
                        .getConnection()
                        .createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
        ) {
            st.executeQuery(String.format(PostQueries.GET_BY_ID, id));
            try (ResultSet rs = st.getResultSet()) {
                while (rs.next()) {

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
        Post post = null;
        try (
                Statement st = ObjectFactory.getInstance()
                        .getConnection()
                        .createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
        ) {
            st.executeQuery(String.format(PostQueries.GET_BY_NAME, content));
            try (ResultSet rs = st.getResultSet()) {
                while (rs.next()) {

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
        try (
                Statement st = ObjectFactory.getInstance().getStatement()
        ) {
            int row = st.executeUpdate(String.format(PostQueries.UPDATE, post.getContent(), post.getId()));
            if (row == 0) {
                log.warn("IN update - Не удалось обновить запись  " + post + ".");
                return null;
            }
            if (row > 1) {
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
        try (
                Statement st = ObjectFactory.getInstance()
                        .getConnection()
                        .createStatement()
        ) {
            int row = st.executeUpdate(String.format(PostQueries.DELETE, id));
            if (row == 0) {
                log.warn("IN remove - Запись с id" + id + " не удалена.");
            }
            if (row > 1) {
                log.warn("IN remove - Удаление записи с id  " + id + " затронуло другие записи.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Post> getAllByWriterId(Long writerId) {
        List<Post> posts = new ArrayList<>();
        try (
                Statement st = ObjectFactory.getInstance()
                        .getConnection()
                        .createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(String.format(PostQueries.GET_ALL_BY_WRITER_ID, writerId));
        ) {
            while (rs.next()) {
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