package com.revature.repos;

import com.revature.annotations.Column;
import com.revature.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;


/**
 * Class containing all the SQL commands
 */
public class QueryBuilder implements CrudQueryBuilder {
ConnectionManager connectionManager=ConnectionManager.getInstance();
DbManager db=DbManager.getInstance();
Logger logger = LogManager.getLogger(QueryBuilder.class);
    /**
     * For testing purposes only --
     * @param obj
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void test(Object obj) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        Metamodel<Class<?>> table=db.getByClassName(obj.getClass().getSimpleName());

        String className=obj.getClass().getName();
        Class cls=Class.forName(className);
        Object instance=cls.newInstance();

        /** both return the value of the variable*/
        System.out.println(instance.getClass().getDeclaredField("id").getInt(obj));
        System.out.println(cls.getDeclaredField("id").getInt(obj));


    }

//---------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------- Selects
// ---------------------------------------------------------------------------------------------------------

    /**
     * Queries and Prints all records in a table to the console
     * @param tableName the name of the table to be queried
     */
    public void selectStar(String tableName) {
        logger.info("Attempting Query");
        try(Connection conn = connectionManager.getConnection()){
            Metamodel<Class<?>> table=db.get(tableName);
            String sql = "select*from "+table.getTable().getTableName()+";";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            ColumnField[] fields = table.getColumns().toArray(new ColumnField[0]);
            ResultSetMapper.printResults(rs, fields);
;

        } catch (SQLException e) {
            logger.error("Attempt Failed");
            e.printStackTrace();
        }
        connectionManager.releaseConnection();
    }

    /**
     * Queries the database for the table identified by the object
     * and returns all records in the table to the calling program
     * @param tableObj the object representing the table
     * @return all records in object format
     */
    public ArrayList<Object> selectStarObject(Object tableObj) {
        logger.info("Attempting Query");
        try(Connection conn = connectionManager.getConnection()){
            String className=tableObj.getClass().getName();
            Class cls=Class.forName(className);
            Metamodel<Class<?>> table=db.getByClassName(tableObj.getClass().getSimpleName());
            ArrayList<Object> instances=new ArrayList<>();

            String sql = "select*from "+table.getTable().getTableName()+";";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            ColumnField[] fields = table.getColumns().stream().toArray(ColumnField[]::new);
            if(new ResultSetMapper().mapResultsToObjectArray(instances ,cls, rs, fields)){
                connectionManager.releaseConnection();
                return instances;
            }
            connectionManager.releaseConnection();
            return null;
        } catch (SQLException | IllegalAccessException | NoSuchFieldException | InstantiationException | ClassNotFoundException e) {
            logger.error("Attempt Failed");
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  Selects everything from a record, or multiple records
     *  where the column value is equal to the integer. Returs
     * @param tableObj the type of object to output
     * @param col the column to search
     * @param value the integer value to find
     * @return an Arraylist of objects containing the data of the records
     */
    public ArrayList<Object> selectStarObjectWhereInt(Object tableObj,String col , int value) {
        logger.info("Attempting Query");
        try(Connection conn = connectionManager.getConnection()){
            String className=tableObj.getClass().getName();
            Class cls=Class.forName(className);
            Metamodel<Class<?>> table=db.getByClassName(tableObj.getClass().getSimpleName());
            ArrayList<Object> instances=new ArrayList<>();

            String sql = "select*from "+table.getTable().getTableName()+" where "+col+" = "+value+";";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            ColumnField[] fields = table.getColumns().stream().toArray(ColumnField[]::new);
            if(new ResultSetMapper().mapResultsToObjectArray(instances ,cls, rs, fields)){
                connectionManager.releaseConnection();
                return instances;
            }
            connectionManager.releaseConnection();
            return null;
        } catch (SQLException | IllegalAccessException | NoSuchFieldException | InstantiationException | ClassNotFoundException e) {
            logger.error("Attempt Failed");
            e.printStackTrace();
        }
        return null;
    }


    /**
     *
     * @param tableObj
     * @param username
     * @param pw
     * @return
     */
    public Object findObjectMatch(Object tableObj, String username, String pw) {
        logger.info("Attempting Query");
        try(Connection conn = connectionManager.getConnection()){
            String className=tableObj.getClass().getName();
            Class cls=Class.forName(className);
            Metamodel<Class<?>> table=db.getByClassName(tableObj.getClass().getSimpleName());
            Object instance=cls.newInstance();

            String sql = "select*from "+table.getTable().getTableName()+" where username = '"+username+"' and password = '"+pw+"';";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            ColumnField[] fields = table.getColumns().stream().toArray(ColumnField[]::new);
            if(new ResultSetMapper().mapResultsToObject(instance, rs, fields)){
                connectionManager.releaseConnection();
                return instance;
            }
            connectionManager.releaseConnection();
            return null;
        } catch (SQLException | IllegalAccessException | NoSuchFieldException | InstantiationException | ClassNotFoundException e) {
            logger.error("Attempt Failed");
            e.printStackTrace();
        }
        connectionManager.releaseConnection();
        return null;
    }


    /**
     * find record by username and password
     *  --columns must be labeled username and password in database
     * @param tableName the table to be searched
     * @param username the user name to be searched for
     * @param pw the correlated password
     * @return the record in the form of an object if found, otherwise returns null
     */
    public void findMatch(String tableName, String username, String pw) {
        logger.info("Attempting Query");
        try(Connection conn = connectionManager.getConnection()){
            Metamodel<Class<?>> table=db.get(tableName);
            String sql = "select*from "+table.getTable().getTableName()+" where username = '"+username+"' and password = '"+pw+"';";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            ColumnField[] fields = table.getColumns().stream().toArray(ColumnField[]::new);
            ResultSetMapper.printResults(rs, fields);

        } catch (SQLException e) {
            logger.error("Attempt Failed");
            e.printStackTrace();
        }
        connectionManager.releaseConnection();
        //return null;
    }



    /**
     * Searches for a record based on one criteria
     * @param tableName Table to search
     * @param columnName  Column to search
     * @param value the value to be searched for
     * @return true if it exists, false otherwise
     */
    public boolean findMatchBool(String tableName, String columnName, String value) {
        logger.info("Attempting Query");
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

        } catch (SQLException e) {
            logger.error("Attempt Failed");
            e.printStackTrace();
        }
        connectionManager.releaseConnection();
        return false;
    }




//---------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------- Inserts
// ---------------------------------------------------------------------------------------------------------

    /**
     * parses an object and Saves its data to the database
     * @param newObj the object to save
     */
    @Override
    public void save(Object newObj) {
        logger.info("Attempting Query");
        try(Connection conn = connectionManager.getConnection()){
            List<Object> args= new LinkedList<>();
            List<String> datatype= new LinkedList<>();
            Metamodel<Class<?>> table=db.getByClassName(newObj.getClass().getSimpleName());
            StringBuilder sql1 = new StringBuilder("insert into " + table.getTable().getTableName() + " (");


            StringBuilder sql2 = new StringBuilder(") \n values(");

            /** Build sql string */
            for (Field field: newObj.getClass().getDeclaredFields()  ) {
                //System.out.println(field.getName());
                if(field.getDeclaredAnnotations().length>0) {
                    switch (field.getType().toString()) {
                        case "int":

                            if (field.getName().equals("id")) {
                                break;
                            }
                            //System.out.println("int: " + field.getAnnotation(Column.class).name() + " == " + field.get(newObj));
                            sql1.append(field.getAnnotation(Column.class).name()).append(",");
                            args.add(field.get(newObj));
                            datatype.add(field.getType().toString());
                            sql2.append("?,");
                            break;
                        case "class java.lang.String":
                            //System.out.println("str: " + field.getAnnotation(Column.class).name() + " == " + field.get(newObj));
                            sql1.append(field.getAnnotation(Column.class).name()).append(",");
                            args.add(field.get(newObj));
                            datatype.add(field.getType().toString());
                            sql2.append("?,");
                            break;
                        case "boolean":
                            //System.out.println("boo: " + field.getAnnotation(Column.class).name() + " == " + field.get(newObj));
                            sql1.append(field.getAnnotation(Column.class).name()).append(",");
                            args.add(field.get(newObj));
                            datatype.add(field.getType().toString());
                            sql2.append("?,");
                            break;
                        case "class java.util.Date":
                            //System.out.println("str: " + field.getAnnotation(Column.class).name() + " == " + field.get(newObj));
                            sql1.append(field.getAnnotation(Column.class).name()).append(",");
                            args.add(field.get(newObj));
                            datatype.add(field.getType().toString());
                            sql2.append("?,");
                            break;
                        default:
                            System.out.println("ERROR DEFAULT -- field.getType() ==" + field.getType());
                            break;
                    }
                }

            }
            sql1.deleteCharAt(sql1.length()-1);
            sql2.deleteCharAt(sql2.length()-1);
            sql2.append(");");
            sql1.append(sql2);
            PreparedStatement pstmt = conn.prepareStatement(sql1.toString());
            int i=1;

            /**  Set values for prepared statement  */
            for (Object arg: args ) {
                switch(datatype.get(i-1)){
                    case "int":
                        if(arg == null){
                            pstmt.setInt(i,-1);
                            break;
                        }
                        pstmt.setInt(i, Integer.parseInt(arg.toString()));
                        break;
                    case "class java.lang.String":
                        if(arg == null){
                            pstmt.setString(i,"");
                            break;
                        }
                        pstmt.setString(i, arg.toString());
                        break;
                    case "boolean":
                        if(arg == null){
                            pstmt.setBoolean(i,false);
                            break;
                        }
                        pstmt.setBoolean(i, Boolean.getBoolean(arg.toString()));
                        break;
                    case "class java.util.Date":
                        pstmt.setTimestamp(i, new Timestamp(Calendar.getInstance().getTime().getTime()));
                        break;
                    default:
                        System.out.println("ERROR DEFAULT -- field.getType() =="+datatype.get(i-1));
                        break;
                }
                i++;
            }
            pstmt.execute();
        } catch (SQLException | IllegalAccessException e) {
            logger.error("Attempt Failed");
            e.printStackTrace();
        }
        connectionManager.releaseConnection();

    }




    @Override
    public LinkedList findAll() {
        return null;
    }

    @Override
    public Object findById(int id) {
        return null;
    }



//---------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------- Updates
// ---------------------------------------------------------------------------------------------------------
    /**
     *
     * @param obj
     * @param col
     * @param value
     * @return
     */
    public boolean updateById(Object obj, String col, String value) {//TODO pstmt
        logger.info("Building Query");
            String sql="";
            Metamodel<Class<?>> table=db.getByClassName(obj.getClass().getSimpleName());
            String className=obj.getClass().getName();
            try {
                Class cls=Class.forName(className);
                System.out.println("1");
                sql = "update "+table.getTable().getTableName()+"\n" +
                        "set "+col+" = '"+value+"'\n" +
                        "where id = "+cls.getDeclaredField("id").getInt(obj)+";";
            } catch (IllegalAccessException | NoSuchFieldException | ClassNotFoundException e) {
                logger.error("Build Failed");
                e.printStackTrace();
            }


        try(Connection conn = connectionManager.getConnection()){
            logger.info("Attempting Query");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.execute();
            connectionManager.releaseConnection();
            return true;

        } catch (SQLException e) {
            logger.error("Attempt Failed");
            e.printStackTrace();
        }
        connectionManager.releaseConnection();
        return false;
    }





//    public boolean updateById(Object obj, String col, String value) {
//        try(Connection conn = connectionManager.getConnection()){
//            Metamodel<Class<?>> table=db.getByClassName(obj.getClass().getSimpleName());
//            String className=obj.getClass().getName();
//            Class cls=Class.forName(className);
//
//            System.out.println("1");
//            String sql = "update "+table.getTable().getTableName()+"\n" +
//                    "set "+col+" = '"+value+"'\n" +
//                    "where id = "+cls.getDeclaredField("id").getInt(obj)+";";
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            System.out.println("2");
//            pstmt.execute();
//            System.out.println("3");
//            connectionManager.releaseConnection();
//            return true;
//
//        } catch (SQLException | IllegalAccessException | NoSuchFieldException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        connectionManager.releaseConnection();
//        return false;
//    }


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
//---------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------- Deletes
// ---------------------------------------------------------------------------------------------------------
    /**
     *
     * @param deleteObj
     */
    public void delete (Object deleteObj) {//TODO delete cascade
        logger.info("Building SQL DELETE Statement");
        String sql="";
        int id=-1;
        try {
            String temp=deleteObj.getClass().getDeclaredField("id").get(deleteObj).toString();
            id=Integer.parseInt(temp);
            Metamodel<Class<?>> table=db.getByClassName(deleteObj.getClass().getSimpleName());
            table.getTable().getTableName();
            sql= "delete from "+table.getTable().getTableName()+" where id = ?;";
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error("Statement Build failed");
            e.printStackTrace();
        }
        logger.info("Attempting Statement Execution");
        try(Connection conn = connectionManager.getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.error("Attempt Failed");
            e.printStackTrace();
        }

    }









    @Override
    public boolean deleteById(int id) {

        return false;
    }
}
