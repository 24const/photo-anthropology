package com.nsu.PhotoAnthropology.DAO;

import com.nsu.PhotoAnthropology.DBClasses.DBConnector;
import com.nsu.PhotoAnthropology.StructureClasses.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GroupDao implements Dao<Group>{

    @Override
    public void save(Group group){
        String sql = "INSERT INTO groups (group_name, group_question) VALUES(?, ?);";
        DBConnector dbConnector = new DBConnector();
        Connection connection = dbConnector.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, group.getGroupName());
            stm.setString(2, group.getGroupQuestion());
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
    public void delete(Group group){
        String sql = "DELETE FROM groups WHERE id = ?;";
        DBConnector dbConnector = new DBConnector();
        Connection connection = dbConnector.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, group.getId());
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
