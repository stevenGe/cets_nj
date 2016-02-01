package com.njcets.tools.core.db;

import com.njcets.tools.core.data.MaterialTable;
import com.njcets.tools.core.data.Row;
import com.njcets.tools.core.pmmswriter.XLSWriter;
import com.njcets.tools.core.xls.template.XLSTemplateItem;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gexinl
 */
public class DBHelper {
    private Logger logger = Logger.getLogger(DBHelper.class);

    private Connection dbConnection;


    public DBHelper() {
        DBConnectionManager DBConnectionManager = new DBConnectionManager();
        this.dbConnection = DBConnectionManager.getDBConnection();
//        try {
//            this.dbConnection.setAutoCommit(false);
//        } catch (SQLException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
    }

    // delete table if exist
    public void deleteTableIfExisted() {
        Statement stmt = null;
        try {
            boolean isPMMSTableExist = false;
            stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT name FROM sqlite_master WHERE type='table' AND name='PMMS_RESULT';" );
            while ( rs.next() ) {
                String  name = rs.getString("name");
                if(name.equalsIgnoreCase("PMMS_RESULT")) {
                    isPMMSTableExist = true;
                    logger.info("Table [PMMS_RESULT] is already existed.");
                }

            }
            rs.close();
            stmt.close();

            if(isPMMSTableExist) {
                // drop table
                logger.info("Drop table [PMMS_RESULT].");
                stmt = dbConnection.createStatement();
                stmt.executeUpdate("DROP TABLE IF EXISTS PMMS_RESULT;");
                stmt.close();
            }

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    // create table
    public void createTable(MaterialTable materialTable) {
        List<String> titles = materialTable.getTitle();
        Statement stmt = null;
        try {
            stmt = dbConnection.createStatement();

            StringBuffer createTableSQLSb = new StringBuffer("CREATE TABLE PMMS_RESULT ( ");

            for(int i = 0; i < titles.size(); i++) {
                String oneTitle = titles.get(i);
                if((titles.size() - 1) != i) {
                    createTableSQLSb.append(oneTitle + " TEXT, ");
                } else {
                    createTableSQLSb.append(oneTitle + " TEXT ");
                }
            }
            createTableSQLSb.append(");");

            logger.info("create table sql: " + createTableSQLSb.toString());
            stmt.executeUpdate(createTableSQLSb.toString());
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    // insert data to table
    public void insertData2Table(MaterialTable materialTable) {
        List<String> titles = materialTable.getTitle();
        List<Row> rows = materialTable.getRows();
        PreparedStatement preparedStatement = null;
        String insertSql = "INSERT INTO PMMS_RESULT values ( ";
        for(int i = 0; i < titles.size(); i++) {
            if((titles.size() - 1) != i) {
                insertSql += "?, ";
            }else {
                insertSql += "? ";
            }
        }
        insertSql += " );";
        logger.info("insert sql statement is:" + insertSql);
        try {
            preparedStatement = dbConnection.prepareStatement(insertSql);
//            dbConnection.setAutoCommit(false);

            for(int i = 0; i < rows.size(); i++) {
                Row oneRow = rows.get(i);
                for(int j = 0; j < oneRow.length(); j++) {
                    preparedStatement.setString(j + 1, oneRow.getValueByColumnIndex(j));
                }
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
//            connection.commit();
            preparedStatement.close();
            logger.info("Insert sql statement executed.");

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    // read out all column names from table
    public List<String> readTableColumnNames() {
        List<String> titles = new ArrayList<String>();
        Statement stmt = null;
        try {
            stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PMMS_RESULT;");
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++ ) {
                String columnName = rsmd.getColumnName(i);
                titles.add(columnName);
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return titles;
    }

    // read out data from PMMS_RESULT table
    public void readDataToXLSWriter(XLSWriter xlsWriter, XLSTemplateItem xlsTemplateItem){
        List<String> columnNames = xlsTemplateItem.getColumnNamesAsList();
        Statement stmt = null;
        try {
            stmt = dbConnection.createStatement();
            String sqlStatement = generatreSQlStatement(xlsTemplateItem);
            logger.info("Generate SQL Statement is: [" + sqlStatement + "]");
//            ResultSet rs = stmt.executeQuery("SELECT * FROM PMMS_RESULT;");
            ResultSet rs = stmt.executeQuery(sqlStatement);

            while(rs.next()) {
                List<String> columnValues = new ArrayList<String>();
                for(String oneColumn : columnNames) {
                    String oneColumnValue = rs.getString(oneColumn);
                    columnValues.add(oneColumnValue);
                }
                xlsWriter.addOneRow2XLS(columnValues);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void readDataToXLSWriter(XLSWriter xlsWriter){
        List<String> columnNames = readTableColumnNames();
        Statement stmt = null;
        try {
            stmt = dbConnection.createStatement();
            logger.info("Generate SQL Statement is: " + "[SELECT * FROM PMMS_RESULT;]");
            ResultSet rs = stmt.executeQuery("SELECT * FROM PMMS_RESULT;");

            while(rs.next()) {
                List<String> columnValues = new ArrayList<String>();
                for(String oneColumn : columnNames) {
                    String oneColumnValue = rs.getString(oneColumn);
                    columnValues.add(oneColumnValue);
                }
                xlsWriter.addOneRow2XLS(columnValues);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void closeDBConnection() {
        try {
            this.dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private String generatreSQlStatement(XLSTemplateItem xlsTemplateItem) {
        StringBuffer sqlStatement = new StringBuffer("SELECT ");
        sqlStatement.append(xlsTemplateItem.getColumnNames()).append(" ");
        sqlStatement.append("FROM PMMS_RESULT ");
        sqlStatement.append(" WHERE ").append(xlsTemplateItem.getFilters());
        sqlStatement.append(" ORDER BY ").append(xlsTemplateItem.getOrderBySequence()).append(";");
        return sqlStatement.toString();
    }
}
