package com.revature.repos;

import com.revature.annotations.Column;
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
import java.util.List;

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
            List<Object> args= new LinkedList<>();
            List<String> datatype= new LinkedList<>();
            StringBuilder sql1 = new StringBuilder("insert into " + table.getTable().getTableName() + " (");
//            for (ColumnField field: table.getColumns()) {
//                sql1.append(field.getColumnName()).append(",");
//            }

            StringBuilder sql2 = new StringBuilder(") \n values(");


            for (Field field: newObj.getClass().getDeclaredFields()  ) {
                System.out.println(field.getName());
                switch(field.getType().toString()){
                    case "int":

                        if(field.getName().equals("id")){break;}
                        System.out.println("int: "+field.getAnnotation(Column.class).name()+" == "+field.get(newObj));
                        sql1.append(field.getAnnotation(Column.class).name()).append(",");
                        args.add(field.get(newObj));
                        datatype.add(field.getType().toString());
                        sql2.append("?,");
                        break;
                    case "class java.lang.String":
                        if(field.getName().equals("registrationDate")){break;}
                        System.out.println("str: "+field.getAnnotation(Column.class).name()+" == "+field.get(newObj));
                        sql1.append(field.getAnnotation(Column.class).name()).append(",");
                        args.add(field.get(newObj));
                        datatype.add(field.getType().toString());
                        sql2.append("?,");
                        break;
                    case "boolean":
                        System.out.println("boo: "+field.getAnnotation(Column.class).name()+" == "+field.get(newObj));
                        sql1.append(field.getAnnotation(Column.class).name()).append(",");
                        args.add(field.get(newObj));
                        datatype.add(field.getType().toString());
                        sql2.append("?,");
                        break;
                    default:
                        System.out.println("ERROR DEFAULT -- field.getType() =="+field.getType());
                        break;
                }

            }
            sql1.deleteCharAt(sql1.length()-1);
            sql2.deleteCharAt(sql2.length()-1);
            sql2.append(");");
            sql1.append(sql2);
            System.out.println(sql1);
            //String sql=sql1.toString();
            System.out.println("3");
            PreparedStatement pstmt = conn.prepareStatement(sql1.toString());
            System.out.println("4");
            int i=1;
            for (Object arg: args ) {
                switch(datatype.get(i-1)){
                    case "int":
                        if(arg == null){
                            System.out.println("null int");
                            pstmt.setInt(i,-1);
                            break;
                        }
                        System.out.println("int"+arg.toString());
                        pstmt.setInt(i, Integer.parseInt(arg.toString()));
                        break;
                    case "class java.lang.String":
                        if(arg == null){
                            System.out.println("null str");
                            pstmt.setString(i,"");
                            break;
                        }
                        System.out.println("str"+arg.toString());
                        pstmt.setString(i, arg.toString());
                        break;
                    case "boolean":
                        if(arg == null){
                            System.out.println("null bool");
                            pstmt.setBoolean(i,false);
                            break;
                        }
                        System.out.println("bool"+arg);
                        pstmt.setBoolean(i, Boolean.getBoolean(arg.toString()));
                        break;
                    default:
                        System.out.println("ERROR DEFAULT -- field.getType() ==");
                        break;
                }
                i++;
            }
            System.out.println("1) count "+ i);
            pstmt.execute();
            System.out.println("2");
        } catch (SQLException | ClassNotFoundException | IllegalAccessException e) {
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
