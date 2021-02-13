package com.revature.services;

import com.revature.forum.models.AppUser;
import com.revature.forum.models.UserRole;
import com.revature.util.DbManager;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    ConnectionPool basicConnectionPool;


    private ConnectionManager() {super();}

    /** eager singleton */
    private static ConnectionManager cm = new ConnectionManager();
    public static ConnectionManager getInstance() {
        return cm;
    }






    public Connection getConnection(){
            return basicConnectionPool.getConnection();
    }

    public void setConnection(String url, String user, String pass){
        try {
            basicConnectionPool= BasicConnectionPool.create(url, user, pass);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


}
