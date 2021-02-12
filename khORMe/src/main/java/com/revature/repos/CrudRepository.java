package com.revature.repos;


import java.util.LinkedList;

/**
 *
 * @param <T>
 */
public interface CrudRepository<T> {

    void save(T newObj);
    LinkedList<T> findAll();
    T findById(int id);
    boolean update(T updatedObj);
    boolean deleteById(int id);

}
