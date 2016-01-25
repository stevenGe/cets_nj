package com.njcets.tools.core.xls.template;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gexinl
 */
public class XLSTemplateItem {
    private String columnNames;

    private String filters;

    private String orderBySequence;

    public String getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String columnNames) {
        this.columnNames = columnNames;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public String getOrderBySequence() {
        return orderBySequence;
    }

    public void setOrderBySequence(String orderBySequence) {
        this.orderBySequence = orderBySequence;
    }

    public List<String> getColumnNamesAsList() {
        String[] columnNamesArray = this.columnNames.split(",");
        List<String> result = new ArrayList<String>();
        for(String oneColumn : columnNamesArray){
            result.add(oneColumn);
        }
        return result;
    }
}
