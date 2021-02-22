package com.revature.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * api initializer
 * handles the initial call to start up portions of the api
 * calls
 */
public class Initializer {
    private static final Logger logger = LogManager.getLogger(Initializer.class);

    DbManager db = DbManager.getInstance();
    XMLParser xml = new XMLParser();
    ConnectionManager connectionManager=ConnectionManager.getInstance();

    public void initialize(String configPath) {
        logo();
        logger.info("Initializing application");
        initialConfig(configPath);
        logger.info("Initialization Complete");

    }




    private void initialConfig(String path){
        logger.info("Parsing XML Configuration File");
        String[] parsedXML= xml.parse(path);
        logger.info("Creating Metamodels");
        try{

            for (int i=0;i< parsedXML.length-3;i++) {
                Class cls=Class.forName(parsedXML[i+3]);
                Metamodel<Class> userMetamodel = Metamodel.of(cls);
                db.add(userMetamodel);
            }


        }catch(ClassNotFoundException e){
            System.out.println("ERROR");
            e.printStackTrace();
        }
        logger.info("Initializing connection pool");
        connectionManager.setConnection(parsedXML[0], parsedXML[1], parsedXML[2]);
        logger.debug("pools created");
    }






























    String C_BLK="\u001b[30m";
    String C_RED="\u001b[31m";

    public void logo(){
        System.out.println(C_BLK+"  @      @@@             .     @@@          "+C_RED+"                 @@@@@@@@@@@@@              @@@@@@@@@@        @@@@@                               @@@@@@@@      @@@@@@@@   @       ");
        System.out.println(C_BLK+"    @@@@@@                #@@@@@            "+C_RED+"             &@@   @#@@@@@@@@@@@@%                @@@@@@   @#  @@@@@@                        *@@@     @@@@@ @@ @    @@@@@@@  ");
        System.out.println(C_BLK+"     @@@@                  @@@@@            "+C_RED+"         (@@@@@@   @        @@@@@@@@               @@@@@@  @     @@@@@@                    @@  @      @@@@@    @     @@@@@ ");
        System.out.println(C_BLK+"     @@@@                  @@@@@            "+C_RED+"     &@@@  @@@@@   @           @@@@@@              @@@@@   @       @@@@@@                @@@   @      @@@@@    @     @@@@@ ");
        System.out.println(C_BLK+"     @@@@     @@@@         @@@@@   %@@@@@   "+C_RED+"    @@@    @@@@@   @(@        @@ @@@@@          @@ @@@@@   @        @@                  @@@@  &(      @@@@@    @     @@@@@     "+C_BLK+"       @@@@@@@             ");
        System.out.println(C_BLK+"     @@@@  @  @@@@@@@      @@@@@%@   @@@@@  "+C_RED+"   @@@     @@@@@   @              @@@@      @@     @@@@@   @    .@,                    @@@@@  &( .....@@@@@....@...  @@@@@     "+C_BLK+"   @@@@@  @@@@@@          ");
        System.out.println(C_BLK+"     @@@@@       @         @@@@@     @@@@@  "+C_RED+"  @@@@     @@@@@   @              @@@@      (@@@@@ @@@@@   @ @@@@@@@*               *@@@@@@@  &#@@@@@@@@@@@@@@@@@    @@@@@     "+C_BLK+"   @@@@@     @@@@@       ");
        System.out.println(C_BLK+"     @@@@     @@           @@@@@     @@@@@  "+C_RED+"  @@@@     @@@@@   @ @         @%@@@@           @@@@@@@@   @    .@@@@@@           @@@@@@@@@@  &(      @@@@@    @     @@@@@     "+C_BLK+"   @@@@@    (@@          ");
        System.out.println(C_BLK+"     @@@@  @@@@@@@         @@@@@     @@@@@  "+C_RED+"  @@@@@    @@@@    @            @@@@              @@@@@@   @       @@@@@                @@@@  &(      @@@@@    @     @@@@@     "+C_BLK+"   @@@@@ @@             ");
        System.out.println(C_BLK+"     @@@@@    @@@@@        @@@@@     @@@@@  "+C_RED+"   @@@@@@  @@      @           @@@                 @@@@@   @        @@@@@                /@@  &(      @@@@@    @     @@@@@     "+C_BLK+"   @@@@@            ");
        System.out.println(C_BLK+"     @@@@      @@@@@       @@@@@     @@@@@  "+C_RED+"    @@@@@@@        @        @@@                    @@@@@   @         @@@@@                @@  &(      @@@@@    @.@   @@@@@     "+C_BLK+"   @@@@@            ");
        System.out.println(C_BLK+"    @@@@@@@  @&,@@@@  @   %@@@@@     @@@@@  "+C_RED+"      @@@@@@@@@,   @    @@@@#                @@@@@@@@@@@@@@@@@*  @@   @@@@@  @@          (@@  @(     @@@@@@@@@@      /@@@@@  @ "+C_BLK+"   @@@@@@@     @   ");
        System.out.println(C_BLK+"      @@@@@%    @@@@,      @@@@@     @@@@.  "+C_RED+"         (@@@@@@@@@@@@@@/                 @@@           /@@@@@@@       @@@@@         (@@#  *@@@         @@@@          @@@@@,   "+C_BLK+"     @@@@@@@@    ");
        System.out.println(C_BLK+"                                    /@@@    "+C_RED+"                                                                                 @@@@@@@@@@@@ ");
        System.out.println(C_BLK+"                                  @@(       "+C_RED+"                                                                                @@");
        System.out.println(C_BLK+"                             @@@ ");
        System.out.println(C_RED+"----------------------------------------------------------------------------------------"+C_BLK+"Code for the Code God"+C_RED+"----------------------------------------------------------------------------------------");
    }

}
