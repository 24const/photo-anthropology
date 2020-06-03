package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.Group;
import com.nsu.photo_anthropology.structure_entities.Tag;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TagDao extends DaoFactory<Tag> implements Dao<Tag> {

    public static final String SQL_DELETE_REQUEST = "DELETE FROM tags WHERE id = ?";

    /**
     * Процедура сохранения данных об теге в таблице tags БД
     *
     * @param tag - тег, данные о котором сохраняем {@link Tag}
     * @return - Возвращает id сохраненного тега
     */
    @Override
    public int save(Tag tag) {
        String sql = "INSERT INTO tags(group_id, tag_name) VALUES((SELECT id from groups where group_name = ?), ?);";
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, tag.getGroupName());
            stm.setString(2, tag.getTagName());
            stm.executeUpdate();
            return setIdOfSavedTag();
        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("Ошибка сохранения данных в БД в TagDao.save(Tag tag)");
        }
    }

    /**
     * Функция получения значения поля {@link TagDao#SQL_DELETE_REQUEST}
     *
     * @return возвращает SQL-запрос для удаления записи о теге из таблицы tags БД по id
     */
    @Override
    public String getDeleteSqlRequest() {
        return SQL_DELETE_REQUEST;
    }

    /**
     * Функция получения данных из БД обо всех тегах группы
     *
     * @param groupId - id группы, теги, которой узнаем
     * @return список всех тегов, содержащихся в группе
     */
    public List<Tag> getAllTagsInGroupByGroupId(int groupId) {

        List<Tag> listOfTags = new ArrayList<>();
        String sql = "SELECT id, tag_name FROM tags WHERE group_id = ?";

        GroupDao groupDao = new GroupDao();
        Group group = groupDao.getById(groupId);

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

    /**
     * Процедура удаления из БД записей о тегах по id группы
     *
     * @param groupId - id группы, теги которой удаляем
     */
    public void deleteAllTagsByGroupId(int groupId) throws SQLException {
        List<Tag> tagsInGroup = this.getAllTagsInGroupByGroupId(groupId);
        for (Tag tag : tagsInGroup) {
            deleteById(tag.getId());
        }
    }

    /**
     * Процедура определения id сохраненного тега {@link TagDao#save(Tag)}
     */
    private int setIdOfSavedTag() {
        String sql = "SELECT MAX(id) as last_tag_id FROM tags";

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = stm.executeQuery()) {
                resultSet.next();
                return resultSet.getInt("last_tag_id");
            }
        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("Невозможно получить информацию из БД.");
        }
    }

    /**
     * Процедура удаления тега по Id
     *
     * @param tagId - тег, данные которого удаляем {@link Tag}
     */
    public void deleteTagById(int tagId) throws SQLException {

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        connection.setAutoCommit(false);
        Savepoint savepointOne = connection.setSavepoint("SavepointOne");
        try {
            deleteById(tagId);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception e) {
            connection.rollback(savepointOne);
            throw new PhotoAnthropologyRuntimeException("Невозможно изменить данные в БД в ImageDao.delete(Image image).");
        }
    }

}
