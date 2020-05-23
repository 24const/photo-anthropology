package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

public abstract class DaoFactory<Entity> implements Dao<Entity> {

    @Override
    public void deleteById(int id) throws SQLException {

        String sql = getDeleteSqlRequest();
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        connection.setAutoCommit(false);
        Savepoint savepointOne = connection.setSavepoint("SavepointOne");
        try {
            deleteRelatedEntities(id);
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback(savepointOne);
            throw new RuntimeException(e);
        }
    }
}
