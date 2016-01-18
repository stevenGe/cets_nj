package com.njcets.tools.core.data;


import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * @author gexinl
 * This object encapsulation the Row Object for XLS to write out
 */

@Deprecated

public class XLSRow {
    private Row row;

    public XLSRow(Sheet sheet) {
        this.row = sheet.createRow(0);
    }
}
