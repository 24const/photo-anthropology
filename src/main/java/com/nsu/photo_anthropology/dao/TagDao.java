package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_classes.DBConnector;
import com.nsu.photo_anthropology.structure_classes.Tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TagDao implements Dao<Tag>{

    @Override
    public void save(Tag tag){
        String sql = "INSERT INTO tags(group_id, tag_name) VALUES(?, ?);";
        DBConnector dbConnector = new DBConnector();
        Connection connection = dbConnector.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, tag.getGroup_id());
            stm.setString(2, tag.getTag_name());
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
    public void delete(Tag tag){
        //FIXME: смотри FileDao
        String sql = "DELETE FROM tags WHERE id = ?;";
        DBConnector dbConnector = new DBConnector();
        Connection connection = dbConnector.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, tag.getId());
            stm.execute();
        } catch (SQLException e) {
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
}
