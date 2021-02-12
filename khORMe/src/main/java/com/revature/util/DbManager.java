package com.revature.util;

import java.util.ArrayList;
import java.util.List;


public class DbManager {
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
    private ArrayList<Metamodel<Class<?>>> tables=new ArrayList<>();

    private DbManager() {
        super();
    }

    /** eager singleton */
    private static DbManager db = new DbManager();
    public static DbManager getInstance() {
        return db;
    }


    static {

    }






    public void add(Metamodel mm){
        tables.add(mm);
    }


    public ArrayList<Metamodel<Class<?>>> getAll() {
        return (tables == null) ? null : tables;
    }

    public Metamodel<Class<?>> get(int i) {
        return tables.get(i);
    }


    public void print(int i){
        Metamodel mm=tables.get(i);
        List<ColumnField> columnFields = mm.getColumns();
        System.out.println("\nTABLE: "+mm.getTable().getTableName());
        System.out.println("_______________________________________");
        System.out.print("| PK:"+mm.getPrimaryKey().getKeyName()+"("+mm.getPrimaryKey().getType().getSimpleName()+") | ");
        for(ColumnField columnField :columnFields) {
            System.out.print(""+columnField.getColumnName()+"("+columnField.getType().getSimpleName()+") | ");
        }
        System.out.println("");
    }
}
