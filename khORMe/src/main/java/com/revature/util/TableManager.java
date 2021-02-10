package com.revature.util;

import java.util.List;

public class TableManager<T> {
    private List<Metamodel<T>> tables;

    public void add(Metamodel<T> mm){
        tables.add(mm);
    }
}
