package com.revature.forum.repos;


import java.util.LinkedList;

/**
 * interface to be implemented by user repository
 * @param <T>
 */
public interface CrudRepository<T> {

    void save(T newObj);
    LinkedList<T> findAll();
    T findById(int id);
    boolean update(T updatedObj);
    boolean deleteById(int id);

}
