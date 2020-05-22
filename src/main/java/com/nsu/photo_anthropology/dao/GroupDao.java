package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.structure_entities.File;
import com.nsu.photo_anthropology.structure_entities.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GroupDao extends DaoFactory<Group> implements Dao<Group>{

    public final static String TABLE_NAME = "groups";

    @Override
    public void save(Group group){
        String sql = "INSERT INTO groups (group_name, group_question) VALUES(?, ?);";
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, group.getGroupName());
            stm.setString(2, group.getGroupQuestion());
            stm.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public int getId(Group group) {
        return group.getId();
    }

}
