package com.njcets.tools.core.data;

/**
 * @author gexinl
 */
public class Column {

    private String columnName;  // get column name from template file.

    private String columnValue; // get column value from data file.

    private int startIndex;     // the index in data file, start from 0.

    private int dataLength;     // the length in data file, use this with startIndex.

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

}
