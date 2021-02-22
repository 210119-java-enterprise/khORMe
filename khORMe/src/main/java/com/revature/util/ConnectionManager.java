package com.revature.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * the outward facing class used to access and connections
 */
public class ConnectionManager {
    private ConnectionPoolInterface basicConnectionPool;
    private Connection conn;
    Logger logger = LogManager.getLogger(ConnectionManager.class);
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
        logger.info("Getting Connection from Pool");
        try {
            logger.info("Connection Acquired");
            return conn= basicConnectionPool.getConnection();
        }catch(SQLException e){
            e.printStackTrace();
        }
        logger.error("Failed to Connect");
        return null;
    }


    /**
     * returns a connection from a user to the pool of available connections
     */
    public void releaseConnection(){
        logger.info("Releasing Connection");
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
            logger.info("Creating Connection Pool");
            basicConnectionPool= ConnectionPool.create(url, user, pass);
        }catch(SQLException e){
            logger.error("Failed to Create ConnectionPool");
            e.printStackTrace();
        }
    }


}
