package com.nsu.photo_anthropology.dao;

public interface Dao<T> {

    void save(T t);
    void delete(T t);

}
