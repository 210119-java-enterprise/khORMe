package com.revature;


import com.revature.model.QueryBuilder;
import com.revature.util.DbManager;
import com.revature.services.ConnectionManager;
import com.revature.util.*;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.services.BasicConnectionPool;

/**
 *            khORMe
 *    -code for the code god-
 */
public class Driver {
    public static void main(String[] args) {
        Initializer init =new Initializer();
        init.initialize("src/main/resources/khORM.xml");

        try {
            System.out.println("Driver "+ConnectionManager.getInstance().getConnection().isValid(1));
        }catch (SQLException e){}

        QueryBuilder queryBuilder= new QueryBuilder();
        queryBuilder.SelectStar("boards");
        //init.getConnection().isValid(1);
        //ConnectionPool cp=BasicConnectionPool;
        //Connection conn= BasicConnectionPool.

    }










}

