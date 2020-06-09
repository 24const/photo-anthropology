package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public class InitDao {

    private static final List<String> tablesInDb = Arrays.asList("images", "tags", "groups", "files", "tagged_images", "groups_in_file");

    private InitDao() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Функция создания схемы таблиц в БД, при ее отсутствии
     */
    public static void createDbSchema() {

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();

        int tablesInSchemaCount = 0;

        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema NOT IN ('information_schema', 'pg_catalog') AND table_schema IN('public');")) {
                while (rs.next()) {
                    if (tablesInDb.contains(rs.getString("table_name"))) {
                        tablesInSchemaCount++;
                    }
                }
                if (tablesInSchemaCount < 6) {
                    createNewDbSchema(connection);
                }
            }
        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("StartServlet: невозможно установить соединение с БД.", e);
        }
    }

    /**
     * Функция создания схемы таблиц в БД
     */
    private static void createNewDbSchema(Connection connection) {
        try {
            SqlFile sf = new SqlFile(new File("src/main/webapp/WEB-INF/sql-scripts/init.sql"));
            sf.setConnection(connection);
            sf.execute();
        } catch (SQLException | IOException | SqlToolError e) {
            throw new PhotoAnthropologyRuntimeException("StartServlet: ошибка при запуске скрипта создания таблиц БД.", e);
        }
    }
}
