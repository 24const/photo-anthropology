package com.nsu.PhotoAnthropology.DAO;

import com.nsu.PhotoAnthropology.DBClasses.DBConnector;
import com.nsu.PhotoAnthropology.StructureClasses.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FileDao implements Dao<File>{

    @Override
    public void save(File file){
        String sql = "INSERT INTO files(file_name, column_names) VALUES(?, ?);";
        DBConnector dbConnector = new DBConnector();
        Connection connection = dbConnector.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, file.getFile_name());
            stm.setString(2, file.getColumnNames());
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
        String sql = "DELETE FROM files WHERE file_id = ?;";
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
