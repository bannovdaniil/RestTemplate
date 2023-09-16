package org.example.repository;

import org.example.repository.exception.NotFoundException;

import java.util.List;

public interface UserRepository<T, K> {
    T findById(K id) throws NotFoundException;

    boolean deleteById(K id);

    List<T> findAll();

    T save(T t);
}
