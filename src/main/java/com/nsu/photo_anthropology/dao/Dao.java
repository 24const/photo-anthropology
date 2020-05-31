package com.nsu.photo_anthropology.dao;

import java.sql.SQLException;

public interface Dao<E> {

    void save(E entity) throws SQLException;

    void deleteById(int id) throws SQLException;

    String getDeleteSqlRequest();

    //TODO: данный метод не требуется и только путает. Т.к. пользователю DAO слоя, ничего не нужно знать о связных сущностях БД
    //TODO: Т.е. удаление связных записях должно также быть скрыто в методе deleteById
    void deleteRelatedEntities(int id) throws SQLException;
}
