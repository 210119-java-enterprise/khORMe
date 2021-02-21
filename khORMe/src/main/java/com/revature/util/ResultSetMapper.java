package com.revature.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResultSetMapper {
    DbManager db=DbManager.getInstance();


    /**
     * takes the resultset of a query that returns one or more records
     * and maps it onto an arraylists of objects that will be sent back to the calling method
     * @param instances arraylist of the type of object to be returned
     * @param cls
     * @param rs
     * @param fields
     * @return
     * @throws SQLException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public boolean mapResultsToObjectArray(ArrayList<Object> instances, Class cls, ResultSet rs, ColumnField[] fields) throws SQLException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        int size=0;
        while(rs.next()) {
            Object obj=cls.newInstance();
            size++;
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
                    case "class java.util.Date":
                        f = obj.getClass().getDeclaredField(field.getName());
                        f.set(obj, rs.getDate(field.getColumnName()));
                        break;
                    default:
                        System.out.println("ERROR unregistered type--"+field.getType().toString());
                        break;
                }
            }
            //System.out.print("\n");
        instances.add(obj);
        }
        if(size<1){
            System.out.println("No Results Returned");

            return false;
        }
        return true;
    }


    /**
     * Maps the results of a SQL query to an object
     * and from there, returned to the calling program
     * @param obj the object to apply the SQL results to
     * @param rs the object containing the results of the sql query
     * @param fields an array of fields to scan for in the result set
     * @return
     * @throws SQLException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public boolean mapResultsToObject(Object obj, ResultSet rs, ColumnField[] fields) throws SQLException, NoSuchFieldException, IllegalAccessException {
        int size=0;
        while(rs.next()) {

            size++;
            for (ColumnField field : fields) {
                Field f;
                switch(field.getType().toString()){
                    case "int":
                        //System.out.println(obj.getClass().getDeclaredField(field.getName()) +" "+rs.getInt(field.getColumnName()));
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
                    case "class java.util.Date":
                        f = obj.getClass().getDeclaredField(field.getName());
                        f.set(obj, rs.getDate(field.getColumnName()));
                        break;
                    default:
                        System.out.println("ERROR DEFAULT -- field.getType() == "+obj.getClass().getDeclaredField(field.getName()));
                        break;
                }
            }
            System.out.print("\n");

        }
        if(size<1){
            System.out.println("No Results Returned");

            return false;
        }
        return true;
    }


    /**
     * Takes a given result set, and prints everything to the console
     * @param rs the object containing the results of the sql query
     * @param fields an array of fields to scan for in the result set
     * @throws SQLException
     */
    public static void printResults(ResultSet rs, ColumnField[] fields) throws SQLException{
        while(rs.next()) {
            for (ColumnField field : fields) {
                System.out.print(field.getColumnName()+": ");
                switch(field.getType().toString()){
                    case "int":
                        System.out.print(rs.getInt(field.getColumnName())+" ");
                        break;
                    case "class java.lang.String":
                        System.out.println(rs.getString(field.getColumnName())+" ");
                        break;
                    case "boolean":
                        System.out.println(rs.getBoolean(field.getColumnName())+" ");
                        break;
                    default:
                        System.out.println("ERROR DEFAULT -- field.getType()");
                        break;
                }
            }
            System.out.print("\n");

        }
    }



}
