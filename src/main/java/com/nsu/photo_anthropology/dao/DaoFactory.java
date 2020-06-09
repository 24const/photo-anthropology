package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.db_tools.DbTransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DaoFactory<E> implements Dao<E> {

    /**
     * Процедура удаления записи о соответствующей сущности из БД
     *
     * @param id - id удаляемой сущности
     */
    @Override
    public void deleteById(final int id) throws SQLException {

        final String sql = getDeleteSqlRequest();
        DbConnector dbConnector = DbConnector.getInstance();
        final Connection connection = dbConnector.getConnection();
        new DbTransaction() {
            @Override
            protected PreparedStatement executeUpdate() throws SQLException {
                PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stm.setInt(1, id);
                stm.executeUpdate();
                return stm;

            }
        }.runTransactions(connection);
    }
}
