package com.revature.services;

import com.revature.util.DbManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Class containing pool initialization, the list of connections and methods for utilizing them
 */
public class BasicConnectionPool implements ConnectionPool {

    /** url to database */
    private String url;
    /** username for database */
    private String user;
    /** password for database */
    private String password;
    /** list of connections available */
    private List<Connection> connectionPool;
    /** list of conntions currently in use */
    private List<Connection> usedConnections = new ArrayList<>();
    /** starting size of the connection pool */
    private static int INITIAL_POOL_SIZE = 10;
    /** max size pool can increase to */
    private static final int MAX_POOL_SIZE = 20;
    /** time allotted to wait for response when validating connection */
    private static final int MAX_TIMEOUT = 5;



    /**
     * Constructor for the connection pool. Called by the create method.
     * @param url url to database
     * @param user username for database
     * @param password password for database
     * @param pool
     */
    private BasicConnectionPool(String url, String user, String password, List<Connection> pool){
        this.url=url;
        this.user=user;
        this.password=password;
        this.connectionPool=pool;
    }


    /**
     * Creation of the pool -- Create a list of connections to the database
     * @param url url to database
     * @param user username for database
     * @param password password for database
     * @return The pool of connections just created
     * @throws SQLException
     */
    public static BasicConnectionPool create(String url, String user, String password) throws SQLException {
        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection(url, user, password));
        }
        return new BasicConnectionPool(url, user, password, pool);
    }


    /**
     *
     * @param url
     * @param user
     * @param password
     * @return
     * @throws SQLException
     */
    private static Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }






    /**
     * gives a connection to the requester If the connection pool has available connections
     * allows for pool resizing up to a max limit
     * @return a connection from the pool
     * @throws SQLException
     */
    @Override
    public Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                connectionPool.add(createConnection(url, user, password));
            } else {
                throw new RuntimeException("Maximum pool size reached, no available connections!");
            }
        }
        //System.out.println("size: "+getSize()+" - "+connectionPool.size()+" - "+usedConnections.size());
        Connection connection = connectionPool.remove(connectionPool.size() - 1);

        if(!connection.isValid(MAX_TIMEOUT)){
            connection = createConnection(url, user, password);
        }
        //System.out.println("size: "+getSize()+" - "+connectionPool.size()+" - "+usedConnections.size());
        usedConnections.add(connection);
        //System.out.println("size: "+getSize()+" - "+connectionPool.size()+" - "+usedConnections.size());
        return connection;
    }


    /**
     * returns connection back to the pool of available connection
     * and removes it from the active connections list
     * @param connection the connection to release
     * @return true if the connection was successfully removed, false if otherwise
     */
    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        //System.out.println("returned to connection pool  POOLSIZE="+connectionPool.size());
        return usedConnections.remove(connection);
    }

    /**
     * sends all connections to the connection pool, and closes them
     * @throws SQLException
     */
    @Override
    public void shutdown() throws SQLException {
        usedConnections.forEach(this::releaseConnection);
        for (Connection c : connectionPool) {
            c.close();
        }
        connectionPool.clear();
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Connection> getConnectionPool() {
        return connectionPool;
    }

    public void setConnectionPool(List<Connection> connectionPool) {
        this.connectionPool = connectionPool;
    }

    public List<Connection> getUsedConnections() {
        return usedConnections;
    }

    public void setUsedConnections(List<Connection> usedConnections) {
        this.usedConnections = usedConnections;
    }



}
