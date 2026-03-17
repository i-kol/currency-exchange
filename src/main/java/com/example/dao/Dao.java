package com.example.dao;

import java.util.List;

public interface Dao<K, T> {

    List<T> findAll();

    Long save(T entity);
}
