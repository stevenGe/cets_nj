package com.njcets.tools.core.data;

/**
 * @author gexinl
 */
public class ColumnMetaData {

    private String columnName;

    private int columnIndex;

    private int columnValueLength;

    public ColumnMetaData() {
    }

    public ColumnMetaData(String columnName, int columnIndex, int columnValueLength) {
        this.columnName = columnName;
        this.columnIndex = columnIndex;
        this.columnValueLength = columnValueLength;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getColumnValueLength() {
        return columnValueLength;
    }

    public void setColumnValueLength(int columnValueLength) {
        this.columnValueLength = columnValueLength;
    }
}
