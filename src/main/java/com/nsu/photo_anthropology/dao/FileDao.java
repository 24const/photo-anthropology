package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.db_tools.DbTransaction;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.Image;
import com.nsu.photo_anthropology.structure_entities.UploadedFile;

import java.sql.*;
import java.util.List;

public class FileDao extends DaoFactory<UploadedFile> implements Dao<UploadedFile> {

    public static final String SQL_DELETE_REQUEST = "DELETE FROM files WHERE id = ?";

    /**
     * Процедура сохранения данных о файле в таблице files БД
     *
     * @param file - файл, данные о котором сохраняем {@link UploadedFile}
     * @return - Возвращает id сохраненного файла
     */
    @Override
    public int save(final UploadedFile file) throws SQLException {
        final String sql = "INSERT INTO files(file_name, column_names, date_created) VALUES(?, ?::JSON, (SELECT NOW()))";
        DbConnector dbConnector = DbConnector.getInstance();
        final Connection connection = dbConnector.getConnection();
        int savedId = new DbTransaction() {
            @Override
            protected PreparedStatement executeUpdate() throws SQLException {
                try {
                    PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    stm.setString(1, file.getFileName());
                    stm.setObject(2, file.getColumnNames().toJSONString());
                    if (file.getImagesInFile() != null) {
                        saveImagesFromFile(file, -1);
                    }
                    return stm;
                } catch (Exception e) {
                    throw new PhotoAnthropologyRuntimeException("Ошибка при сохранении информации о файле в " + FileDao.this.getClass().getName());
                }
            }
        }.runTransactions(connection);
        DbTransaction.endTransaction(connection);
        return savedId;
    }


    private void saveImagesFromFile(UploadedFile file, int idOfSavedFile) throws SQLException {
        ImageDao imageDao = new ImageDao();
        imageDao.setUploadFileId(idOfSavedFile);
        List<Image> imagesInFile = file.getImagesInFile();
        for (Image image : imagesInFile) {
            imageDao.save(image);
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
        } catch (Exception e) {
            connection.rollback(savepointOne);
            throw new PhotoAnthropologyRuntimeException("Невозможно изменить данные в БД в FileDao.delete(File file).");
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public UploadedFile getFileById(int fileId) {

        String sql = "SELECT * FROM files WHERE id = ?;";

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, fileId);
            try (ResultSet resultSet = stm.executeQuery()) {
                resultSet.next();
                int id = resultSet.getInt("id");
                String fileName = resultSet.getString("file_name");
                String columnNames = resultSet.getString("column_names");
                return new UploadedFile(id, fileName, null);
            }
        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("Невозможно считать данные из БД в методе FileDao.getFileByID");
        }
    }
}
