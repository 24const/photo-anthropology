package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.structure_entities.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImageDao extends DaoFactory<Image> implements Dao<Image> {

    public static final String SQLDELETEREQUEST = "DELETE FROM images WHERE id = ?";

    @Override
    public void save(Image image) {
        String sql = "INSERT INTO images(file_id, image_path, other_information) VALUES((SELECT id FROM files WHERE file_name = ?), ?, ?::JSON);";
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, image.getFileName());
            stm.setString(2, image.getImagePath());
            stm.setObject(3, image.getOtherInformation().toJSONString());
            stm.execute();
        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("Ошибка сохранения данных в БД в ImageDao.save(Image image).");
        }
    }

    @Override
    public String getDeleteSqlRequest() {
        return SQLDELETEREQUEST;
    }

    @Override
    public int getId(Image image) {
        return image.getId();
    }


    @Override
    public void deleteRelatedEntities(int id) {
        // На данной стадии не реализовано удаления изображений,
        // а следовательно, и удаление связанной информации
    }
}
