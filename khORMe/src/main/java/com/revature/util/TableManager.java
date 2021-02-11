package com.revature.util;

import java.util.ArrayList;
import java.util.List;

public class TableManager {
    private ArrayList<Metamodel> tables;

    public TableManager(){
        super();
        tables=new ArrayList<>();
    }

    public void add(Metamodel mm){
        tables.add(mm);
    }


    public void get(){}


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
