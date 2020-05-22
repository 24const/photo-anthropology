package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class DaoFactory<Entity> implements Dao<Entity> {

    @Override
    public int deleteById(int id) {

        String sql = getSqlRequest();
        int isSuccessfulRemove = 1;
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            stm.execute();
        } catch (SQLException e) {
            isSuccessfulRemove = 0;
        }
        return isSuccessfulRemove;
    }
}
