package com.revature.util;

import com.revature.annotations.Table;

/**
 * strores the class name and table name for the metamodel
 */
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
