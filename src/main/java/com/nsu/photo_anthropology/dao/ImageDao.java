package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_classes.DBConnector;
import com.nsu.photo_anthropology.structure_classes.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImageDao implements Dao<Image>{

    @Override
    public void save(Image image){
        String sql = "INSERT INTO images(file_id, image_path, other_information) VALUES((SELECT id FROM files WHERE file_name = ?), ?, ?::JSON);";
        DBConnector dbConnector = new DBConnector();
        Connection connection = dbConnector.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, image.getFileName());
            stm.setString(2, image.getImagePath());
            stm.setObject(3, image.getOtherInformation().toJSONString());
            stm.execute();
        } catch (SQLException e) {
            //FIXME: смотри FileDao
            e.printStackTrace();
        } finally {
            try {
                //FIXME: смотри FileDao
                dbConnector.closeConnection();
            } catch (SQLException e) {
                //FIXME: смотри FileDao
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Image image){
        //FIXME: смотри FileDao
        String sql = "DELETE FROM images WHERE id = ?;";
        DBConnector dbConnector = new DBConnector();
        Connection connection = dbConnector.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, image.getId());
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
