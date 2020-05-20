//FIXME: в java все есть класс, поэтому лучше назвать db_tools
package com.nsu.photo_anthropology.db_classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    //FIXME: получения настроек базы перенесте в конфигурацию программы
    //FIXME: https://crunchify.com/java-properties-file-how-to-read-config-properties-values-in-java/
    private static String DB_URL = "jdbc:postgresql://192.168.1.43:5432/photo_anthropology";
    private static String USER = "ksu";
    private static String PASS = "ksu";

    //FIXME: web приложение - многопоточное, каждый запрос к нему создает новый поток. Соответственно
    //FIXME: много запросов создадут много соединений и будет ошибка SQLException "too many connection"
    //FIXME: Поэтому давай использовать thread safe singleton, который держит одновременно одно открытое соединение
    private Connection connection;

    public Connection getConnection() {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            //FIXME: смотри FileDao
            e.printStackTrace();
            return null;
        }

        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASS);
            return connection;
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            //FIXME: смотри FileDao
            e.printStackTrace();
            return null;
        }
    }

    public void closeConnection() throws SQLException {
        //FIXME: закрывать connection нужно, когда web приложение выключается
        this.connection.close();
    }
}
