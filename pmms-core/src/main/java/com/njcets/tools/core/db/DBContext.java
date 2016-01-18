package com.njcets.tools.core.db;

import com.njcets.tools.core.settings.SettingManagement;

/**
 * DBContext manage all of the database connections for PMMS.
 * @author gexinl
 */

@Deprecated

public class DBContext {

    private DBType dbType;

    private String driver;

    private String url;

    private String userName;

    private String password;

    private DBContext(DBType dbType, String driver, String url, String userName, String password) {
        this.dbType = dbType;
        this.driver = driver;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public static DBContext createDBContext(DBType dbType, String url, String userName, String password) {
        if( dbType == DBType.SQLITE3 ) {
            return new DBContext( dbType, SettingManagement.DRIVER_SQLITE3, url, userName, password );
        } else { // use SQLITE3 as default
            return new DBContext( dbType, SettingManagement.DRIVER_SQLITE3, url, userName, password );
        }
    }

}
