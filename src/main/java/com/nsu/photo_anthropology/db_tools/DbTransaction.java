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

    public static void endTransaction(Connection connection) throws SQLException {
        connection.setAutoCommit(true);
        transactionStatus = true;
    }

    public int runTransactions(Connection connection) throws SQLException {

        getTransactionStatus(connection);

        try (PreparedStatement stm = executeUpdate()) {
            stm.executeUpdate();
            int savedId = 0;
            try (ResultSet rs = stm.getGeneratedKeys()) {
                if (rs.next()) {
                    savedId = rs.getInt(1);
                }
            }
            return savedId;
        } catch (Exception e) {
            connection.rollback();
            throw new PhotoAnthropologyRuntimeException("Ошибка транзакции", e);
        }
    }

    protected abstract PreparedStatement executeUpdate() throws SQLException;
}
