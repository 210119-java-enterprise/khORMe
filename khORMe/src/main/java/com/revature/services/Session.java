package com.revature.services;

import com.revature.forum.models.AppUser;
import com.revature.forum.models.UserRole;

import java.sql.Connection;

/**
 * hold references to a particular user and their connection in order to maintain
 * a connection rather than instantiating new ones where possible
 */
public class Session {

    private AppUser sessionUser;
    private Connection connection;

    /**
     * creates a new session for a user
     * @param sessionUser user starting a new session
     * @param connection connection implementation
     */
    public Session(AppUser sessionUser, Connection connection) {

        if (sessionUser == null || connection == null) {
            throw new ExceptionInInitializerError("Cannot establish user session, null values provided!");
        }

        this.sessionUser = sessionUser;
        this.connection = connection;

    }

    public AppUser getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(AppUser sessionUser) {
        this.sessionUser = sessionUser;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean isAdmin() {
        return (sessionUser.getUserRole().equals(UserRole.ADMIN));
    }

}
