package com.nazarov.javadeveloper.chapter22.repository.impl;

import com.nazarov.javadeveloper.chapter22.ObjectFactory;
import com.nazarov.javadeveloper.chapter22.entity.Region;
import com.nazarov.javadeveloper.chapter22.repository.DBUtils;
import com.nazarov.javadeveloper.chapter22.repository.RegionRepository;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

@NoArgsConstructor
public class RegionRepositoryImpl implements RegionRepository {
    private final Logger log = LoggerFactory.getLogger("RegionRepositoryImpl");

    public Region save(Region entity) {
        Region region = get(entity.getName());
        if (region != null) {
            return region;
        }

        try (Statement stmt = DBUtils.getStatement()) {
            int row = stmt.executeUpdate(
                    String.format(DBUtils.SAVE_REGION, entity.getName()),
                    Statement.RETURN_GENERATED_KEYS
            );
            if (row == 0) {
                log.warn("IN save - Запись " + entity + " не сохранена.");
                return null;
            }
            if (row > 1) {
                log.warn("IN save - Сохранение " + entity + " затронуло другие записи.");
            }
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    entity.setId(rs.getLong(1));
                    log.info("IN - save - Добавлена новая запись " + entity);
                } else
                    throw new SQLException("Сохранение прошло успешно, но не удалось получить id для записи " + entity);
            }
        } catch (SQLException e) {
            log.warn("IN - save - " + e.getMessage());
        }

        return entity;
    }

    public Region get(Long id) {
        Region find = null;

        try (
                Statement st = DBUtils.getStatement();
                ResultSet rs = st.executeQuery(String.format(DBUtils.GET_REGION_BY_ID, id))
        ) {
            while (rs.next()) {
                find = new Region(rs.getLong("id"),
                        rs.getString("name"));
            }
        } catch (SQLException e) {
            log.warn("IN - Region(get) - " + e.getMessage());
        }

        return find;
    }

    public Region get(String name) {
        Region find = null;

        try (
                Statement st = DBUtils.getStatement();
                ResultSet rs = st.executeQuery(String.format(DBUtils.GET_REGION_BY_NAME, name))
        ) {
            if (rs.next()) {
                find = new Region(rs.getLong("id"),
                        rs.getString("name"));
                return find;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return find;
    }

    @Override
    public Region update(Region region) {
        try (Statement st = DBUtils.getStatement()) {
            int isUpdated = st.executeUpdate(String.format(DBUtils.UPDATE_REGION, region.getName(), region.getId()));
            if (isUpdated == 1) {
                log.info("IN - Regions(update) - Запись обновлена на " + region);
                return region;
            } else {
                log.warn("Запись " + region + " не обновлена.");
            }
        } catch (SQLException e) {
            log.error("IN - Regions(update) - " + e.getMessage());
        }

        return null;
    }

    @Override
    public void remove(Long id) {
        try (Statement st = DBUtils.getStatement()) {
            st.execute(String.format(DBUtils.DELETE_REGION, id));
        } catch (SQLException e) {
            log.error("IN - Regions(remove) - " + e.getMessage());
        }
    }
}