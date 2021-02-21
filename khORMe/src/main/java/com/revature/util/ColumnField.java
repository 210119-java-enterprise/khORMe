package com.revature.util;

import com.revature.annotations.Column;
import com.revature.annotations.PrimaryKey;

import java.lang.reflect.Field;

public class ColumnField {

    private Field field;

    public ColumnField(Field field) {
        if (field.getAnnotation(Column.class) == null) {
            throw new IllegalStateException("Cannot create ColumnField object! Provided field, " + getName() + "is not annotated with @Column");
        }
        this.field = field;
    }
    /** returns the name of the variable */
    public String getName() {
        return field.getName();
    }

    /** returns the datatype of the variable */
    public Class<?> getType() {
        return field.getType();
    }
    /** returns the column name */
    public String getColumnName() {
        return field.getAnnotation(Column.class).name();
    }
    /** returns the key status of the column -- pk, fk, or 0 (none) */
    public String getKey() {
        return field.getAnnotation(Column.class).key();
    }
}
