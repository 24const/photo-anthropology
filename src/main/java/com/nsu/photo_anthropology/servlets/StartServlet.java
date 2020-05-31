package com.nsu.photo_anthropology.servlets;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
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

    @Override
    public void init() {
        //TODO: в сервлетах напрямую рабоют с визуализацией данных. Сервлет про БД ничего не знает, для него есть только абстрация хранилища данных ввдие DAO слоя
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT count(table_name) as cnt FROM information_schema.tables WHERE table_schema NOT IN ('information_schema', 'pg_catalog') AND table_schema IN('public');")) {
                rs.next();
                //TODO: а если в БД будут 6 таблиц сторонних созданых вручную? Приложение должно убедиться что схема БД чиста и невинна, прежде чем создавать в ней свою структуру?
                if (rs.getInt("cnt") < 5) {
                    createDbSchema(connection);
                }
            }
        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("StartServlet: невозможно установить соединение с БД.");
        }
    }

    private void createDbSchema(Connection connection) {
        try {
            //TODO: про работу с БД знает только DAO
            SqlFile sf = new SqlFile(new File("init.sql"));
            sf.setConnection(connection);
            sf.execute();
        } catch (SQLException | IOException | SqlToolError e) {
            throw new PhotoAnthropologyRuntimeException("StartServlet: ошибка при запуске скрипта создания таблиц БД.");
        }
    }
}
