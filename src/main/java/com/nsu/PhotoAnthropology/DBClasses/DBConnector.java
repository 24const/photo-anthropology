package com.nsu.PhotoAnthropology.DBClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static String DB_URL = "jdbc:postgresql://192.168.1.43:5432/photo_anthropology";
    private static String USER = "ksu";
    private static String PASS = "ksu";
    private Connection connection;

    public Connection getConnection() {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return null;
        }

        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASS);
            return connection;
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return null;
        }
    }

    public void closeConnection() throws SQLException {
        this.connection.close();
    }
}
