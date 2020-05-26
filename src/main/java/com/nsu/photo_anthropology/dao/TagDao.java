package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.structure_entities.Group;
import com.nsu.photo_anthropology.structure_entities.Tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagDao extends DaoFactory<Tag> implements Dao<Tag> {

    public static final String SQLDELETETAGREQUEST = "DELETE FROM tags WHERE id = ?";

    @Override
    public void save(Tag tag) {
        String sql = "INSERT INTO tags(group_id, tag_name) VALUES((SELECT id from groups where group_name = ?), ?);";
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, tag.getGroupName());
            stm.setString(2, tag.getTagName());
            stm.execute();
        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("Ошибка сохранения данных в БД в TagDao.save(Tag tag)");
        }
    }

    @Override
    public String getDeleteSqlRequest() {
        return SQLDELETETAGREQUEST;
    }

    @Override
    public int getId(Tag tag) {
        return tag.getId();
    }

    public List<Tag> getAllTagsInGroup(Group group) {

        List<Tag> listOfTags = new ArrayList<>();
        String sql = "SELECT id, tag_name FROM tags WHERE group_id = ?";
        int groupId = group.getId();

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, groupId);

            try (ResultSet resultSet = stm.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String tagName = resultSet.getString("tag_name");
                    listOfTags.add(new Tag(id, tagName, group.getGroupName()));
                }
            }

        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("Ошибка при получении информации о тегах из БД в TagDao.getAllTagsInGroup(Group group).");
        }
        return listOfTags;
    }

    public void deleteAllTagsInGroup(int id) {
        String sql = "DELETE FROM tags WHERE group_id = ?";

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            stm.execute();
        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("Ошибка при удалении информации из БД в TagDao.deleteAllTagsInGroup(int id).");
        }
    }

    @Override
    public void deleteRelatedEntities(int id) throws SQLException {
        // На данной стадии не реализовано удаления тегов,
        // а следовательно, и удаление связанной информации
    }
}
