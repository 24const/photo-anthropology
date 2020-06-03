package com.nsu.photo_anthropology.dao;

import java.sql.SQLException;

public interface Dao<E> {

    int save(E entity) throws SQLException;

    void deleteById(int id) throws SQLException;

    String getDeleteSqlRequest();
}
