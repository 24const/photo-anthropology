package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.db_tools.DbTransaction;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.Image;
import com.nsu.photo_anthropology.structure_entities.UploadedFile;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            protected PreparedStatement executeUpdate() {
                try {
                    PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    stm.setInt(1, uploadFileId);
                    stm.setString(2, image.getImagePath());
                    stm.setObject(3, image.getOtherInformation().toJSONString());
                    return stm;
                } catch (Exception e) {
                    throw new PhotoAnthropologyRuntimeException("Ошибка при сохранении информации об изображении в " + ImageDao.this.getClass().getName(), e);
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
     * Процедура определения  {@link ImageDao#uploadFileId}
     *
     * @param uploadFileId - id файла, в котором содержится данное изображение
     */
    public void setUploadFileId(int uploadFileId) {
        this.uploadFileId = uploadFileId;
    }

    /**
     * Процедура удаления изображения по Id в транзакци
     *
     * @param imageId - изображение, данные которого удаляем {@link UploadedFile}
     */
    public void deleteImageById(int imageId) throws SQLException {
        deleteById(imageId);
    }

    /**
     * Процедура сохранения данных об изображении отдельно от файла в таблице images БД
     *
     * @param image - изображение, данные о котором сохраняем {@link Image}
     * @return - Возвращает id сохраненного изображения
     */
    public int saveOnlyImage(Image image) throws SQLException {
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        int savedId = save(image);
        DbTransaction.endTransaction(connection);
        return savedId;
    }

    /**
     * Функция получения файла из таблицы images БД
     *
     * @return возвращает информацию об изображении, содержащуюся под указанным id в базе данных
     */
    public Image getImageById(int imageId) {
        String sql = "SELECT images.id as id, files.file_name as file_name, image_path, other_information FROM images JOIN files ON images.file_id = files.id WHERE images.id = ?;";

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        Image image = null;
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, imageId);
            try (ResultSet resultSet = stm.executeQuery()) {
                resultSet.next();
                int id = resultSet.getInt("id");
                String fileName = resultSet.getString("file_name");
                String imagePath = resultSet.getString("image_path");
                image = new Image(id, fileName, imagePath, null);
            }
            return image;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Процедура отдельного удаления изображения по Id
     *
     * @param id - изображение, данные которого удаляем {@link UploadedFile}
     */
    public void deleteOnlyImageById(int id) throws SQLException {
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        deleteImageById(id);
        DbTransaction.endTransaction(connection);
    }

    /**
     * Функция получения данных из БД обо всех изображениях в файле
     *
     * @param fileId - id файла, изображения которого получаем
     * @return список всех изображений, содержащихся в файле
     */
    public List<Image> getAllImagesInFileByFileId(int fileId) {

        List<Image> listOfImages = new ArrayList<>();
        String sql = "SELECT images.id as id, files.file_name as file_name, image_path, other_information FROM images JOIN files ON images.file_id = files.id WHERE files.id = ?;";

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, fileId);

            try (ResultSet resultSet = stm.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String fileName = resultSet.getString("file_name");
                    String imagePath = resultSet.getString("image_path");
                    listOfImages.add(new Image(id, fileName, imagePath, null));
                }
            }
        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("Ошибка при получении информации о тегах из БД в TagDao.getAllTagsInGroup(Group group).", e);
        }
        return listOfImages;
    }

    /**
     * Процедура удаления из БД записей об изображениях по id файла, в котором они содержатся
     *
     * @param fileId - id файла, изображения которого удаляем
     */
    public void deleteAllImagesByFileId(int fileId) throws SQLException {
        List<Image> deletedImages = getAllImagesInFileByFileId(fileId);
        for (Image image : deletedImages) {
            deleteById(image.getId());
        }
    }

}
