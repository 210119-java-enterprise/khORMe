package com.revature.util;

import com.revature.services.ConnectionManager;
import com.revature.services.ConnectionPool;

public class Initializer {

    DbManager db = DbManager.getInstance();
    XMLParser xml = new XMLParser();
    ConnectionPool basicConnectionPool;
    ConnectionManager connectionManager=ConnectionManager.getInstance();

    public void initialize(String configPath) {
        //logo();
        initialConfig(configPath);
        System.out.println("\n\n-----Initialization Complete-------\n\n");
    }







    private void initialConfig(String path){
        System.out.println("[LOG] Parsing config file");
        String[] parsedXML= xml.parse(path);
        System.out.println("[LOG] Creating metamodels");
        try{

            for (int i=0;i< parsedXML.length-3;i++) {
                Class cls=Class.forName(parsedXML[i+3]);
                Metamodel<Class> userMetamodel = Metamodel.of(cls);
                db.add(userMetamodel);
                //db.print(i);
            }


        }catch(ClassNotFoundException e){
            System.out.println("ERROR");
            e.printStackTrace();
        }
            System.out.println("[LOG] Initializing connection pool");
            connectionManager.setConnection(parsedXML[0], parsedXML[1], parsedXML[2]);
            System.out.println("[LOG] pools created");
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
