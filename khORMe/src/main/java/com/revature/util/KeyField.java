package com.revature.util;

import java.lang.reflect.Field;

public class KeyField {

    private Field field;

    public KeyField(Field field) {
        this.field = field;
    }

    public String getName() {
        return field.getName();
    }

    public Class<?> getType() {
        return field.getType();
    }

}
