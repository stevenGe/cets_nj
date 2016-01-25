package com.njcets.tools.core.pmmswriter;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gexinl
 * This class write content to a xls file (Excel '97(-2007) file format)
 */

public class XLSWriter {
    private Logger logger = Logger.getLogger(XLSWriter.class);

    private String xlsFilePath;

    private Workbook workbook;

    private Sheet sheet;

    private int rowIndex = 0;

    public XLSWriter(String xlsFilePath) {
        this.xlsFilePath = xlsFilePath;
        this.workbook = new HSSFWorkbook();
        this.sheet = workbook.createSheet("PMMS_RESULT");
    }

    public void addOneRow2XLS(List<String> rowColumnValues) {
        Row row = sheet.createRow((short)rowIndex);
        for(int i = 0; i < rowColumnValues.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(rowColumnValues.get(i));
        }
        rowIndex++;
    }

    public void addTitle2XLS(List<String> titleColumnNames) {
        Row row = sheet.createRow((short)0);                // The first row is the title
        for(int i = 0; i < titleColumnNames.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(titleColumnNames.get(i));
        }

    }
//
//    public void addRowContent2XLS(List<String> rowColumnValues) {
//        Row row = sheet.createRow((short)rowIndex);                // The first row is the title
//        for(int i = 0; i < rowColumnValues.size(); i++) {
//            Cell cell = row.createCell(i);
//            cell.setCellValue(rowColumnValues.get(i));
//        }
//        rowIndex++;
//    }

    public void writeToXLS() {
        logger.info("Write rows to XLS file: " + xlsFilePath);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(xlsFilePath);
            workbook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
