package com.nsu.photo_anthropology.servlets;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;

import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StartServlet extends HttpServlet {

    public void init() {
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT count(table_name) as cnt FROM information_schema.tables WHERE table_schema NOT IN ('information_schema', 'pg_catalog') AND table_schema IN('public');");
            rs.next();
            if (rs.getInt("cnt") < 5) {
                try {
                    SqlFile sf = new SqlFile(new File("C:\\Users\\Эльдорадо\\Desktop\\photo-anthropology\\src\\main\\webapp\\WEB-INF\\sql-scripts\\init.sql"));
                    sf.setConnection(connection);
                    sf.execute();
                } catch (SQLException | IOException | SqlToolError e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
