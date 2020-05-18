package com.nsu.PhotoAnthropology.DAO;

import java.sql.SQLException;

public interface Dao<T> {

    void save(T t);
    void delete(T t);

}
