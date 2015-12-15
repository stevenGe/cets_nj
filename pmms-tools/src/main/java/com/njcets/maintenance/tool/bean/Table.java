package com.njcets.maintenance.tool.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gexinl
 */
public class Table {

    private String tableName;

    private Map<String, String> columns;    // key-value pairs: column_name, column_type

    public Table(String tableName) {
        this.tableName = tableName;
        this.columns = new HashMap<String, String>();
    }

    public String getTableName() {
        return tableName;
    }

    public Map<String, String> getColumns() {
        return columns;
    }

}
