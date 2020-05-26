package com.nsu.photo_anthropology.db_tools;

import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.config_workers.GetPropertyValues;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {

    private static DbConnector instance;
    private Connection connection;

    public static synchronized DbConnector getInstance() {

        if (instance == null) {
            instance = new DbConnector();
        }
        return instance;
    }

    private DbConnector() {
        connection = setConnection();
    }

    private Connection setConnection() {

        GetPropertyValues properties = new GetPropertyValues();

        try {
            properties.getPropValues();
        } catch (IOException e) {
            throw new PhotoAnthropologyRuntimeException("DbConnector.setConnection(): Ошибка при считывании данных для подключения к БД.");
        }

        String dbUrlAddress = properties.getDbUrlAddress();
        String user = properties.getUser();
        String password = properties.getPassword();

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            return DriverManager.getConnection(dbUrlAddress, user, password);
        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("DbConnector.setConnection(): Ошибка при подключении к БД.");
        }
    }
    public Connection getConnection() {
        return connection;
    }
}

