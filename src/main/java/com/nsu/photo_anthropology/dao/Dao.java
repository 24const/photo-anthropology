package com.nsu.photo_anthropology.dao;

public interface Dao<Entity> {

    void save(Entity entity);
    void delete(Entity entity);

}
