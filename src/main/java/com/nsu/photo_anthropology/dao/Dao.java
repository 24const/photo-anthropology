package com.nsu.photo_anthropology.dao;

import java.util.List;

public interface Dao<Entity> {

    void save(Entity entity);
    int deleteById(int id);
    String getSqlRequest();
    int getId(Entity entity);
}
