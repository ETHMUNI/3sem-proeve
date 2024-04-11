package org.example.DAO;

import org.example.DTO.HealthProductDTO;

import java.util.Set;

public interface iDAO <T, V> {
    Set<T> getAll();
    T getById(int id);
    T create(V v);
    T update(V v);
    T delete(int id);
}
