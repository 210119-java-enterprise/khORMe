package com.revature.util;

import com.revature.forum.models.Board;

public class Initializer {

    public void initialize() {
        logo();
        XMLParser xml = new XMLParser();
        String[] parsedXML= xml.parse();
        for (String table:parsedXML) {
            System.out.println(table);
        }

        try{
            TableManager tableManager= new TableManager();
            for (int i=0;i< parsedXML.length;i++) {
                Class cls=Class.forName(parsedXML[i]);
                Metamodel<Board> userMetamodel = Metamodel.of(cls);
                tableManager.add(userMetamodel);
                tableManager.print(i);
            }


        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }

//        TableManager tableManager= new TableManager();
//        Metamodel<Boards> userMetamodel = Metamodel.of(Boards.class);
//        tableManager.add(userMetamodel);
//        tableManager.print(0);

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
