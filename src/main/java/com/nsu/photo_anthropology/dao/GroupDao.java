package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.Group;
import com.nsu.photo_anthropology.structure_entities.Tag;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDao extends DaoFactory<Group> {

    public static final String SQL_DELETE_REQUEST = "DELETE FROM groups WHERE id = ?";
    private int idOfSavedGroup;

    /**
     * Процедура сохранения данных о группе в таблице groups БД
     *
     * @param group - сохреняемая группа {@link Group}
     */
    @Override
    public void save(Group group) throws SQLException {
        String sql = "INSERT INTO groups (group_name, group_question) VALUES(?, ?);";
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        connection.setAutoCommit(false);
        Savepoint savepointOne = connection.setSavepoint("SavepointOne");
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, group.getGroupName());
            stm.setString(2, group.getGroupQuestion());
            stm.executeUpdate();
            this.setIdOfSavedFile();
            if (group.getTagsInGroup() != null) {
                this.saveTagsInGroup(group);
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            connection.rollback(savepointOne);
            throw new PhotoAnthropologyRuntimeException("Ошибка сохранения данных в БД в GroupDao.save(Group group).");
        }
    }

    /**
     * Процедура определения id охраненной группы {@link GroupDao#save(Group)}
     */
    private void setIdOfSavedFile() {
        String sql = "SELECT MAX(id) as last_group_id FROM groups";

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = stm.executeQuery()) {
                resultSet.next();
                this.idOfSavedGroup = resultSet.getInt("last_group_id");
            }
        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("Невозможно получить информацию из БД.");
        }
    }

    /**
     * Функция получения значения поля {@link GroupDao#idOfSavedGroup}
     *
     * @return возвращает id загруженной группы
     */
    public int getIdOfSavedGroup() {
        return idOfSavedGroup;
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
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        connection.setAutoCommit(false);
        Savepoint savepointOne = connection.setSavepoint("SavepointOne");
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, group.getGroupName());
            stm.setString(2, group.getGroupQuestion());
            stm.setInt(3, group.getId());
            stm.executeUpdate();
            this.updateTagsInGroup(group);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            connection.rollback(savepointOne);
            throw new PhotoAnthropologyRuntimeException("Невозможно изменить данные в БД в GroupDao.update(Group group).");
        }
    }

    /**
     * Процедура сохранения тегов в таблице tags БД
     *
     * @param group - группа, данные которой сохраняем {@link Group}
     */
    private void saveTagsInGroup(Group group) {
        TagDao tagDao = new TagDao();
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
     * Процедура замены сужествующих тегов в таблице tags БД новыми при обновлении группы
     *
     * @param group - группа, данные которой изменяем {@link Group}
     */
    private void updateTagsInGroup(Group group) throws SQLException {
        TagDao tagDao = new TagDao();
        tagDao.deleteAllTagsByGroupId(group.getId());
        this.saveTagsInGroup(group);
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
        Savepoint savepointOne = connection.setSavepoint("SavepointOne");
        try {
            tagDao.deleteAllTagsByGroupId(groupId);
            deleteById(groupId);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception e) {
            connection.rollback(savepointOne);
            throw new PhotoAnthropologyRuntimeException("Невозможно изменить данные в БД в GroupDao.update(Group group).");
        }
        System.out.println("Почему данная конструкция не работает?????7");
    }
}
