package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.Image;
import com.nsu.photo_anthropology.structure_entities.UploadedFile;

import java.sql.*;

public class ImageDao extends DaoFactory<Image> implements Dao<Image> {

    private static final String SQL_DELETE_REQUEST = "DELETE FROM images WHERE id = ?";
    private int uploadFileId;

    /**
     * Процедура сохранения данных об изображении в таблице images БД
     *
     * @param image - изображение, данные о котором сохраняем {@link Image}
     * @return - Возвращает id сохраненного изображения
     */
    @Override
    public int save(Image image) {
        String sql = "INSERT INTO images(file_id, image_path, other_information) VALUES(?, ?, ?::JSON)";
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, this.uploadFileId);
            stm.setString(2, image.getImagePath());
            stm.setObject(3, image.getOtherInformation().toJSONString());
            stm.execute();
            return setIdOfSavedImage();
        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("Ошибка сохранения данных в БД в ImageDao.save(Image image).");
        }
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
            connection.setAutoCommit(true);
        } catch (Exception e) {
            connection.rollback(savepointOne);
            throw new PhotoAnthropologyRuntimeException("Невозможно изменить данные в БД в ImageDao.delete(Image image).");
        }
    }

    /**
     * Процедура определения id сохраненного изображения {@link ImageDao#save(Image)}
     */
    private int setIdOfSavedImage() {
        String sql = "SELECT MAX(id) as last_image_id FROM images";

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = stm.executeQuery()) {
                resultSet.next();
                return resultSet.getInt("last_image_id");
            }
        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("Невозможно получить информацию из БД.");
        }
    }
}
