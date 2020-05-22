package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.structure_entities.File;
import com.nsu.photo_anthropology.structure_entities.Group;
import com.nsu.photo_anthropology.structure_entities.Image;
import com.nsu.photo_anthropology.structure_entities.Tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DaoFactory<Entity>{

    public void delete(Entity entity){
        String sql="";
        int id = 0;

        if (File.class.equals(entity.getClass())) {
            sql = "DELETE FROM files WHERE id = ?";
            id = ((File) entity).getId();
        } else if (Group.class.equals(entity.getClass())) {
            sql = "DELETE FROM groups WHERE id = ?";
            id = ((Group) entity).getId();
        } else if (Image.class.equals(entity.getClass())) {
            sql = "DELETE FROM images WHERE id = ?";
            id = ((Image) entity).getId();
        } else if (Tag.class.equals(entity.getClass())) {
            sql = "DELETE FROM tags WHERE id = ?";
            id = ((Tag) entity).getId();
        }
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
