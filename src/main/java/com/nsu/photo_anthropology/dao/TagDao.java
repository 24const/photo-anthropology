package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.Group;
import com.nsu.photo_anthropology.structure_entities.Tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagDao extends DaoFactory<Tag> implements Dao<Tag> {

    public static final String SQLDELETEREQUEST = "DELETE FROM tags WHERE id = ?";

    /**
     * Процедура сохранения данных об теге в таблице tags БД
     *
     * @param tag - тег, данные о котором сохраняем {@link Tag}
     */
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

    /**
     * Функция получения значения поля {@link TagDao#SQLDELETEREQUEST}
     *
     * @return возвращает SQL-запрос для удаления записи о теге из таблицы tags БД по id
     */
    @Override
    public String getDeleteSqlRequest() {
        return SQLDELETEREQUEST;
    }

    /**
     * Функция получения данных из БД обо всех тегах группы
     *
     * @param group - группа, теги, которой узнаем
     * @return список всех тегов, содержащихся в группе
     */
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

    /**
     * Процедура удаления из БД записей о тегах по id группы
     *
     * @param id - id группы
     */
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

    /**
     * Процедура удаления записей из таблицы tagged_images по внешнему ключу
     *
     * @param id - родительский ключ
     */
    @Override
    public void deleteRelatedEntities(int id) throws SQLException {
        // На данной стадии не реализовано удаления тегов,
        // а следовательно, и удаление связанной информации
    }
}
