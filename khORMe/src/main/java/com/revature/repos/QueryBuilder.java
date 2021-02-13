package com.revature.repos;

import com.revature.services.ConnectionManager;
import com.revature.util.ColumnField;
import com.revature.util.CreateObject;
import com.revature.util.DbManager;
import com.revature.util.Metamodel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class QueryBuilder implements CrudQueryBuilder {
ConnectionManager connectionManager=ConnectionManager.getInstance();
DbManager db=DbManager.getInstance();

    public void SelectStar(String tableName) {
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
    public Object findObjectMatch2(Object tableObj, String username, String pw) {

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
            return null;//instance;
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
