package com.njcets.maintenance.tool.handler;

import com.njcets.tools.core.reader.AbstractFileReader;
import com.njcets.tools.core.reader.FileReaderFactory;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Serializer;

import java.io.*;
import java.util.List;

/**
 * @author gexinl
 */

public class TableFilesHandler {
    private Document doc;

    public void initilizeTableXML() {
        Element root = new Element("Tables");
        doc = new Document(root);
    }

    public void readTableFilesToXML(String tableFilesDir) {
        Element rootElement = doc.getRootElement();

        File tableFileDirFile = new File(tableFilesDir);
        if(tableFileDirFile.exists()) {
            String[] tableFiles = tableFileDirFile.list();
            for(String oneTableFile : tableFiles) {
                AbstractFileReader fileReader = FileReaderFactory.getFileReader("TXT");
                fileReader.open(tableFilesDir + File.separator + oneTableFile);
                List<String> lines = fileReader.readLines();
                if(lines != null && lines.size() != 0) {
                    rootElement.appendChild(parseFileToTableElement(oneTableFile, lines));
                }
                fileReader.close();
            }

            writeDocToXMLFile();
        } else {

        }
    }

    public void updateTableFilesToXML(String tableFilesDir) {

    }

    private Element parseFileToTableElement(String tableName ,List<String> lines) {
        Element tableElement = new Element("Table");

        // add TableName Element
        Element tableNameElement = new Element("TableName");
        tableNameElement.appendChild(tableName);
        tableElement.appendChild(tableNameElement);

        // add TableColumns Element
        Element tableColumnElement = new Element("TableColumns");
        // add Column Element
        for(int i = 0; i < lines.size(); i++) {
            String[] columnInfo = lines.get(i).split("\\t\\t");
            String columnName = columnInfo[0].trim();
            String  columnType;
            if(columnInfo.length < 2) {
                columnType = "UNKNOWN";
            } else {
                columnType = columnInfo[1].trim();
            }

            Element columnElement = new Element("Column");

            // add index attribute, start from 0
            Attribute index = new Attribute("index", String.valueOf(i + 1));
            columnElement.addAttribute(index);

            // add columnName
            Element columnNameElement = new Element("ColumnName");
            columnNameElement.appendChild(columnName);
            columnElement.appendChild(columnNameElement);

            // add columnType
            Element columnTypeElement = new Element("ColumnType");
            columnTypeElement.appendChild(columnType);
            columnElement.appendChild(columnTypeElement);

            // add current column element to table columns element
            tableColumnElement.appendChild(columnElement);
        }

        tableElement.appendChild(tableColumnElement);
        return tableElement;
    }

    private void writeDocToXMLFile() {
        File outPutFile = new File("C:\\Work\\github\\cets_nj\\out\\PMMS\\resources\\Tables.xml");
        OutputStream out = null;
        if(!outPutFile.exists()) {
            try {
                outPutFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        try {
            out = new FileOutputStream(outPutFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try {
            if(out != null) {
                Serializer serializer = new Serializer(out, "ISO-8859-1");
                serializer.setIndent(4);
                serializer.setMaxLength(64);
                serializer.write(doc);
            }
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
    }

}
