package org.example.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, K> {
    T save(T t);

    void update(T t);

    boolean deleteById(K id);

    Optional<T> findById(K id);

    List<T> findAll();

    boolean exitsById(K id);
}
