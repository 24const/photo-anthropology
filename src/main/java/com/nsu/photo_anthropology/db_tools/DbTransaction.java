package com.nsu.photo_anthropology.db_tools;

import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DbTransaction {

    private static boolean transactionStatus = true;

    private static synchronized void getTransactionStatus(Connection connection) throws SQLException {
        if (transactionStatus) {
            transactionStatus = false;
            connection.setAutoCommit(false);
        }
    }

    public int runTransactions(Connection connection) throws SQLException {

        getTransactionStatus(connection);

        try (PreparedStatement stm = executeUpdate()) {
            int savedId = 0;
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                savedId = rs.getInt(1);
            }
            rs.close();
            return savedId;
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            endTransaction(connection);
            throw new PhotoAnthropologyRuntimeException("Ошибка транзакции");
        }
    }

    protected abstract PreparedStatement executeUpdate() throws SQLException;

    public void endTransaction(Connection connection) throws SQLException {
        connection.setAutoCommit(true);
        transactionStatus = true;
    }
}
