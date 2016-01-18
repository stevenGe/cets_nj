package com.njcets.tools.core.data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gexinl
 */

public class Row {

    private Map<Integer, String> values;    // column_index - column-value

    public Row() {
        this.values = new HashMap<Integer, String>();
    }

    public String getValueByColumnIndex(int index) {
        return  values.get(index);
    }

    public void setValueByColumnIndex(int index, String value) {
        this.values.put(index, value);
    }

    public int length() {
        return values.size();
    }

    public void printRow() {
        for(int i = 0; i < values.size(); i++) {
            String oneValue = values.get(i);
            System.out.print(oneValue + "\t\t\t");
        }
    }

}
