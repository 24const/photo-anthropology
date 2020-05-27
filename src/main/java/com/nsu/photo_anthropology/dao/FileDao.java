package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.File;

import java.sql.*;

public class FileDao extends DaoFactory<File> implements Dao<File> {

    public static final String SQLDELETEREQUEST = "DELETE FROM files WHERE id = ?";
    private int idOfSavedFile;

    @Override
    public void save(File file) throws SQLException {
        String sql = "INSERT INTO files(file_name, column_names) VALUES(?, ?::JSON)";
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        connection.setAutoCommit(false);
        Savepoint savepointOne = connection.setSavepoint("SavepointOne");
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, file.getFileName());
            stm.setObject(2, file.getColumnNames().toJSONString());
            stm.executeUpdate();
            setIdOfSavedFile();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            connection.rollback(savepointOne);
            throw new PhotoAnthropologyRuntimeException("Ошибка сохранения данных в БД в FileDao.save(File file)");
        }
    }

    @Override
    public String getDeleteSqlRequest() {
        return SQLDELETEREQUEST;
    }

    @Override
    public void deleteRelatedEntities(int id) {
        // На данной стадии не реализовано удаление файлов,
        // а следовательно, и удаление связанной информации
    }

    private void setIdOfSavedFile() {
        String sql = "SELECT MAX(id) as last_file_id FROM files";

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = stm.executeQuery()) {
                resultSet.next();
                this.idOfSavedFile = resultSet.getInt("last_file_id");
            }
        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("Невозможно получить информацию из БД в GroupDao.getById(int id).");
        }
    }

    public int getIdOfSavedFile() {
        return this.idOfSavedFile;
    }
}
