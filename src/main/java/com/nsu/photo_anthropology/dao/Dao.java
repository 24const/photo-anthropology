package com.nsu.photo_anthropology.dao;

import java.sql.SQLException;

public interface Dao<Entity> {

    void save(Entity entity);
    void deleteById(int id) throws SQLException;
    String getDeleteSqlRequest();
    int getId(Entity entity);
    void deleteRelatedEntities(int id) throws SQLException;
}
