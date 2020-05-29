package com.nsu.photo_anthropology.config_workers;

import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetPropertyValues {

    String user;
    String password;
    String dbUrlAddress;
    String uploadedFilesDirectory;

    /**
     * Функция считывания из "config.properties" данных
     * для подключения к БД, а так же пути папки для
     * сохранения загружаемых на сервер файлов
     */
    public void getPropValues() throws IOException {

        Properties prop = new Properties();
        String propFileName = "config.properties";

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName)) {
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            user = prop.getProperty("user");
            password = prop.getProperty("password");
            dbUrlAddress = prop.getProperty("db_url");
            uploadedFilesDirectory = prop.getProperty("uploaded_files_directory");


        } catch (Exception e) {
            throw new PhotoAnthropologyRuntimeException("Невозможно считать данные конфигурации для получения доступа к БД.");
        }
    }

    /**
     * Функция получения значения поля {@link GetPropertyValues#user}
     *
     * @return Возвращает имя пользователя БД
     */
    public String getUser() {
        return user;
    }

    /**
     * Функция получения значения поля {@link GetPropertyValues#password}
     *
     * @return Возвращает пароль пользователя БД
     */
    public String getPassword() {
        return password;
    }

    /**
     * Функция получения значения поля {@link GetPropertyValues#dbUrlAddress}
     *
     * @return Возвращает адрес БД
     */
    public String getDbUrlAddress() {
        return dbUrlAddress;
    }

    /**
     * Функция получения значения поля {@link GetPropertyValues#uploadedFilesDirectory}
     *
     * @return Возвращает путь папки для сохранения загружаемых файлов
     */
    public String getUploadedFilesDirectory() {
        return uploadedFilesDirectory;
    }
}
