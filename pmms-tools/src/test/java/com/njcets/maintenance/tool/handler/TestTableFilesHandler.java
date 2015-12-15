package com.njcets.maintenance.tool.handler;

/**
 * @author gexinl
 */

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class TestTableFilesHandler {
    private TableFilesHandler tableFilesHandler;

    @Before
    public void setup() {
        tableFilesHandler = new TableFilesHandler();
        tableFilesHandler.initilizeTableXML();
    }

    @After
    public void teardown() {

    }

    @Test
    public void testReadTableFilesToXML() {
        tableFilesHandler.readTableFilesToXML("C:\\Users\\gexin\\Desktop\\PMMS\\resources");
    }

}
