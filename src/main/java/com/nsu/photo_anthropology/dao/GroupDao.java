package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_classes.DBConnector;
import com.nsu.photo_anthropology.structure_classes.Group;

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
    public void delete(Group group){
        //FIXME: смотри FileDao
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
