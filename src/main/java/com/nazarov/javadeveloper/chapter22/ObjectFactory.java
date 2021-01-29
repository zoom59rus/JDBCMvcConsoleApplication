package com.nazarov.javadeveloper.chapter22;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class ObjectFactory {
    private final Logger log = LoggerFactory.getLogger("ObjectFactory");
    private static ObjectFactory INSTANCE = null;
    private final Properties props;

    private ObjectFactory() {
        Properties props = new Properties();
        try(InputStream is = ClassLoader.getSystemResourceAsStream("application.properties")){
            props.load(is);
        } catch (IOException e) {
            log.warn("Невозможно прочитать application.properties");
            System.err.println(e.getMessage());
        }
        this.props = props;
    }

    public static ObjectFactory getInstance(){
        if(INSTANCE == null){
            synchronized (ObjectFactory.class){
                if(INSTANCE == null){
                    INSTANCE = new ObjectFactory();
                }
            }
        }

        return INSTANCE;
    }

    public Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(props.getProperty("url"), props.getProperty("username"), props.getProperty("password"));
        } catch (SQLException e) {
            log.warn("IN - getConnection - Ошибка создания экземпляра Connection.");
            e.printStackTrace();
        }
        log.info("IN - getConnection - Объект Connection успешно создан.");

        return conn;
    }
}