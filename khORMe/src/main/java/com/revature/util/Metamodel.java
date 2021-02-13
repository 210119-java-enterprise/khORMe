package com.revature.util;

import com.revature.annotations.PrimaryKey;
import com.revature.annotations.Column;
import com.revature.annotations.Table;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Metamodel<T> {

    private Class<T> cls;

    public static <T> Metamodel<T> of(Class<T> clazz) {
        return new Metamodel<>(clazz);
    }

    public Metamodel(Class<T> clazz) {
        this.cls = clazz;
    }


    public Class getThisClass() {
        return cls;
    }

    public TableData getTable() {
        Table tbl = cls.getAnnotation(Table.class);
        return new TableData(cls.getSimpleName(), tbl.name());
    }


    public KeyField getPrimaryKey() {
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
            if (primaryKey != null) {
                return new KeyField(field);
            }
        }
        throw new RuntimeException("Did not find a field annotated with @Id in: " + cls.getName());
    }

    public List<ColumnField> getColumns() {
        List<ColumnField> columnFields = new ArrayList<>();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                columnFields.add(new ColumnField(field));
            }
        }

        if (columnFields.isEmpty()) {
            throw new RuntimeException("No columns found in: " + cls.getName());
        }

        return columnFields;
    }


//    public List<ColumnField> getColumnSetMethods() {
//        List<ColumnField> columnFields = new ArrayList<>();
//        Method[] methods = cls.getDeclaredMethods();
//        for (Method meth : methods) {
//            Column column = field.getAnnotation(Column.class);
//            if (column != null) {
//                columnFields.add(new ColumnField(field));
//            }
//        }
//
//        if (columnFields.isEmpty()) {
//            throw new RuntimeException("No columns found in: " + cls.getName());
//        }
//
//        return columnFields;
//    }










}
