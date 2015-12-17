package com.njcets.tools.core.table;

import nu.xom.Element;
import nu.xom.Elements;

/**
 * @author gexinl
 */
public class TableColumn {
    private int index;

    private String columnName;

    private String columnType;

    public TableColumn() {
    }

    public TableColumn(int index, String columnName, String columnType) {
        this.index = index;
        this.columnName = columnName;
        this.columnType = columnType;
    }

    public TableColumn(Element element) {
        index = Integer.valueOf(element.getAttributeValue("index"));
        columnName = element.getFirstChildElement("ColumnName").getValue();
        columnType = element.getFirstChildElement("ColumnType").getValue();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }
}
