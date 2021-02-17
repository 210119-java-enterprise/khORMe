package com.revature.repos;

import com.revature.annotations.Column;
import com.revature.services.ConnectionManager;
import com.revature.util.ColumnField;
import com.revature.util.CreateObject;
import com.revature.util.DbManager;
import com.revature.util.Metamodel;

import java.lang.reflect.Field;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

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
            printResults(rs, fields);
;

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
                connectionManager.releaseConnection();
                return instances;
            }
            connectionManager.releaseConnection();
            return null;
        } catch (SQLException | IllegalAccessException | NoSuchFieldException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        connectionManager.releaseConnection();
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
                connectionManager.releaseConnection();
                return instances;
            }
            connectionManager.releaseConnection();
            return null;
        } catch (SQLException | IllegalAccessException | NoSuchFieldException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        connectionManager.releaseConnection();
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
                connectionManager.releaseConnection();
                return instance;
            }
            connectionManager.releaseConnection();
            return null;
        } catch (SQLException | IllegalAccessException | NoSuchFieldException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        connectionManager.releaseConnection();
        return null;
    }


    /**
     * find record by username and password
     * @param tableName the table to be searched
     * @param username the user name to be seached for
     * @param pw the correlated password
     * @return the record in the form of an object if found, otherwise returns null
     */
    public void findMatch(String tableName, String username, String pw) {
        try(Connection conn = connectionManager.getConnection()){
            Metamodel<Class<?>> table=db.get(tableName);
            String sql = "select*from "+table.getTable().getTableName()+" where username = '"+username+"' and pw = '"+pw+"';";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            ColumnField[] fields = table.getColumns().stream().toArray(ColumnField[]::new);
            printResults(rs, fields);

        } catch (SQLException e) { e.printStackTrace(); }
        connectionManager.releaseConnection();
        //return null;
    }




    private void printResults(ResultSet rs,ColumnField[] fields) throws SQLException{
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




//    public boolean findMatchBool(String tableName, String username, String pw) {
//        try(Connection conn = connectionManager.getConnection()){
//            Metamodel<Class<?>> table=db.get(tableName);
//            String sql = "select*from "+table.getTable().getTableName()+" where username = '"+username+"' and pw = '"+pw+"';";
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            ResultSet rs = pstmt.executeQuery();
//            int count=0;
//            while(rs.next()){
//                count++;
//            }
//            if(count>0){
//                return true;
//            }
//
//        } catch (SQLException e) { e.printStackTrace(); }
//        return false;
//    }


    /**
     * Seaches for a record based on one criteria
     * @param tableName Table to search
     * @param columnName  Column to search
     * @param value the value to be searched for
     * @return
     */
    public boolean findMatchBool(String tableName, String columnName, String value) {
        try(Connection conn = connectionManager.getConnection()){
            Metamodel<Class<?>> table=db.get(tableName);
            String sql = "select*from "+table.getTable().getTableName()+" where "+columnName+" = '"+value+"';";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int count=0;
            while(rs.next()){
                count++;
            }
            if(count>0){
                connectionManager.releaseConnection();
                return true;
            }

        } catch (SQLException e) { e.printStackTrace(); }
        connectionManager.releaseConnection();
        return false;
    }






    /**
     * parses an object and Saves an object to the database
     * @param newObj the object to save
     */
    @Override
    public void save(Object newObj) {
        try(Connection conn = connectionManager.getConnection()){
            List<Object> args= new LinkedList<>();
            List<String> datatype= new LinkedList<>();
            Metamodel<Class<?>> table=db.getByClassName(newObj.getClass().getSimpleName());
            StringBuilder sql1 = new StringBuilder("insert into " + table.getTable().getTableName() + " (");


            StringBuilder sql2 = new StringBuilder(") \n values(");

            /** Build sql string */
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
                    case "class java.util.Date":
                        System.out.println("str: "+field.getAnnotation(Column.class).name()+" == "+field.get(newObj));
                        sql1.append(field.getAnnotation(Column.class).name()).append(",");
                        args.add(field.get(newObj));
                        datatype.add(field.getType().toString());
                        sql2.append("?,");
                        System.out.println("dsadasdased");
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
            System.out.println("3");
            PreparedStatement pstmt = conn.prepareStatement(sql1.toString());
            System.out.println("4");
            int i=1;

            /**  Set values for prepared statement  */
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
                    case "class java.util.Date":

                        System.out.println("date"+arg);
                        pstmt.setTimestamp(i, new Timestamp(Calendar.getInstance().getTime().getTime()));
                        break;
                    default:
                        System.out.println("ERROR DEFAULTr -- field.getType() =="+datatype.get(i-1));
                        break;
                }
                i++;
            }
            System.out.println("1) count "+ i);
            pstmt.execute();
            System.out.println("2");
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        connectionManager.releaseConnection();

    }

    public void test(Object obj) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        Metamodel<Class<?>> table=db.getByClassName(obj.getClass().getSimpleName());


        String className=obj.getClass().getName();
        Class cls=Class.forName(className);
        Object instance=cls.newInstance();

        /** both return the velue of the variable*/
        System.out.println(instance.getClass().getDeclaredField("id").getInt(obj));
        System.out.println(cls.getDeclaredField("id").getInt(obj));


    }

    @Override
    public LinkedList findAll() {
        return null;
    }

    @Override
    public Object findById(int id) {
        return null;
    }


    public boolean updateById(Object obj, String col, String value) {
        try(Connection conn = connectionManager.getConnection()){
            Metamodel<Class<?>> table=db.getByClassName(obj.getClass().getSimpleName());
            String className=obj.getClass().getName();
            Class cls=Class.forName(className);

            System.out.println("1");
            String sql = "update "+table.getTable().getTableName()+"\n" +
                    "set "+col+" = '"+value+"'\n" +
                    "where id = "+cls.getDeclaredField("id").getInt(obj)+";";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            System.out.println("2");
            pstmt.execute();
            System.out.println("3");
            connectionManager.releaseConnection();
            return true;

        } catch (SQLException | IllegalAccessException | NoSuchFieldException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        connectionManager.releaseConnection();
        return false;
    }


    @Override
    public boolean update(Object obj) {
//        try(Connection conn = connectionManager.getConnection()){
//            String className=obj.getClass().getName();
//            Class cls=Class.forName(className);
//            Metamodel<Class<?>> table=db.getByClassName(obj.getClass().getSimpleName());
//            //Object instance=cls.newInstance();
//
//            String sql = "update "+table.getTable().getTableName()+"\n" +
//                    "set username = value\n" +
//                    "where id = '"+username+"' and pw = '"+pw+"';";
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.execute();
//            ColumnField[] fields = table.getColumns().stream().toArray(ColumnField[]::new);
//
//        } catch (SQLException | IllegalAccessException | NoSuchFieldException | InstantiationException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        "update users" +
//
//                "where username = old value"
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
