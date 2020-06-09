package com.nsu.photo_anthropology.db_tools;

import com.nsu.photo_anthropology.config_workers.GetPropertyValues;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnector {

    private static DbConnector instance;
    private Connection connection;

    private DbConnector() {
        connection = setConnection();
    }

    /**
     * Процедура установки соединения с БД
     */
    public static synchronized DbConnector getInstance() {

        if (instance == null) {
            instance = new DbConnector();
        }
        return instance;
    }

    /**
     * Функция получения значения поля {@link DbConnector#connection}
     *
     * @return Возвращает соединение с БД
     */
    private Connection setConnection() {

        GetPropertyValues properties = new GetPropertyValues();

        try {
            properties.getPropValues();
        } catch (IOException e) {
            throw new PhotoAnthropologyRuntimeException("DbConnector.setConnection(): Ошибка при считывании данных для подключения к БД.", e);
        }

        String dbUrlAddress = properties.getDbUrlAddress();
        String user = properties.getUser();
        String password = properties.getPassword();

        try {
            return DriverManager.getConnection(dbUrlAddress, user, password);
        } catch (Exception e) {
            throw new PhotoAnthropologyRuntimeException("DbConnector.setConnection(): Ошибка при подключении к БД.", e);
        }
    }

    /**
     * Функция получения значения поля {@link DbConnector#connection}
     *
     * @return Возвращает соединение с БД
     */
    public Connection getConnection() {
        return connection;
    }
}

