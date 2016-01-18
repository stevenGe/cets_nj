package com.njcets.tools.core.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gexinl
 */
public class MaterialTable {

    private List<String> title;

    private List<Row> rows;

    public MaterialTable() {
        this.title = new ArrayList<String>();
        this.rows = new ArrayList<Row>();
    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public void addOneTitle(String titleColumn) {
        this.title.add(titleColumn);
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public void addRow(Row row) {
        this.rows.add(row);
    }
}
