package com.revature.util;

import com.revature.annotations.PrimaryKey;

import java.lang.reflect.Field;

public class KeyField {

    private Field field;

    public KeyField(Field field) {
        if (field.getAnnotation(PrimaryKey.class) == null) {
            //throw new IllegalStateException("Cannot create ColumnField object! Provided field, " + getName() + "is not annotated with @Column");
        }
        this.field = field;

    }

//    public String getName() {
//        return field.getName();
//    }

    public Class<?> getType() {
        return field.getType();
    }

    public String getKeyName() {
        return field.getAnnotation(PrimaryKey.class).name();
    }

}
