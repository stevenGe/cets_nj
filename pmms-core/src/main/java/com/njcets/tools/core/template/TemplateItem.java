package com.njcets.tools.core.template;

import java.util.List;

/**
 * @author gexinl
 */

public class TemplateItem {

    private String itemName;

    private int columnLength;

    private List<String> columnDefinition;

    public TemplateItem(String itemName, int columnLength) {
        this.itemName = itemName;
        this.columnLength = columnLength;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getColumnLength() {
        return columnLength;
    }

    public void setColumnLength(int columnLength) {
        this.columnLength = columnLength;
    }

    public List<String> getColumnDefinition() {
        return columnDefinition;
    }

    public void setColumnDefinition(List<String> columnDefinition) {
        this.columnDefinition = columnDefinition;
    }
}
