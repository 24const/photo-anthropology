package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.UploadedFile;

import java.sql.*;

public class FileDao extends DaoFactory<UploadedFile> implements Dao<UploadedFile> {

    public static final String SQL_DELETE_REQUEST = "DELETE FROM files WHERE id = ?";
    private int idOfSavedFile;

    /**
     * Процедура сохранения данных о файле в таблице files БД
     *
     * @param file - файл, данные о котором сохраняем {@link UploadedFile}
     */
    @Override
    public void save(UploadedFile file) throws SQLException {
        String sql = "INSERT INTO files(file_name, column_names, date_created) VALUES(?, ?::JSON, (SELECT NOW()))";
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
            throw new PhotoAnthropologyRuntimeException("Невозможно получить информацию из БД.");
        }
    }

    /**
     * Функция получения значения поля {@link FileDao#idOfSavedFile}
     *
     * @return возвращает id загруженного файла
     */
    public int getIdOfSavedFile() {
        return this.idOfSavedFile;
    }
}
