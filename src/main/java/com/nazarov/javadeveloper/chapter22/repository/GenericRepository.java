package com.nazarov.javadeveloper.chapter22.repository;

public interface GenericRepository<T, ID> {
    T save(T entity);
    T get(ID primaryKey);
    T update(T entity);
    void remove(ID primaryKey);
}