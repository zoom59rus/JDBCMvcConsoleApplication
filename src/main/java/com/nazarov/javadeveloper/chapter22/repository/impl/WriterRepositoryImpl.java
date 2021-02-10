package com.nazarov.javadeveloper.chapter22.repository.impl;

import com.nazarov.javadeveloper.chapter22.ObjectFactory;
import com.nazarov.javadeveloper.chapter22.entity.Writer;
import com.nazarov.javadeveloper.chapter22.repository.WriterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WriterRepositoryImpl implements WriterRepository {
    private final Logger log = LoggerFactory.getLogger("WriterRepositoryImpl");
    private final Connection conn;

    public WriterRepositoryImpl() {
        this.conn = ObjectFactory.getInstance().getConnection();
    }

    @Override
    public Writer save(Writer writer) {
        String sqlQuery = String.format("INSERT INTO writers(id, regions_id, first_name, last_name) VALUES(null, '%d', '%s', '%s')", writer.getRegions_id(), writer.getFirstName(), writer.getLastName());
        try (Statement st = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {
            int affect = st.executeUpdate(sqlQuery, Statement.RETURN_GENERATED_KEYS);
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
        String sqlQuery = String.format("Select * From writers Where id=%d", id);
        return executeQuery(sqlQuery);
    }

    @Override
    public Writer getByFirstName(String firstName) {
        String sqlQuery = String.format("Select * From writers Where first_name='%s'", firstName);
        return executeQuery(sqlQuery);

    }

    @Override
    public Writer getByLastName(String lastName) {
        String sqlQuery = String.format("Select * From writers Where last_name='%s'", lastName);
        return executeQuery(sqlQuery);

    }

    @Override
    public Writer getByRegion(Long regionId) {
        String sqlQuery = String.format("Select * From writers Where regions_id=%d", regionId);
        return executeQuery(sqlQuery);
    }

    @Override
    public Writer update(Writer writer) {
        String sqlQuery = String.format("Update writers Set regions_id=%d, first_name='%s', last_name='%s' Where id=%d",
                writer.getRegions_id(), writer.getFirstName(), writer.getLastName(), writer.getId());
        try (Statement st = conn.createStatement()) {
            int affected = st.executeUpdate(sqlQuery);
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
        String sqlQuery = String.format("Delete From writers Where id=%d", id);
        try (Statement st = conn.createStatement()) {
            st.execute(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Writer executeQuery(String sqlQuery) {
        try (Statement st = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = st.executeQuery(sqlQuery)
        ) {
            if (rs.next()) {
                return new Writer(
                        rs.getLong("id"),
                        rs.getLong("regions_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}