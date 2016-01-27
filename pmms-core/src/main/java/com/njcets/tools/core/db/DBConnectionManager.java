package com.njcets.tools.core.db;

import com.njcets.tools.core.conf.EnvManager;
import com.njcets.tools.core.data.MaterialTable;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * @author gexinl
 */
public class DBConnectionManager {
    private Logger logger = Logger.getLogger(DBConnectionManager.class);

    private final String SQLITE3_DIRVER = "org.sqlite.JDBC";

    // TODO: get DB_PATH from config file
    private final String DB_PATH = EnvManager.getInstance().getPMMS_RESULT_DB_FILE();
//    private String DB_PATH = "/Users/gexinlu/Downloads/cets_pmms/db/pmms.db";

    private Connection connection = null;

    public Connection getDBConnection() {
        try {
            // TODO: load db properties from file

            Class.forName(SQLITE3_DIRVER);
            String url = "jdbc:sqlite:" + DB_PATH;
            logger.info("Database connection url is: " + url);
            connection = DriverManager.getConnection(url);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        logger.info("Opened database successfully");
        return connection;
    }

    public void closeConnection() {
        try{
            if(connection != null) {
                connection.close();
            }
            logger.info("Close connection successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to close connection successfully.");
            System.out.println();
        }
    }

}
