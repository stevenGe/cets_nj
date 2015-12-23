package com.njcets.tools.core.template;

import java.util.ArrayList;
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

    public List<String> getPureColumnDefinition() {
        List<String> result = new ArrayList<String>();
        for(String oneLine : columnDefinition) {
            int indexOfDescription = oneLine.indexOf("!");
            if(indexOfDescription > 0) {
                result.add((oneLine.substring(0, indexOfDescription)).trim());
            } else {
                result.add(oneLine);
            }

        }
        return result;
    }

    public void setColumnDefinition(List<String> columnDefinition) {
        this.columnDefinition = columnDefinition;
    }
}
