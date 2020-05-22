package com.nsu.photo_anthropology.dao;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.structure_entities.File;
import com.nsu.photo_anthropology.structure_entities.Group;
import com.nsu.photo_anthropology.structure_entities.Tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TagDao extends DaoFactory<Tag> implements Dao<Tag>{

    public final static String TABLE_NAME = "tags";

    @Override
    public void save(Tag tag){
        String sql = "INSERT INTO tags(group_id, tag_name) VALUES(?, ?);";
        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, tag.getGroup_id());
            stm.setString(2, tag.getTag_name());
            stm.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public int getId(Tag tag) {
        return tag.getId();
    }

}
