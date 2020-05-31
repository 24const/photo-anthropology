package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

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
        connection.setAutoCommit(false);
        Savepoint savepointOne = connection.setSavepoint("SavepointOne");
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            //TODO: в целом транзация реализованно верно, для элементарного случая. Но, что
            //TODO: если у тебя в deleteRelatedEntities() будет строчка кода connection.setAutoCommit(true)
            //TODO: Чтобы избежать ошибки, нужно сделать обертку для транзакции, т.е. вынести в потоко-безопастный singlton
            //TODO: 1. Сперва напиши тест, который ломает данный метод 2. Затем сделай рефакторинг, вынеся транзакцию в Singltlton
            deleteRelatedEntities(id);
            stm.setInt(1, id);
            stm.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            connection.rollback(savepointOne);
            throw new PhotoAnthropologyRuntimeException("Ошивка в ходе выполнения транзакции.");
        }
    }
}
