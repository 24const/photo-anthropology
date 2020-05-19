package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_classes.DBConnector;
import com.nsu.photo_anthropology.structure_classes.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FileDao implements Dao<File>{

    @Override
    public void save(File file){
        String sql = "INSERT INTO files(file_name, column_names) VALUES(?, ?::JSON)";
        DBConnector dbConnector = new DBConnector();
        Connection connection = dbConnector.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, file.getFileName());
            stm.setObject(2, file.getColumnNames().toJSONString());
            stm.execute();
        } catch (SQLException e) {
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
    public void delete(File file){
        String sql = "DELETE FROM files WHERE file_id = ?";
        DBConnector dbConnector = new DBConnector();
        Connection connection = dbConnector.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, file.getId());
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                dbConnector.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
