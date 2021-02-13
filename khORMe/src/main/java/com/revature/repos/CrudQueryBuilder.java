package com.revature.repos;


import java.util.LinkedList;

/**
 * interface to be implemented by user repository
 * @param <T>
 */
public interface CrudQueryBuilder<T> {

    void save(T newObj);
    LinkedList<T> findAll();
    T findById(int id);
    boolean update(T updatedObj);
    boolean deleteById(int id);

}
