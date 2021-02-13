package com.revature.model;

import com.revature.services.ConnectionManager;
import com.revature.util.ColumnField;
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


        //try(Connection conn = ConnectionFactory.getInstance().getConnection()){
        try(Connection conn = connectionManager.getConnection()){
            Metamodel<Class<?>> table=db.get(tableName);
            String sql = "select*from "+table.getTable().getTableName()+";";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            ColumnField[] fields = table.getColumns().stream().toArray(ColumnField[]::new);
            mapResults(rs, fields);

        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void mapResults(ResultSet rs,ColumnField[] fields) throws SQLException{
        while(rs.next()) {
            for (ColumnField field : fields) {
                switch(field.getType().toString()){
                    case "int":
                        System.out.print(rs.getInt(field.getName())+" ");
                        break;
                    case "class java.lang.String":
                        System.out.println(rs.getString(field.getName())+" ");
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
