package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.db_tools.DbTransaction;
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
    public int save(final Tag tag) throws SQLException {
        final String sql = "INSERT INTO tags(group_id, tag_name) VALUES(?, ?);";
        DbConnector dbConnector = DbConnector.getInstance();
        final Connection connection = dbConnector.getConnection();
        return new DbTransaction() {
            @Override
            protected PreparedStatement executeUpdate(){
                try {
                    PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    stm.setInt(1, tag.getGroupId());
                    stm.setString(2, tag.getTagName());
                    return stm;
                } catch (Exception e) {
                    throw new PhotoAnthropologyRuntimeException("Ошибка при сохранении информации о теге в " + TagDao.this.getClass().getName(), e);
                }
            }
        }.runTransactions(connection);
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
            throw new PhotoAnthropologyRuntimeException("Ошибка при получении информации о тегах из БД в TagDao.getAllTagsInGroup(Group group).", e);
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
     * Процедура удаления тега по Id
     *
     * @param tagId - тег, данные которого удаляем {@link Tag}
     */
    public void deleteTagById(int tagId) throws SQLException {
        try {
            deleteById(tagId);
        } catch (Exception e) {
            throw new PhotoAnthropologyRuntimeException("Невозможно изменить данные в БД в ImageDao.delete(Image image).", e);
        }
    }

    public int saveOnlyTag(Tag tag) throws SQLException {
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        int savedId = save(tag);
        DbTransaction.endTransaction(connection);
        return savedId;
    }


}
