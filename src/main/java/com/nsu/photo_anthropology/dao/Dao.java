package com.nsu.photo_anthropology.dao;

import java.sql.SQLException;

public interface Dao<E> {

    void save(E entity);

    void deleteById(int id) throws SQLException;

    String getDeleteSqlRequest();

    int getId(E entity);

    void deleteRelatedEntities(int id) throws SQLException;
}
