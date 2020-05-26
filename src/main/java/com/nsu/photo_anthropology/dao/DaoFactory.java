package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.db_tools.DbConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

public abstract class DaoFactory<E> implements Dao<E> {

    @Override
    public void deleteById(int id) throws SQLException {

        String sql = getDeleteSqlRequest();
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        connection.setAutoCommit(false);
        Savepoint savepointOne = connection.setSavepoint("SavepointOne");
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            deleteRelatedEntities(id);
            stm.setInt(1, id);
            stm.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback(savepointOne);
            throw new PhotoAnthropologyRuntimeException("Ошивка в ходе выполнения транзакции.");
        } finally {
            connection.close();
        }
    }
}
