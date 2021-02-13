package com.revature.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateObject {
    DbManager db=DbManager.getInstance();














    public boolean mapResultsToObject(Object obj, ResultSet rs, ColumnField[] fields) throws SQLException, NoSuchFieldException, IllegalAccessException {
        int size=0;
        while(rs.next()) {

            size++;
            System.out.println(size);
            for (ColumnField field : fields) {
                Field f;
                switch(field.getType().toString()){
                    case "int":
                        f = obj.getClass().getDeclaredField(field.getName());
                        f.setInt(obj, rs.getInt(field.getColumnName()));
                        break;
                    case "class java.lang.String":
                        f = obj.getClass().getDeclaredField(field.getName());
                        f.set(obj, rs.getString(field.getColumnName()));
                        break;
                    case "boolean":
                        f = obj.getClass().getDeclaredField(field.getName());
                        f.setBoolean(obj, rs.getBoolean(field.getColumnName()));
                        break;
                    default:
                        System.out.println("ERROR DEFAULT -- field.getType()");
                        break;
                }
            }
            System.out.print("\n");

        }
        if(size<1){
            System.out.println("size 0 return null");

            return false;
        }
        return true;
    }
}
