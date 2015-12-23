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

    public int length() {
        return values.size();
    }

}
