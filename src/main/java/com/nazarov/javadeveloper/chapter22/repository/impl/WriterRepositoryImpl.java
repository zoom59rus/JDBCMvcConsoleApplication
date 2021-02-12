package com.nazarov.javadeveloper.chapter22.repository.impl;

import com.nazarov.javadeveloper.chapter22.entity.Post;
import com.nazarov.javadeveloper.chapter22.entity.Region;
import com.nazarov.javadeveloper.chapter22.entity.Writer;
import com.nazarov.javadeveloper.chapter22.repository.DBUtils;
import com.nazarov.javadeveloper.chapter22.repository.WriterRepository;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class WriterRepositoryImpl implements WriterRepository {
    private final Logger log = LoggerFactory.getLogger("WriterRepositoryImpl");

    @Override
    public Writer save(Writer writer) {
        try (Statement st = DBUtils.getStatement()) {
            int affect = st.executeUpdate(String.format(DBUtils.SAVE_WRITER, writer.getRegions_id(), writer.getFirstName(), writer.getLastName()),
                                        Statement.RETURN_GENERATED_KEYS);
            if (affect == 0) {
                log.warn("IN save - Запись " + writer + " не сохранена.");
                return null;
            }
            if (affect > 1) {
                log.warn("IN save - Сохранение " + writer + " затронуло другие записи.");
            }
            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    writer.setId(rs.getLong(1));
                    return writer;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Writer get(Long id) {
        return executeQuery(DBUtils.GET_WRITER_BY_ID, id.toString());
    }

    @Override
    public Writer getByFirstName(String firstName) {
        return executeQuery(DBUtils.GET_WRITER_BY_FIRST_NAME, firstName);
    }

    @Override
    public Writer getByLastName(String lastName) {
        return executeQuery(DBUtils.GET_WRITER_BY_LAST_NAME, lastName);
    }

    @Override
    public Writer getByRegion(Long regionId) {
        return executeQuery(DBUtils.GET_WRITER_BY_REGION_ID, regionId.toString());
    }

    @Override
    public Writer update(Writer writer) {
        try (Statement st = DBUtils.getStatement()) {

            int affected = st.executeUpdate(
                    String.format(DBUtils.UPDATE_WRITER, writer.getRegions_id(),
                                                        writer.getFirstName(),
                                                        writer.getLastName(),
                                                        writer.getId())
            );

            if (affected == 0) {
                log.warn("IN update - Не удалось обновить запись  " + writer + ".");
                return null;
            }
            if (affected > 1) {
                log.warn("IN update - Обновление записи  " + writer + " затронуло другие записи.");
            }

            return writer;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void remove(Long id) {
        try (Statement st = DBUtils.getStatement()) {
            st.execute(String.format(DBUtils.DELETE_WRITER, id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Writer executeQuery(String sqlQuery, String param) {
        try (
                Statement st = DBUtils.getStatement();
                ResultSet rs = st.executeQuery(sqlQuery + "\'" + param + "\'")
        ) {
            if (rs.next()) {
                Writer writer = new Writer();
                List<Post> posts = new ArrayList<>();

                writer.setId(rs.getLong("id"));
                writer.setRegions_id(rs.getLong("regions_id"));
                writer.setFirstName(rs.getString("first_name"));
                writer.setLastName(rs.getString("last_name"));
                writer.setRegion(new Region(
                        writer.getRegions_id(),
                        rs.getString("regionName")
                ));
                posts.add(new Post(
                        rs.getLong("postId"),
                        writer.getId(),
                        rs.getString("content"),
                        rs.getTimestamp("create"),
                        rs.getTimestamp("upgrade")
                ));
                while (rs.next()) {
                    posts.add(new Post(
                            rs.getLong("postId"),
                            writer.getId(),
                            rs.getString("content"),
                            rs.getTimestamp("create"),
                            rs.getTimestamp("upgrade")
                    ));
                }
                writer.setPosts(posts);
                return writer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}