package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.structure_entities.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FileDao extends DaoFactory<File> implements Dao<File>{

    public final static String SQL_REQUEST = "DELETE FROM files WHERE id = ?";

    @Override
    public void save(File file){
        String sql = "INSERT INTO files(file_name, column_names) VALUES(?, ?::JSON)";
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, file.getFileName());
            stm.setObject(2, file.getColumnNames().toJSONString());
            stm.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getSqlRequest() {
        return SQL_REQUEST;
    }

    @Override
    public int getId(File file) {
        return file.getId();
    }
}
