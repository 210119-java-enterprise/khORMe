package com.revature.util;

import com.revature.annotations.Column;

import java.lang.reflect.Field;

/**
 * this class contains all applicable data pertaining
 * to an objects fields in relation to a database record
 */
public class ColumnField {

    private Field field;

    /**
     * constructor -- takes a field and checks if it contains the annotation data to
     * signify a place in a database
     * @param field
     */
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
    public String getKey() {return field.getAnnotation(Column.class).key();}
    /** returns true if column is primary key */
    public boolean isPk(){return field.getAnnotation(Column.class).key()=="pk";}
    /** returns true if column is foreign key */
    public boolean isFk(){return field.getAnnotation(Column.class).key()=="fk";}
}
