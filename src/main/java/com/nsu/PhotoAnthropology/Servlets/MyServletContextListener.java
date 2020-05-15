package com.nsu.PhotoAnthropology.Servlets;

import com.nsu.PhotoAnthropology.DBClasses.DBConnector;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class MyServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        DBConnector dbConnector = new DBConnector();
        Connection connection = dbConnector.getConnection();

        try {
            SqlFile sf = new SqlFile(new File("C:\\Users\\Эльдорадо\\Desktop\\photo-anthropology\\src\\main\\java\\com\\nsu\\PhotoAnthropology\\Servlets\\init.sql"));
            sf.setConnection(connection);
            sf.execute();
        } catch (SQLException | IOException | SqlToolError e) {
            e.printStackTrace();

        } finally {
            try {
                dbConnector.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }


}
