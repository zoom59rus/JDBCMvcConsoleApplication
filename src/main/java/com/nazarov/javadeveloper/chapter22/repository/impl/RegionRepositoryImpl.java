package com.nazarov.javadeveloper.chapter22.repository.impl;

import com.nazarov.javadeveloper.chapter22.ObjectFactory;
import com.nazarov.javadeveloper.chapter22.entity.Region;
import com.nazarov.javadeveloper.chapter22.repository.RegionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class RegionRepositoryImpl implements RegionRepository {
    private final Logger log = LoggerFactory.getLogger("RegionRepositoryImpl");
    private final Connection conn;

    public RegionRepositoryImpl() {
        this.conn = ObjectFactory.getInstance().getConnection();
    }

    public Region save(Region entity) {
        String sqlQuery = String.format("INSERT regions VALUES(null, '%s')", entity.getName());
        Region region = get(entity.getName());
        if(region != null){
            return region;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    entity.setId(rs.getLong(1));
                    log.info("IN - save - Добавлена новая запись " + entity);
                } else throw new SQLException("Сохранение прошло успешно, но не удалось получить id для записи " + entity);
            }
        } catch (SQLException e) {
            log.warn("IN - save - " + e.getMessage());
        }

        return entity;
    }

    public Region get(Long id) {
        String sqlQuery = String.format("SELECT `id`, `name` FROM regions WHERE id=%d", id);
        Region find = null;

        try(Statement st = conn.createStatement()){
            st.execute(sqlQuery);
            try (ResultSet rs = st.getResultSet()){
                while (rs.next()){
                    find = new Region(rs.getLong("id"), rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            log.warn("IN - Region(get) - " + e.getMessage());
        }

        return find;
    }

    public Region get(String name){
        String sqlQuery = String.format("Select * From regions Where name='%s'", name);
        try(Statement st = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sqlQuery);
        ){
            if(rs.next()){
                Region region = new Region(rs.getLong("id"),
                                            rs.getString("name"));
                return region;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Region update(Region region) {
        String sqlQuery = String.format("UPDATE `regions` SET name='%s' WHERE id='%d'", region.getName(), region.getId());

        try(Statement st = conn.createStatement()){
            int isUpdated = st.executeUpdate(sqlQuery);
            if(isUpdated == 1){
                log.info("IN - Regions(update) - Запись обновлена на " + region);
                return region;
            }else {
                log.warn("Запись " + region + " не обновлена.");
            }
        } catch (SQLException e) {
            log.error("IN - Regions(update) - " + e.getMessage());
        }

        return null;
    }

    @Override
    public void remove(Long id) {
        String sqlQuery = String.format("DELETE FROM regions WHERE id=%d", id);

        try(Statement st = conn.createStatement()){
            st.execute(sqlQuery);
        } catch (SQLException e) {
            log.error("IN - Regions(remove) - " + e.getMessage());
        }

    }
}