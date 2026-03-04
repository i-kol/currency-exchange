package com.example.dao;

import java.util.List;

public interface Dao<K, T> {

    List<T> findAll();

    T save(T entity);
}
