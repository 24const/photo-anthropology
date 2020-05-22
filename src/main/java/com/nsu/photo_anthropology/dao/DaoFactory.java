package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.structure_entities.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class DaoFactory<Entity> implements Dao<Entity>{

    @Override
    public void delete(Entity entity){
        String sql="DELETE FROM ? WHERE id = ?";
        String tableName = getTableName();
        int id = getId(entity);

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, tableName);
            stm.setInt(2, id);
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
