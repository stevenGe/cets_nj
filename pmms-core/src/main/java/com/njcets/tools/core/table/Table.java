package com.njcets.tools.core.table;

import nu.xom.Element;
import nu.xom.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gexinl
 */
public class Table {
    private String tableName;

    private List<TableColumn> columns;

    public Table(String tableName) {
        this.tableName = tableName;
        this.columns = new ArrayList<TableColumn>();
    }

    public Table(Element element) {
        this.columns = new ArrayList<TableColumn>();

        Element tableNameElement = element.getFirstChildElement("TableName");
        Element tableColumnsElement = element.getFirstChildElement("TableColumns");

        this.tableName = tableNameElement.getValue();

        Elements columnElements = tableColumnsElement.getChildElements();
        for(int i = 0; i < columnElements.size(); i++) {
            Element oneColumnElement = columnElements.get(i);
            TableColumn oneTableColumn = new TableColumn(oneColumnElement);
            columns.add(oneTableColumn);
        }
    }

    public TableColumn getColumnByIndex(int index) {
        if(this.columns != null) {
            return columns.get(index - 1);
        } else {
            return null;
        }
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<TableColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<TableColumn> columns) {
        this.columns = columns;
    }
}