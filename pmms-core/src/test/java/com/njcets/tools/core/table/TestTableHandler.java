package com.njcets.tools.core.table;

/**
 * @author gexinl
 */

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class TestTableHandler {
    private TableHandler tableHandler;

    @Before
    public void setup() {
        this.tableHandler = new TableHandler("C:/Work/github/cets_nj/out/PMMS/resources/Tables.xml");
    }

    @After
    public void teardown() {

    }

    //@Test
    public void testParseTableXML() {
        tableHandler.parseTableXML();
        System.out.println(tableHandler.getTables().size());
    }

    //@Test
    public void testGetTableColumn() {
        tableHandler.parseTableXML();
        TableColumn result = tableHandler.getTableColumn("Table-1", 1);
        System.out.println(result.getIndex());
        System.out.println(result.getColumnName());
        System.out.println(result.getColumnType());
    }

}
