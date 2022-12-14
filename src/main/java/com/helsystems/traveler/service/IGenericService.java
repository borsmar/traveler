package com.helsystems.traveler.service;

import java.util.List;

public interface IGenericService<T> {
    List<T> findAll();
    T save(T entity);
    T update(T entity, Long id);
    T findById(Long id);
    void deleteById(Long id);
}

