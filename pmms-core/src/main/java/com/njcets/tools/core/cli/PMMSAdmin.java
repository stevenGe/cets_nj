package com.njcets.tools.core.cli;

import com.njcets.tools.core.data.ColumnMetaData;
import com.njcets.tools.core.data.MaterialTable;
import com.njcets.tools.core.data.Row;
import com.njcets.tools.core.db.DBHelper;
import com.njcets.tools.core.pmmswriter.XLSWriter;
import com.njcets.tools.core.reader.AbstractFileReader;
import com.njcets.tools.core.reader.FileReaderFactory;
import com.njcets.tools.core.rule.RuleHandler;
import com.njcets.tools.core.table.TableHandler;
import com.njcets.tools.core.template.TemplateHandler;
import com.njcets.tools.core.translater.ColumnNameTranslaterHandler;
import com.njcets.tools.core.xls.template.XLSTemplateHandler;
import com.njcets.tools.core.xls.template.XLSTemplateItem;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * CLI entrance for nj-cets PMMS project.
 * @author gexinl
 * @version 1.0-SNAPSHOT
 */

public class PMMSAdmin {
    public static Logger logger = Logger.getLogger(PMMSAdmin.class);

    public static void main( String[] args ) {
        Options options = new Options();

//        Option importTemplate = Option.builder( "importTemplate" )
//                .longOpt( "import-template" )
//                .desc( "import source template to db" )
//                .hasArg()
//                .numberOfArgs( 1 ).build();
//
//        Option listTemplates = Option.builder( "listTemplates" )
//                .longOpt( "list-templates" )
//                .desc( "list current existed templates" ).build();
//
//        Option deleteTemplate = Option.builder( "deleteTemplate" )
//                .longOpt("delete-template")
//                .desc("delete a specified template")
//                .hasArg()
//                .numberOfArgs( 1 ).build();

        Option importData = Option.builder( "importData" )
                .longOpt( "import-data" )
                .desc( "import data files by existed template" )
                .hasArgs()
                .numberOfArgs( 2 ).build();

        Option exportData = Option.builder( "exportData" )
                .longOpt( "export-data" )
                .desc( "export data to specified xls file" )
                .hasArgs()
                .numberOfArgs( 2 ).build();

        Option help = Option.builder( "help" )
                .longOpt( "help" )
                .desc( "print help message" )
                .build();

//        options.addOption( importTemplate );
//        options.addOption( listTemplates );
//        options.addOption( deleteTemplate );
        options.addOption( importData );
        options.addOption( exportData );
        options.addOption( help );

        // parse the command line arguments
        CommandLineParser parser = new DefaultParser();
        try {
            // parse the command line arguments
            CommandLine line = parser.parse( options, args );
            if( line.hasOption( "help" ) ) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp( "PMMSAdmin", options );
            }
//            if( line.hasOption( "import-template" ) ) {
//                String templateDir = line.getOptionValue( "import-template" );
//                System.out.println( "Import template from directory Successfully" );
//            }
//            if( line.hasOption( "list-templates" ) ) {
//                System.out.println( "list templates successfully" );
//            }
//            if ( line.hasOption( "delete-template" ))  {
//                String templateDir = line.getOptionValue( "delete-template" );
//                System.out.println( "Delete template Successfully" );
//            }
            if( line.hasOption( "importData" ) ) {
                logger.info("import data from template and material files...");
                // value-1 template file path
                // value-2 material directory path
                String[] values = line.getOptionValues( "import-data" );
                String templateFilePath = values[0];
                String materialDirPath = values[1];

                // parse Template File
                TemplateHandler templateHandler  = new TemplateHandler("DEFAULT_TEMPLATE_NAME", templateFilePath );
                templateHandler.generateTemplate();

                // parse Table File
                TableHandler tableHandler = new TableHandler();
                tableHandler.parseTableXML();

                // parse Rule File
                RuleHandler ruleHandler = new RuleHandler();
                ruleHandler.parseRuleXML();

                // read lines from material directory
                List<String> dataLines = new ArrayList<String>();
                File file = new File(materialDirPath);
                String[] bfileslist =  file.list();
                for(String oneDotbFile : bfileslist) {
                    String oneDotbFileFullPath = materialDirPath + File.separator + oneDotbFile;
                    AbstractFileReader fileReader = FileReaderFactory.getFileReader("TXT");
                    fileReader.open(oneDotbFileFullPath);
                    List<String> oneFileLines = fileReader.readLines();
                    dataLines.addAll(oneFileLines);
                    fileReader.close();
                }

                // translate column name
                ColumnNameTranslaterHandler columnNameTranslater = new ColumnNameTranslaterHandler();
                columnNameTranslater.setRuleHandler(ruleHandler);
                columnNameTranslater.setTableHandler(tableHandler);
                columnNameTranslater.setTemplateHandler(templateHandler);
                columnNameTranslater.translate();

                // generate MaterialTable Object
                MaterialTable materialTable = new MaterialTable();

                // generate MaterialTable Title
                List<ColumnMetaData> columnsMetaDataList = columnNameTranslater.getColumnMetaDataList();
                for(ColumnMetaData oneColumnMetaData : columnsMetaDataList) {
                    materialTable.addOneTitle(oneColumnMetaData.getColumnName());
                }

                // generate MaterialTable Row
                for(String oneBLine : dataLines) {
                    Row row = new Row();
                    for(int i = 0; i < columnsMetaDataList.size(); i++) {
                        ColumnMetaData columnMetaData = columnsMetaDataList.get(i);
                        int columnIndex = columnMetaData.getColumnIndex();
                        int columnLength = columnMetaData.getColumnValueLength();
                        String columnValue = StringUtils.substring(oneBLine, columnIndex, columnIndex + columnLength);
                        if(columnValue == null || columnValue.trim().equals("")) {
                            columnValue = "NOTRELEVANT";
                        }
                        row.setValueByColumnIndex(i, columnValue);
                    }
                    materialTable.addRow(row);
                }

                // import MaterialTable Ojbect to SQLite3 Database
                DBHelper dbHelper = new DBHelper();
                dbHelper.deleteTableIfExisted();
                dbHelper.createTable(materialTable);
                dbHelper.insertData2Table(materialTable);
                dbHelper.closeDBConnection();

                logger.info("Import data from directory Successfully");
                System.exit(0);
            }
            if( line.hasOption( "exportData" ) ) {
                logger.info("export data to excel file...");
                String[] values = line.getOptionValues( "export-data" );
                String xlsTemplateFilePath = values[0];
                String xlsFilePath = values[1];

                DBHelper dbHelper = new DBHelper();
                XLSWriter xlsWriter = new XLSWriter(xlsFilePath);

                // TODO : handle the xls Template file to generate search sql statement
                XLSTemplateHandler xlsTemplateHandler = new XLSTemplateHandler(xlsTemplateFilePath);
                xlsTemplateHandler.readTemplate();
                List<XLSTemplateItem> xlsTemplateItemList = xlsTemplateHandler.getXlsTemplateItems();

                if(xlsTemplateItemList.size() != 0) {
                    for(XLSTemplateItem oneTemplateItem : xlsTemplateItemList) {
                        xlsWriter.addOneRow2XLS(oneTemplateItem.getColumnNamesAsList());
                        dbHelper.readDataToXLSWriter(xlsWriter, oneTemplateItem);
                    }
                } else {
                    // search out all of the data in current PMMS_RESULT table.
                    xlsWriter.addTitle2XLS(dbHelper.readTableColumnNames());
                    dbHelper.readDataToXLSWriter(xlsWriter);
                }
//                xlsWriter.addTitle2XLS(dbHelper.readTableColumnNames());
//                dbHelper.readDataToXLSWriter(xlsWriter);
                xlsWriter.writeToXLS();

                logger.info("Export data Successfully");
                System.exit(0);
            }
        } catch( ParseException exp ) {
            // oops, something went wrong
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "PMMSAdmin", options );
            System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
            System.exit(1);
        }
    }
}
