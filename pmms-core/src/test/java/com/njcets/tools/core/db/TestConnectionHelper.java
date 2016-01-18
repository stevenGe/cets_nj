package com.njcets.tools.core.db;

/**
 * @author gexinl
 */
public class TestConnectionHelper {


    public static void main(String[] args) {
        DBConnectionManager DBConnectionManager = new DBConnectionManager();
        DBConnectionManager.getDBConnection();
        DBConnectionManager.closeConnection();
    }

}
