package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDao extends DaoFactory<Group> {

    public static final String SQLDELETEREQUEST = "DELETE FROM groups WHERE id = ?";

    /**
     * Процедура сохранения данных о группе в таблице groups БД
     *
     * @param group - сохреняемая группа {@link Group}
     */
    @Override
    public void save(Group group) {
        String sql = "INSERT INTO groups (group_name, group_question) VALUES(?, ?);";
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, group.getGroupName());
            stm.setString(2, group.getGroupQuestion());
            stm.execute();
        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("Ошибка сохранения данных в БД в GroupDao.save(Group group).");
        }
    }

    /**
     * Функция получения значения поля {@link GroupDao#SQLDELETEREQUEST}
     *
     * @return возвращает SQL-запрос для удаления группы из таблицы groups БД по id
     */
    @Override
    public String getDeleteSqlRequest() {
        return SQLDELETEREQUEST;
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

        String sql = "SELECT * FROM groups where id = ?";
        Group group;

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            try (ResultSet resultSet = stm.executeQuery()) {
                resultSet.next();
                String groupName = resultSet.getString("group_name");
                String groupQuestion = resultSet.getString("group_question");
                group = new Group(id, groupName, groupQuestion);
            }
        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("Невозможно получить информацию из БД в GroupDao.getById(int id).");
        }
        return group;
    }

    /**
     * Процедура обновления данных группы в таблице groups БД
     *
     * @param group - группа, данные которой изменяем {@link Group}
     */
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
            throw new PhotoAnthropologyRuntimeException("Невозможно изменить данные в БД в GroupDao.update(Group group).");
        }
    }

    /**
     * Процедура удаления записей из таблицы tags по внешнему ключу
     *
     * @param id - родительский ключ
     */
    @Override
    public void deleteRelatedEntities(int id) {
        TagDao tagDao = new TagDao();
        tagDao.deleteAllTagsInGroup(id);
    }
}
