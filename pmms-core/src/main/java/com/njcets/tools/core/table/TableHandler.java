package com.njcets.tools.core.table;

import com.njcets.tools.core.conf.EnvManager;
import nu.xom.*;

import java.io.File;
import java.io.IOException;
import nu.xom.Builder;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gexinl
 */
public class TableHandler {
    private Logger logger = Logger.getLogger(TableHandler.class);

    private String tableXMLFilePath;

    private Map<String, Table> tables;           // tableName-Table

    public TableHandler(String tableXMLFilePath) {
        this.tableXMLFilePath = this.tableXMLFilePath;
        this.tables = new HashMap<String, Table>();
    }

    public TableHandler() {
        this.tableXMLFilePath = EnvManager.getInstance().getPMMS_HOME() + File.separator + "resources" + File.separator + "Tables.xml";
        this.tables = new HashMap<String, Table>();
    }

    public void parseTableXML() {
        logger.info("Start to parse Table XML file: " + tableXMLFilePath);
        Document doc;
        try {
            Builder parser = new Builder();
            doc = parser.build(new File(tableXMLFilePath));

            Element rootElement = doc.getRootElement();
            Elements tableElements = rootElement.getChildElements();

            for(int i = 0; i < tableElements.size(); i++) {
                Element oneTableElement = tableElements.get(i);
                Table table = new Table(oneTableElement);
                tables.put(table.getTableName(), table);
            }
        } catch (ValidityException ex) {
            ex.printStackTrace();
            System.err.println("Invalid Table.xml file");
        } catch (ParsingException ex) {
            ex.printStackTrace();
            System.err.println("Error parsing the Table.xml file");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Could not open Table.xml file.");
        }
        logger.info("End to parse Table XML file: " + tableXMLFilePath);
    }

    // index start from 1
    public TableColumn getTableColumn(String tableName, int index) {
        TableColumn result = new TableColumn();

        if(this.tables != null) {

            if(tables.containsKey(tableName)) {

                Table table = tables.get(tableName);
                result = table.getColumnByIndex(index);
            } else {
                result = null;
            }
        } else {
            result = null;
        }

        return result;
    }

    public String getTableXMLFilePath() {
        return tableXMLFilePath;
    }

    public void setTableXMLFilePath(String tableXMLFilePath) {
        this.tableXMLFilePath = tableXMLFilePath;
    }

    public Map<String, Table> getTables() {
        return tables;
    }

    public void setTables(Map<String, Table> tables) {
        this.tables = tables;
    }
}
