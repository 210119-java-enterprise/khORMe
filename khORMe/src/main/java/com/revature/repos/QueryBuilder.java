package com.revature.repos;

import com.revature.services.ConnectionManager;
import com.revature.util.ColumnField;
import com.revature.util.CreateObject;
import com.revature.util.DbManager;
import com.revature.util.Metamodel;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class QueryBuilder implements CrudQueryBuilder {
ConnectionManager connectionManager=ConnectionManager.getInstance();
DbManager db=DbManager.getInstance();

    public void selectStar(String tableName) {
        try(Connection conn = connectionManager.getConnection()){
            Metamodel<Class<?>> table=db.get(tableName);
            String sql = "select*from "+table.getTable().getTableName()+";";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            ColumnField[] fields = table.getColumns().toArray(new ColumnField[0]);
            mapResults(rs, fields);
            //System.out.println("results");

        } catch (SQLException e) { e.printStackTrace(); }
        connectionManager.releaseConnection();
    }


    public ArrayList<Object> selectStarObject(Object tableObj) {
        try(Connection conn = connectionManager.getConnection()){
            String className=tableObj.getClass().getName();
            Class cls=Class.forName(className);
            Metamodel<Class<?>> table=db.getByClassName(tableObj.getClass().getSimpleName());
            //Object instance=cls.newInstance();
            ArrayList<Object> instances=new ArrayList<>();

            String sql = "select*from "+table.getTable().getTableName()+";";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            ColumnField[] fields = table.getColumns().stream().toArray(ColumnField[]::new);
            if(new CreateObject().mapResultsToObjectArray(instances ,cls, rs, fields)){
                return instances;
            }
            return null;
        } catch (SQLException | IllegalAccessException | NoSuchFieldException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<Object> selectStarObjectWhereInt(Object tableObj,String col , int value) {
        try(Connection conn = connectionManager.getConnection()){
            String className=tableObj.getClass().getName();
            Class cls=Class.forName(className);
            Metamodel<Class<?>> table=db.getByClassName(tableObj.getClass().getSimpleName());
            //Object instance=cls.newInstance();
            ArrayList<Object> instances=new ArrayList<>();

            String sql = "select*from "+table.getTable().getTableName()+" where "+col+" = "+value+";";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            ColumnField[] fields = table.getColumns().stream().toArray(ColumnField[]::new);
            if(new CreateObject().mapResultsToObjectArray(instances ,cls, rs, fields)){
                return instances;
            }
            return null;
        } catch (SQLException | IllegalAccessException | NoSuchFieldException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Object findObjectMatch(Object tableObj, String username, String pw) {
        try(Connection conn = connectionManager.getConnection()){
            String className=tableObj.getClass().getName();
            Class cls=Class.forName(className);
            Metamodel<Class<?>> table=db.getByClassName(tableObj.getClass().getSimpleName());
            Object instance=cls.newInstance();

            String sql = "select*from "+table.getTable().getTableName()+" where username = '"+username+"' and pw = '"+pw+"';";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            ColumnField[] fields = table.getColumns().stream().toArray(ColumnField[]::new);
            if(new CreateObject().mapResultsToObject(instance, rs, fields)){
                return instance;
            }
            return null;
        } catch (SQLException | IllegalAccessException | NoSuchFieldException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }



    public Object findMatch(String tableName, String username, String pw) {
        try(Connection conn = connectionManager.getConnection()){
            Metamodel<Class<?>> table=db.get(tableName);
            String sql = "select*from "+table.getTable().getTableName()+" where username = '"+username+"' and pw = '"+pw+"';";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            ColumnField[] fields = table.getColumns().stream().toArray(ColumnField[]::new);
            mapResults(rs, fields);

        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }


    public boolean findMatchBool(String tableName, String username, String pw) {
        try(Connection conn = connectionManager.getConnection()){
            Metamodel<Class<?>> table=db.get(tableName);
            String sql = "select*from "+table.getTable().getTableName()+" where username = '"+username+"' and pw = '"+pw+"';";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int count=0;
            while(rs.next()){
                count++;
            }
            if(count>0){
                return true;
            }

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }



    private void mapResults(ResultSet rs,ColumnField[] fields) throws SQLException{
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








    @Override
    public void save(Object newObj) {
        try(Connection conn = connectionManager.getConnection()){
            String className=newObj.getClass().getName();
            Class cls=Class.forName(className);
            Metamodel<Class<?>> table=db.getByClassName(newObj.getClass().getSimpleName());

            StringBuilder sql = new StringBuilder("insert into " + table.getTable().getTableName() + " (");
            for (ColumnField field: table.getColumns()) {
                sql.append(field.getColumnName()).append(",");
            }
            sql.deleteCharAt(sql.length()-1);
            sql.append(") \n values(");


            for (Field field: cls.getClass().getDeclaredFields()  ) {
                switch(field.getType().toString()){
                    case "int":
                        sql.append(field.getName()).append(",");
                        break;
                    case "class java.lang.String":
                        sql.append("'").append(field.getName()).append("',");
                        break;
                    case "boolean":
                        sql.append(field.getName()).append(",");
                        break;
                    default:
                        System.out.println("ERROR DEFAULT -- field.getType()");
                        break;
                }

            }
            sql.deleteCharAt(sql.length()-1);
            sql.append(");");
            System.out.println(sql);
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.execute(sql.toString());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

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
