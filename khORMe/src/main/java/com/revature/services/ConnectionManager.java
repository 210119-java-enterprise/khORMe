package com.revature.services;

import com.revature.forum.models.AppUser;
import com.revature.forum.models.UserRole;
import com.revature.util.DbManager;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    private ConnectionPool basicConnectionPool;
    private Connection conn;

    private ConnectionManager() {super();}

    /** eager singleton */
    private static ConnectionManager cm = new ConnectionManager();
    public static ConnectionManager getInstance() {
        return cm;
    }


//    public Connection getConnection(){
//        return conn;
//        //return basicConnectionPool.getConnection();
//    }



    public Connection getConnection(){
        try {
            return conn= basicConnectionPool.getConnection();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }



    public void releaseConnection(){

        basicConnectionPool.releaseConnection(conn);
    }

    public void setConnection(String url, String user, String pass){
        try {
            basicConnectionPool= BasicConnectionPool.create(url, user, pass);
            //conn=getConnectionFromPool();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


}
