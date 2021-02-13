package com.revature.util;

import com.revature.services.BasicConnectionPool;
import com.revature.services.ConnectionFactory;
import com.revature.services.ConnectionManager;
import com.revature.services.ConnectionPool;
import com.revature.util.DbManager;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Initializer {

    DbManager db = DbManager.getInstance();
    XMLParser xml = new XMLParser();
    ConnectionPool basicConnectionPool;
    ConnectionManager connectionManager=ConnectionManager.getInstance();

    public void initialize(String configPath) {
        logo();
        initialConfig(configPath);
        System.out.println("\n\n-----Initialization Complete-------\n\n");



    }


    public void getConnection(){

    }






    private void initialConfig(String path){

        String[] parsedXML= xml.parse(path);
        System.out.println(parsedXML[0]);
        System.out.println(parsedXML[1]);
        System.out.println(parsedXML[2]);
        try{

            for (int i=0;i< parsedXML.length-3;i++) {
                Class cls=Class.forName(parsedXML[i+3]);
                Metamodel<Class> userMetamodel = Metamodel.of(cls);
                db.add(userMetamodel);
                db.print(i);
            }


        }catch(ClassNotFoundException e){
            System.out.println("ERROR");
            e.printStackTrace();
        }

            connectionManager.setConnection(parsedXML[0], parsedXML[1], parsedXML[2]);

    }
































    public void logo(){
        System.out.println("  @      @@@             .     @@@                           @@@@@@@@@@@@@              @@@@@@@@@@        @@@@@                               @@@@@@@@      @@@@@@@@   @       ");
        System.out.println("    @@@@@@                #@@@@@                         &@@   @#@@@@@@@@@@@@%                @@@@@@   @#  @@@@@@                        *@@@     @@@@@ @@ @    @@@@@@@  ");
        System.out.println("     @@@@                  @@@@@                     (@@@@@@   @        @@@@@@@@               @@@@@@  @     @@@@@@                    @@  @      @@@@@    @     @@@@@ ");
        System.out.println("     @@@@                  @@@@@                 &@@@  @@@@@   @           @@@@@@              @@@@@   @       @@@@@@                @@@   @      @@@@@    @     @@@@@ ");
        System.out.println("     @@@@     @@@@         @@@@@   %@@@@@       @@@    @@@@@   @(@        @@ @@@@@          @@ @@@@@   @        @@                  @@@@  &(      @@@@@    @     @@@@@            @@@@@@@             ");
        System.out.println("     @@@@  @  @@@@@@@      @@@@@%@   @@@@@     @@@     @@@@@   @              @@@@      @@     @@@@@   @    .@,                    @@@@@  &( .....@@@@@....@...  @@@@@        @@@@@  @@@@@@          ");
        System.out.println("     @@@@@       @         @@@@@     @@@@@    @@@@     @@@@@   @              @@@@      (@@@@@ @@@@@   @ @@@@@@@*               *@@@@@@@  &#@@@@@@@@@@@@@@@@@    @@@@@        @@@@@     @@@@@       ");
        System.out.println("     @@@@     @@           @@@@@     @@@@@    @@@@     @@@@@   @ @         @%@@@@           @@@@@@@@   @    .@@@@@@           @@@@@@@@@@  &(      @@@@@    @     @@@@@        @@@@@    (@@          ");
        System.out.println("     @@@@  @@@@@@@         @@@@@     @@@@@    @@@@@    @@@@    @            @@@@              @@@@@@   @       @@@@@                @@@@  &(      @@@@@    @     @@@@@        @@@@@ @@             ");
        System.out.println("     @@@@@    @@@@@        @@@@@     @@@@@     @@@@@@  @@      @           @@@                 @@@@@   @        @@@@@                /@@  &(      @@@@@    @     @@@@@        @@@@@            ");
        System.out.println("     @@@@      @@@@@       @@@@@     @@@@@      @@@@@@@        @        @@@                    @@@@@   @         @@@@@                @@  &(      @@@@@    @.@   @@@@@        @@@@@            ");
        System.out.println("    @@@@@@@  @&,@@@@  @   %@@@@@     @@@@@        @@@@@@@@@,   @    @@@@#                @@@@@@@@@@@@@@@@@*  @@   @@@@@  @@          (@@  @(     @@@@@@@@@@      /@@@@@  @    @@@@@@@     @   ");
        System.out.println("      @@@@@%    @@@@,      @@@@@     @@@@.           (@@@@@@@@@@@@@@/                 @@@           /@@@@@@@       @@@@@         (@@#  *@@@         @@@@          @@@@@,        @@@@@@@@    ");
        System.out.println("                                    /@@@                                                                                     @@@@@@@@@@@@ ");
        System.out.println("                                  @@(                                                                                       @@");
        System.out.println("                             @@@ ");
        System.out.println("----------------------------------------------------------------------------------------Code for the Code God----------------------------------------------------------------------------------------");
    }

}
