package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.UploadedFile;

import java.sql.*;

public class FileDao extends DaoFactory<UploadedFile> implements Dao<UploadedFile> {

    public static final String SQL_DELETE_REQUEST = "DELETE FROM files WHERE id = ?";

    /**
     * Процедура сохранения данных о файле в таблице files БД
     *
     * @param file - файл, данные о котором сохраняем {@link UploadedFile}
     * @return - Возвращает id сохраненного файла
     */
    @Override
    public int save(UploadedFile file) throws SQLException {
        String sql = "INSERT INTO files(file_name, column_names, date_created) VALUES(?, ?::JSON, (SELECT NOW()))";
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        connection.setAutoCommit(false);
        Savepoint savepointOne = connection.setSavepoint("SavepointOne");
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, file.getFileName());
            stm.setObject(2, file.getColumnNames().toJSONString());
            stm.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
            return setIdOfSavedFile();
        } catch (SQLException e) {
            connection.rollback(savepointOne);
            throw new PhotoAnthropologyRuntimeException("Ошибка сохранения данных в БД в FileDao.save(File file)");
        }
    }

    /**
     * Функция получения значения поля {@link FileDao#SQL_DELETE_REQUEST}
     *
     * @return возвращает SQL-запрос для удаления записи о файле из таблицы files БД по id
     */
    @Override
    public String getDeleteSqlRequest() {
        return SQL_DELETE_REQUEST;
    }

    /**
     * Процедура определения id охраненногоо файла {@link FileDao#save(UploadedFile)}
     */
    private int setIdOfSavedFile() {
        String sql = "SELECT MAX(id) as last_file_id FROM files";

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = stm.executeQuery()) {
                resultSet.next();
                return resultSet.getInt("last_file_id");
            }
        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("Невозможно получить информацию из БД.");
        }
    }

    /**
     * Процедура удаления файла по Id
     *
     * @param fileId - файл, данные которого удаляем {@link UploadedFile}
     */
    public void deleteFileById(int fileId) throws SQLException {
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        connection.setAutoCommit(false);
        Savepoint savepointOne = connection.setSavepoint("SavepointOne");
        try {
            deleteById(fileId);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception e) {
            connection.rollback(savepointOne);
            throw new PhotoAnthropologyRuntimeException("Невозможно изменить данные в БД в FileDao.delete(File file).");
        }
    }
}
