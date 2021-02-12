package com.revature.model;

import java.util.LinkedList;

public class QueryBuilder implements CrudQueryBuilder {
    @Override
    public void save(Object newObj) {

    }

    @Override
    public LinkedList findAll() {
        return null;
    }

    @Override
    public Object findById(int id) {
        return null;
    }

    @Override
    public boolean update(Object updatedObj) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
