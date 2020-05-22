package com.nsu.photo_anthropology.db_tools;

import com.nsu.photo_anthropology.config_workers.GetPropertyValues;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {

    private static DbConnector instance;
    private Connection connection;

    public static synchronized DbConnector getInstance(){
        if(instance==null){
            instance = new DbConnector();
        }
        return instance;
    }

    private DbConnector() {
        connection = setConnection();
    }

    private Connection setConnection(){

        GetPropertyValues properties = new GetPropertyValues();

        try {
            properties.getPropValues();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String db_url = properties.getDb_url();
        String user = properties.getUser();
        String password = properties.getPassword();

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            throw new RuntimeException(e);
        }

        try {
            Connection connection = DriverManager.getConnection(db_url, user, password);
            return connection;
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection(){
        return connection;
    }
}

