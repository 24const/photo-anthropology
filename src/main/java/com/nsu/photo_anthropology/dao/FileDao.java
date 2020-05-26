package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.structure_entities.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FileDao extends DaoFactory<File> implements Dao<File> {

    public static final String SQLDELETEREQUEST = "DELETE FROM files WHERE id = ?";

    @Override
    public void save(File file) {
        String sql = "INSERT INTO files(file_name, column_names) VALUES(?, ?::JSON)";
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, file.getFileName());
            stm.setObject(2, file.getColumnNames().toJSONString());
            stm.execute();
        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("Ошибка сохранения данных в БД в FileDao.save(File file)");
        }
    }

    @Override
    public String getDeleteSqlRequest() {
        return SQLDELETEREQUEST;
    }

    @Override
    public int getId(File file) {
        return file.getId();
    }

    @Override
    public void deleteRelatedEntities(int id) {
        // На данной стадии не реализовано удаление файлов,
        // а следовательно, и удаление связанной информации
    }
}
