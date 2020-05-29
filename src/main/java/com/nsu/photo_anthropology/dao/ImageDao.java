package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImageDao extends DaoFactory<Image> implements Dao<Image> {

    public static final String SQLDELETEREQUEST = "DELETE FROM images WHERE id = ?";
    protected int uploadFileId;

    /**
     * Процедура сохранения данных об изображении в таблице images БД
     *
     * @param image - изображение, данные о котором сохраняем {@link Image}
     */
    @Override
    public void save(Image image) {
        String sql = "INSERT INTO images(file_id, image_path, other_information) VALUES(?, ?, ?::JSON)";
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, this.uploadFileId);
            stm.setString(2, image.getImagePath());
            stm.setObject(3, image.getOtherInformation().toJSONString());
            stm.execute();
        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("Ошибка сохранения данных в БД в ImageDao.save(Image image).");
        }
    }

    /**
     * Функция получения значения поля {@link ImageDao#SQLDELETEREQUEST}
     *
     * @return возвращает SQL-запрос для удаления записи об изображении из таблицы images БД по id
     */
    @Override
    public String getDeleteSqlRequest() {
        return SQLDELETEREQUEST;
    }

    /**
     * Процедура удаления записей из таблицы tagged_images по внешнему ключу
     *
     * @param id - родительский ключ
     */
    @Override
    public void deleteRelatedEntities(int id) {
        // На данной стадии не реализовано удаления изображений,
        // а следовательно, и удаление связанной информации
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
}
