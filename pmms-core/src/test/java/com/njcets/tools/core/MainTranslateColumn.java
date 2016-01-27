//package com.njcets.tools.core;
//
//import com.njcets.tools.core.data.ColumnMetaData;
//import com.njcets.tools.core.data.MaterialTable;
//import com.njcets.tools.core.data.Row;
//import com.njcets.tools.core.db.DBConnectionManager;
//import com.njcets.tools.core.pmmswriter.XLSWriter;
//import com.njcets.tools.core.reader.AbstractFileReader;
//import com.njcets.tools.core.reader.FileReaderFactory;
//import com.njcets.tools.core.rule.Rule;
//import com.njcets.tools.core.rule.RuleHandler;
//import com.njcets.tools.core.table.Table;
//import com.njcets.tools.core.table.TableColumn;
//import com.njcets.tools.core.table.TableHandler;
//import com.njcets.tools.core.template.Template;
//import com.njcets.tools.core.template.TemplateHandler;
//import com.njcets.tools.core.template.TemplateItem;
//import com.njcets.tools.core.translater.ColumnNameTranslaterHandler;
//import org.apache.commons.lang3.StringUtils;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author gexinl
// */
//public class MainTranslateColumn {
//
//    public static void main(String[] args) {
//
//
//        // parse Template File
//        TemplateHandler templateHandler  = new TemplateHandler("mtorecord", "C:\\Work\\github\\cets_nj\\out\\PMMS\\templateSource\\isomto_original.txt" );
//        templateHandler.generateTemplate();
//        Template template = templateHandler.getTemplate();
////        System.out.println(template.getTemplateName());
//
//        List<TemplateItem> templateItems = template.getItems();
//        for(TemplateItem oneTemplateItem : templateItems) {
////            System.out.println(oneTemplateItem.getItemName());
//            List<String> columnDefnitions = oneTemplateItem.getColumnDefinition();
//            for(String oneColumnDef : columnDefnitions) {
////                System.out.println(oneColumnDef);
//            }
//        }
//
//        // parse Table File
//        TableHandler tableHandler = new TableHandler("C:/Work/github/cets_nj/out/PMMS/resources/Tables.xml");
//        tableHandler.parseTableXML();
//        Map<String, Table> tables = tableHandler.getTables();
////        System.out.println(tables.size());
//        TableColumn tableColumn = tableHandler.getTableColumn("Table67", 1);
////        System.out.println(tableColumn.getColumnName());
//
//
//        // parse Rule File
//        RuleHandler ruleHandler = new RuleHandler("C:\\Work\\github\\cets_nj\\out\\PMMS\\resources\\PMMS-Rules.xml");
//        ruleHandler.parseRuleXML();
//        Map<String, Rule> rules = ruleHandler.getRulesMap();
////        System.out.println("========== Rule's size is: " + rules.size());
//
//        // read lines from .b file
//        // TODO: input should be a directory not a regular file
//        AbstractFileReader fileReader = FileReaderFactory.getFileReader("TXT");
//        fileReader.open("C:\\Work\\github\\cets_nj\\out\\mto1\\b1\\aos020801.b1");
//        List<String> lines = fileReader.readLines();
//        fileReader.close();
////        System.out.println(lines.size());
//
//        // translate column name
//        ColumnNameTranslaterHandler columnNameTranslater = new ColumnNameTranslaterHandler();
//
//        columnNameTranslater.setRuleHandler(ruleHandler);
//        columnNameTranslater.setTableHandler(tableHandler);
//        columnNameTranslater.setTemplateHandler(templateHandler);
//
//        columnNameTranslater.translate();
//        List<ColumnMetaData> columnsMetaDataList = columnNameTranslater.getColumnMetaDataList();
////        System.out.println(columnsMetaDataList.size());
//        for(ColumnMetaData columnMetaData : columnsMetaDataList) {
//            System.out.println(columnMetaData.getColumnName());
////            System.out.println(columnMetaData.getColumnIndex());
////            System.out.println(columnMetaData.getColumnValueLength());
//        }
//
//        // generate MaterialTable Object
//        MaterialTable materialTable = new MaterialTable();
//
//        //generate MaterialTable Title
//        for(ColumnMetaData oneColumnMetaData : columnsMetaDataList) {
//            materialTable.addOneTitle(oneColumnMetaData.getColumnName());
//        }
//
//        // generate MaterialTable Row
//        for(String oneBLine : lines) {
//            System.out.println("==============Start to handle BLine: " + oneBLine);
//            Row row = new Row();
//            for(int i = 0; i < columnsMetaDataList.size(); i++) {
//                ColumnMetaData columnMetaData = columnsMetaDataList.get(i);
//                int columnIndex = columnMetaData.getColumnIndex();
//                int columnLength = columnMetaData.getColumnValueLength();
//                String columnValue = StringUtils.substring(oneBLine, columnIndex, columnIndex + columnLength);
////                String columnValue = oneBLine.substring(columnIndex, columnLength);
//                if(columnValue == null || columnValue.trim().equals("")) {
//                    columnValue = "NOTRELEVANT";
//                }
//                System.out.println("=======get column Value: " + columnValue);
//                row.setValueByColumnIndex(i, columnValue);
//            }
//            materialTable.addRow(row);
//        }
//
//        List<String> titles = materialTable.getTitle();
//        List<Row> rows = materialTable.getRows();
//
//        // print out the MaterialTable Object
////        List<String> titleInMTObj = materialTable.getTitle();
////        for(String oneTitle : titleInMTObj) {
////            System.out.print(oneTitle + "\t\t\t");
////        }
////        System.out.println();
////
////        List<Row> rowInMtOjb = materialTable.getRows();
////        for(Row oneRow : rowInMtOjb) {
////            oneRow.printRow();
////            System.out.println();
////        }
//
//        // import MaterialTable Ojbect to SQLite3 Database
//        DBConnectionManager DBConnectionManager = new DBConnectionManager();
//        Connection connection = DBConnectionManager.getDBConnection();
//        Statement stmt = null;
//        // delete table if exist
//        try {
////            connection.setAutoCommit(false);
//
//            boolean isPMMSTableExist = false;
//            stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery( "SELECT name FROM sqlite_master WHERE type='table' AND name='PMMS_RESULT';" );
//            while ( rs.next() ) {
//                String  name = rs.getString("name");
//                System.out.println("========DEBUG: table name: " + name);
//                if(name.equalsIgnoreCase("PMMS_RESULT")) {
//                    isPMMSTableExist = true;
//                    System.out.println("========DEBUG: table PMMS_RESULT EXIST!");
//                }
//
//            }
//            rs.close();
//            stmt.close();
//
//            if(isPMMSTableExist) {
//                // drop table
//                System.out.println("========DEBUG: drop table PMMS_RESULT!");
//                stmt = connection.createStatement();
//                stmt.executeUpdate("DROP TABLE IF EXISTS PMMS_RESULT;");
//                stmt.close();
//            }
//
//        } catch ( Exception e ) {
//            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//        }
//
//        // create table
//        try {
//            stmt = connection.createStatement();
//
//            StringBuffer createTableSQLSb = new StringBuffer("CREATE TABLE PMMS_RESULT ( ");
//
//            for(int i = 0; i < titles.size(); i++) {
//                String oneTitle = titles.get(i);
//                if((titles.size() - 1) != i) {
//                    createTableSQLSb.append(oneTitle + " TEXT, ");
//                } else {
//                    createTableSQLSb.append(oneTitle + " TEXT ");
//                }
//            }
//            createTableSQLSb.append(");");
//
//            System.out.println("=============== DEBUG: sql is: " + createTableSQLSb.toString());
//            stmt.executeUpdate(createTableSQLSb.toString());
//            stmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//
//        // import data to this table
//        PreparedStatement preparedStatement = null;
//        String insertSql = "INSERT INTO PMMS_RESULT values ( ";
//        for(int i = 0; i < titles.size(); i++) {
//            if((titles.size() - 1) != i) {
//                insertSql += "?, ";
//            }else {
//                insertSql += "? ";
//            }
//        }
//        insertSql += " );";
//        System.out.println("=========== DEBUG: insert sql is:" + insertSql);
//        try {
//            preparedStatement = connection.prepareStatement(insertSql);
//            connection.setAutoCommit(false);
//
//            for(int i = 0; i < rows.size(); i++) {
//                Row oneRow = rows.get(i);
//                for(int j = 0; j < oneRow.length(); j++) {
//                    preparedStatement.setString(j + 1, oneRow.getValueByColumnIndex(j));
//                }
//                preparedStatement.addBatch();
//            }
//
//            preparedStatement.executeBatch();
//            connection.commit();
//            preparedStatement.close();
//            System.out.println("========================== DEBUG: Insert sql execute.");
//
//        } catch (SQLException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//
//        // prepare the xls search template (with group by and order by)
//
//        // TODO : support modify xsl tempalte ( write template to a file and use python (UI) to modify it
//
//        // read out the required data from SQLite3 Database using xls template
//        // TODO: read out the required data from xls template string and group by string
//
//        String xlsFilePath = "C:\\Work\\github\\cets_nj\\out\\PMMS\\export\\export.xls";
//        XLSWriter xlsWriter = new XLSWriter(xlsFilePath);
//        xlsWriter.addTitle2XLS(titles);
//
//        try {
//            stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM PMMS_RESULT;");
//
//            while(rs.next()) {
//                List<String> columnValues = new ArrayList<String>();
//                for(String oneColumn : titles) {
//                    String oneColumnValue = rs.getString(oneColumn);
//                    columnValues.add(oneColumnValue);
//                }
//                xlsWriter.addOneRow2XLS(columnValues);
//            }
//
//            rs.close();
//            stmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//
//        //close the database connection
//        DBConnectionManager.closeConnection();
//
//        // export the data to xls file  (write the search result to xls file)
//        // TODO: specify the xls file while export data
//        xlsWriter.writeToXLS();
//    }
//}
