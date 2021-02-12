package com.revature.util;

import com.revature.services.BasicConnectionPool;
import com.revature.services.ConnectionFactory;
import com.revature.util.DbManager;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Initializer {

    DbManager db = DbManager.getInstance();
    XMLParser xml = new XMLParser();
    BasicConnectionPool basicConnectionPool;

    public void initialize(String configPath) {
        logo();
        initialConfig(configPath);
        trySQL(db);



    }

    private void trySQL(DbManager db) {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()){
        //try(Connection conn = basicConnectionPool.getConnection()){
            Metamodel<Class<?>> table=db.get(0);
            String sql = "select*from "+table.getTable().getTableName()+";";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            ColumnField[] fields = table.getColumns().stream().toArray(ColumnField[]::new);
            mapResults(rs, fields);

        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void mapResults(ResultSet rs,ColumnField[] fields) throws SQLException{
        while(rs.next()) {
            for (ColumnField field : fields) {
                switch(field.getType().toString()){
                    case "int":
                        System.out.print(rs.getInt(field.getName())+" ");
                        break;
                    case "class java.lang.String":
                        System.out.println(rs.getString(field.getName())+" ");
                        break;
                    default:
                        System.out.println("ERROR DEFAULT -- field.getType()");
                        break;
                }
            }
            System.out.print("\n");

        }
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

        try {
            basicConnectionPool= BasicConnectionPool.create(parsedXML[0], parsedXML[1], parsedXML[3]);
        }catch(SQLException e){
            e.printStackTrace();
        }
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
