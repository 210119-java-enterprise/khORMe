package com.revature.util;

import com.revature.annotations.Table;

public class TableData {
    private String tableName;
    private String className;

    public TableData(String className, String tableName) {
        this.className = className;
        this.tableName = tableName;
    }

    public String getClassName() {
        return className;
    }

    public String getTableName() {
        return tableName;
    }

}
