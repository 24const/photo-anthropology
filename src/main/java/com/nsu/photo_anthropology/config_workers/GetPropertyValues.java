package com.nsu.photo_anthropology.config_workers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class GetPropertyValues {

    String user;
    String password;
    String db_url;

    public void getPropValues() throws IOException {

        Properties prop = new Properties();
        String propFileName = "config.properties";

        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);){

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            Date time = new Date(System.currentTimeMillis());

            user = prop.getProperty("user");
            password = prop.getProperty("password");
            db_url = prop.getProperty("db_url");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDb_url() {
        return db_url;
    }
}
