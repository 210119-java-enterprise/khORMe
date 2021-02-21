package com.revature.services;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * the outward facing class used to access and connections
 */
public class ConnectionManager {
    private ConnectionPool basicConnectionPool;
    private Connection conn;

    private ConnectionManager() {super();}

    /** eager singleton */
    private static ConnectionManager cm = new ConnectionManager();
    public static ConnectionManager getInstance() {
        return cm;
    }


    /**
     * Grabs a connection from the connection pool for the user
     * @return an available connection
     */
    public Connection getConnection(){
        try {
            return conn= basicConnectionPool.getConnection();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * returns a connection from a user to the pool of available connections
     */
    public void releaseConnection(){
        basicConnectionPool.releaseConnection(conn);
    }


    /**
     * Initializes the creation of the connection pool. this is done once
     * @param url the location of the database
     * @param user the username to access the database
     * @param pass the password to access the database
     */
    public void setConnection(String url, String user, String pass){
        try {
            basicConnectionPool= BasicConnectionPool.create(url, user, pass);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


}
