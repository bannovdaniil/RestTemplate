package org.example.repository;

public interface SimpleRepository<T, K> {
    T findById(K id);

    boolean deleteById(K id);

    T findAll();

    T save(T t);
}
