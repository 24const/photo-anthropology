package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.db_tools.DbTransaction;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.Image;
import com.nsu.photo_anthropology.structure_entities.UploadedFile;

import java.sql.*;

public class ImageDao extends DaoFactory<Image> implements Dao<Image> {

    private static final String SQL_DELETE_REQUEST = "DELETE FROM images WHERE id = ?";
    private int uploadFileId;

    /**
     * Процедура сохранения данных об изображении при сохранении файла в таблице images БД
     *
     * @param image - изображение, данные о котором сохраняем {@link Image}
     * @return - Возвращает id сохраненного изображения
     */
    @Override
    public int save(final Image image) throws SQLException {
        final String sql = "INSERT INTO images(file_id, image_path, other_information) VALUES(?, ?, ?::JSON)";
        DbConnector dbConnector = DbConnector.getInstance();
        final Connection connection = dbConnector.getConnection();
        return new DbTransaction() {
            @Override
            protected PreparedStatement executeUpdate() throws SQLException {
                try {
                    PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    stm.setInt(1, uploadFileId);
                    stm.setString(2, image.getImagePath());
                    stm.setObject(3, image.getOtherInformation().toJSONString());
                    return stm;
                } catch (Exception e) {
                    throw new PhotoAnthropologyRuntimeException("Ошибка при сохранении информации об изображении в " + ImageDao.this.getClass().getName());
                }
            }
        }.runTransactions(connection);
    }

    /**
     * Функция получения значения поля {@link ImageDao#SQL_DELETE_REQUEST}
     *
     * @return возвращает SQL-запрос для удаления записи об изображении из таблицы images БД по id
     */
    @Override
    public String getDeleteSqlRequest() {
        return SQL_DELETE_REQUEST;
    }

    /**
     * Функция получения значения поля {@link ImageDao#uploadFileId}
     *
     * @return возвращает id файла, в котором содержится данное изображение
     */
    public int getUploadFileId() {
        return this.uploadFileId;
    }

    /**
     * Процедура определения  {@link ImageDao#uploadFileId}
     *
     * @param uploadFileId - id файла, в котором содержится данное изображение
     */
    public void setUploadFileId(int uploadFileId) {
        this.uploadFileId = uploadFileId;
    }

    /**
     * Процедура удаления изображения по Id
     *
     * @param imageId - изображение, данные которого удаляем {@link UploadedFile}
     */
    public void deleteImageById(int imageId) throws SQLException {
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        connection.setAutoCommit(false);
        Savepoint savepointOne = connection.setSavepoint("SavepointOne");
        try {
            deleteById(imageId);
            connection.commit();
        } catch (Exception e) {
            connection.rollback(savepointOne);
            throw new PhotoAnthropologyRuntimeException("Невозможно изменить данные в БД в ImageDao.delete(Image image).");
        } finally {
            connection.setAutoCommit(true);
        }
    }

    /**
     * Процедура сохранения данных об изображении отдельно от файла в таблице images БД
     *
     * @param image - изображение, данные о котором сохраняем {@link Image}
     * @return - Возвращает id сохраненного изображения
     */
    public int saveOnlyImage(Image image) throws SQLException {
        int savedId = save(image);
        DbConnector dbConnector = DbConnector.getInstance();
        DbTransaction.endTransaction(dbConnector.getConnection());
        return savedId;
    }
}
