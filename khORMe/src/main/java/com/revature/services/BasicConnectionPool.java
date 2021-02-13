package com.revature.services;

import com.revature.util.DbManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BasicConnectionPool implements ConnectionPool {

    private String url;
    private String user;
    private String password;
    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();
    private static int INITIAL_POOL_SIZE = 10;
    private static final int MAX_POOL_SIZE = 20;
    private static final int MAX_TIMEOUT = 5;



    public static BasicConnectionPool create(String url, String user, String password) throws SQLException {
        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection(url, user, password));
        }
        return new BasicConnectionPool(url, user, password, pool);
    }

    private BasicConnectionPool(String url, String user, String password, List<Connection> pool){
        this.url=url;
        this.user=user;
        this.password=password;
        this.connectionPool=pool;
    }


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



    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        //System.out.println("returned to connection pool");
        return usedConnections.remove(connection);

    }

    private static Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
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


    @Override
    public void shutdown() throws SQLException {
        usedConnections.forEach(this::releaseConnection);
        for (Connection c : connectionPool) {
            c.close();
        }
        connectionPool.clear();
    }

}
