package com.nsu.photo_anthropology.servlets;

import com.nsu.photo_anthropology.db_classes.DBConnector;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class StartServlet extends HttpServlet {

    public void init() throws ServletException {
        //super.init();
        DBConnector dbConnector = new DBConnector();
        Connection connection = dbConnector.getConnection();
        try {
            //FIXME: а где проверка что схема уже инициализированна? Скрипт должен запускаться единожды, дабы не испортить схему
            SqlFile sf = new SqlFile(new File("C:\\Users\\Эльдорадо\\Desktop\\photo-anthropology\\src\\main\\webapp\\WEB-INF\\sql-scripts\\init.sql"));
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
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

    }
}
