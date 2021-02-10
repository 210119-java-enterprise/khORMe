package com.revature.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ClassInspector {

    public void inspectClass(Class<?> clazz) {
        listPublicFields(clazz);
        listNonPublicFields(clazz);
    }

    public void listPublicFields(Class<?> clazz) {
        System.out.println("Printing the public fields of the " + clazz.getName());
        Field[] fields = clazz.getFields();
        if (fields.length == 0) {
            System.out.println("\tThere are no public fields in the class " + clazz.getName() + "\n");
        }
        for (Field field : fields) {
            System.out.println("\tField name: " + field.getName());
            System.out.println("\tField type: " + field.getType());
            System.out.println("\tIs field primitive? :: " + field.getType().isPrimitive());
            System.out.println("\tModifiers bit value: " + Integer.toBinaryString(field.getModifiers())  + "\n");
        }
    }

    public void listNonPublicFields(Class<?> clazz) {
        System.out.println("Printing the non-public fields of the " + clazz.getName());
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length == 0) {
            System.out.println("\tThere are no non-public fields in the class " + clazz.getName());
        }
        for (Field field : fields) {
            if ((field.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC) {
                continue;
            }

            System.out.println("\tField name: " + field.getName());
            System.out.println("\tField type: " + field.getType());
            System.out.println("\tIs field primitive? :: " + field.getType().isPrimitive());
            System.out.println("\tModifiers bit value: " + Integer.toBinaryString(field.getModifiers())  + "\n");

        }
    }











}
