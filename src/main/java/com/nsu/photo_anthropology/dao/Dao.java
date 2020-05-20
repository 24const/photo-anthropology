package com.nsu.photo_anthropology.dao;

public interface Dao<T> {

//FIXME: что такое t? лучше использовать понятные названия, например entity
    void save(T t);
    void delete(T t);

}
