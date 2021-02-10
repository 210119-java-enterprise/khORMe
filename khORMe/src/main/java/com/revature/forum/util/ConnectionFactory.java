package com.revature.forum.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * makes the connection to the database
 */
public class ConnectionFactory {

    /** eager singleton */
    private static ConnectionFactory connFactory = new ConnectionFactory();

    /** database access details */
    private Properties props = new Properties();

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor
     * loads location and access details for the database
     */
    private ConnectionFactory() {
        try {
            props.load(new FileReader("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    public static ConnectionFactory getInstance() {
        return connFactory;
    }

    /**
     * creates new connection to the database
     * @return new connection
     */
    public Connection getConnection() {

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(
                    props.getProperty("url"),
                    props.getProperty("admin-usr"),
                    props.getProperty("admin-pw")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

}
