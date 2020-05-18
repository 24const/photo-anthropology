package com.nsu.PhotoAnthropology.DAO;

import com.nsu.PhotoAnthropology.DBClasses.DBConnector;
import com.nsu.PhotoAnthropology.StructureClasses.Tag;

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
    public void delete(Tag tag){
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
                dbConnector.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
