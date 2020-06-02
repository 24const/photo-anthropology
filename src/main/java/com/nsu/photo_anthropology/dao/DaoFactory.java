package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class DaoFactory<E> implements Dao<E> {

    /**
     * Процедура удаления записи о соответствующей сущности из БД
     *
     * @param id - id удаляемой сущности
     */
    @Override
    public void deleteById(int id) throws SQLException {

        String sql = getDeleteSqlRequest();
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("Ошивка в ходе выполнения транзакции.");
        }
    }
}
