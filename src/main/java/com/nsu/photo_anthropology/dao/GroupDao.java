package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.structure_entities.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDao extends DaoFactory<Group>{

    public final static String SQL_DELETE_REQUEST = "DELETE FROM groups WHERE id = ?";

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
    public String getDeleteSqlRequest() {
        return SQL_DELETE_REQUEST;
    }

    @Override
    public int getId(Group group) {
        return group.getId();
    }

    public List<Group> getAll() {

        List<Group> listOfGroups = new ArrayList<>();
        String sql = "SELECT * FROM groups";

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet resultSet = stm.executeQuery();

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String groupName = resultSet.getString("group_name");
                String groupQuestion = resultSet.getString("group_question");
                listOfGroups.add(new Group(id, groupName, groupQuestion));
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOfGroups;
    }

    public Group getById(int id){

        String sql = "SELECT * FROM groups where id = ?";
        Group group;

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            ResultSet resultSet = stm.executeQuery();
            resultSet.next();
            String groupName = resultSet.getString("group_name");
            String groupQuestion = resultSet.getString("group_question");
            group = new Group(id, groupName, groupQuestion);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return group;
    }

    public void update(Group group) {
        String sql = "UPDATE groups SET group_name = ?, group_question = ? where id = ?";
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, group.getGroupName());
            stm.setString(2, group.getGroupQuestion());
            stm.setInt(3, group.getId());
            stm.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteRelatedEntities(int id){
        TagDao tagDao = new TagDao();
        tagDao.deleteAllTagsInGroup(id);
    }
}
