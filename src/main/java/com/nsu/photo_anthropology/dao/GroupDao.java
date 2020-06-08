package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.db_tools.DbTransaction;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.Group;
import com.nsu.photo_anthropology.structure_entities.Tag;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDao extends DaoFactory<Group> {

    public static final String SQL_DELETE_REQUEST = "DELETE FROM groups WHERE id = ?";

    /**
     * Процедура сохранения данных о группе в таблице groups БД
     *
     * @param group - сохреняемая группа {@link Group}
     * @return - Возвращает id сохраненной группы
     */
    @Override
    public int save(final Group group) throws SQLException {
        final String sql = "INSERT INTO groups (group_name, group_question) VALUES(?, ?);";
        return writingGroupToDb(group, sql);
    }

    /**
     * Процедура сохранения данных о группе в таблице groups БД
     *
     * @param group - группа, над которой проводим действие{@link Group}
     * @param sql   - действие, которое проводим над группой(сохранение/изменение)
     * @return - Возвращает id сохраненной группы
     */
    private int writingGroupToDb(final Group group, final String sql) throws SQLException {
        DbConnector dbConnector = DbConnector.getInstance();
        final Connection connection = dbConnector.getConnection();
        int savedId = new DbTransaction() {
            @Override
            protected PreparedStatement executeUpdate() throws SQLException {
                try {
                    PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    stm.setString(1, group.getGroupName());
                    stm.setString(2, group.getGroupQuestion());
                    if (group.getId() != -1) {
                        stm.setInt(3, group.getId());
                    }
                    stm.executeUpdate();
                    if (group.getTagsInGroup() != null) {
                        saveTagsInGroup(group);
                    }
                    return stm;
                } catch (Exception e) {
                    throw new PhotoAnthropologyRuntimeException("Ошибка при сохранении информации о группе в " + GroupDao.this.getClass().getName());
                }
            }
        }.runTransactions(connection);
        DbTransaction.endTransaction(connection);
        return savedId;
    }

    /**
     * Функция получения значения поля {@link GroupDao#SQL_DELETE_REQUEST}
     *
     * @return возвращает SQL-запрос для удаления группы из таблицы groups БД по id
     */
    @Override
    public String getDeleteSqlRequest() {
        return SQL_DELETE_REQUEST;
    }

    /**
     * Функция получения данных обо всех группах в БД
     *
     * @return список всех групп, содержащихся в таблице groups БД
     */
    public List<Group> getAll() {

        List<Group> listOfGroups = new ArrayList<>();
        String sql = "SELECT * FROM groups;";

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = stm.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String groupName = resultSet.getString("group_name");
                    String groupQuestion = resultSet.getString("group_question");
                    listOfGroups.add(new Group(id, groupName, groupQuestion));
                }
            }

        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("Невозможно считать данные из БД в методе GroupDao.getAll()");
        }
        return listOfGroups;
    }

    /**
     * Функция получения данных группе из таблицы groups БД
     *
     * @param id - id группы, информацию о которой необходимо получить из БД
     * @return возвращает объект {@link Group}
     */
    public Group getById(int id) {

        List<Group> allGroups = this.getAll();
        Group returnedGroup = null;

        for (Group group : allGroups) {
            if (group.getId() == id) {
                returnedGroup = group;
            }
        }
        return returnedGroup;
    }

    /**
     * Процедура обновления данных группы в таблице groups БД
     *
     * @param group - группа, данные которой изменяем {@link Group}
     */
    public void update(Group group) throws SQLException {
        String sql = "UPDATE groups SET group_name = ?, group_question = ? where id = ?";
        writingGroupToDb(group, sql);
    }

    /**
     * Процедура сохранения тегов в таблице tags БД
     *
     * @param group - группа, данные которой сохраняем {@link Group}
     */
    private void saveTagsInGroup(Group group) throws SQLException {
        TagDao tagDao = new TagDao();
        tagDao.deleteAllTagsByGroupId(group.getId());
        if (!group.getTagsInGroup().equalsIgnoreCase("")) {
            String[] tagsInGroup = group.getTagsInGroup().split(",");
            for (String tag : tagsInGroup) {
                if (tag.indexOf(' ') == 0) {
                    tag = tag.replaceFirst(" ", "");
                }
                tagDao.save(new Tag(tag, group.getGroupName()));
            }
        }
    }

    /**
     * Процедура удаления группы с тегами по Id группы
     *
     * @param groupId - группа, данные которой изменяем {@link Group}
     */
    public void deleteGroupById(int groupId) throws SQLException {

        TagDao tagDao = new TagDao();

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        connection.setAutoCommit(false);
        Savepoint savepointOne = connection.setSavepoint("SavepointThree");
        try {
            tagDao.deleteAllTagsByGroupId(groupId);
            deleteById(groupId);
            connection.commit();
        } catch (Exception e) {
            connection.rollback(savepointOne);
            throw new PhotoAnthropologyRuntimeException("Невозможно удалить данные из БД.");
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
