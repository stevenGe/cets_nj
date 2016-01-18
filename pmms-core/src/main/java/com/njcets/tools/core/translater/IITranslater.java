package com.njcets.tools.core.translater;


import com.njcets.tools.core.data.ColumnMetaData;
import com.njcets.tools.core.table.TableColumn;

/**
 * @author gexinl
 * IMPLIED_ITEMS
 */
public class IITranslater extends AbstractTranslater {
    @Override
    public ColumnMetaData apply(String line) {
        logger.info("Parse IMPLIED_ITEMS Column Meta Data for line: " + line);

        ColumnMetaData columnMetaData = new ColumnMetaData();
        String[] splitLine = line.split(",");

        String columnIndex = splitLine[rule.getStartIndex() - 1].trim();

        columnMetaData.setColumnIndex(Integer.valueOf(columnIndex));
        String columnValueLength = splitLine[rule.getLengthIndex() - 1].trim();

        columnMetaData.setColumnValueLength(Integer.valueOf(columnValueLength));

        // get column name from line
        String column_10 = splitLine[9].trim();
        if(column_10.equals("0")) {
            String column_11 = splitLine[10].trim();
            if(column_11.equals("-1")) {
                int tableColumnIndex = Integer.valueOf(splitLine[8].trim());
                TableColumn tableColumn = tableHandler.getTableColumn("Table202", tableColumnIndex);
                columnMetaData.setColumnName(tableColumn.getColumnName());
            } else {
                String column_1 = splitLine[0].trim();
                int tableColumnIndex = Integer.valueOf(column_11);
                if(column_1.equals("2")) {
                    TableColumn tableColumn = tableHandler.getTableColumn("Table-4", tableColumnIndex);
                    columnMetaData.setColumnName(tableColumn.getColumnName());
                } else if(column_1.equals("3")) {
                    TableColumn tableColumn = tableHandler.getTableColumn("Table-5", tableColumnIndex);
                    columnMetaData.setColumnName(tableColumn.getColumnName());
                } else {    // 1 or 4
                    TableColumn tableColumn = tableHandler.getTableColumn("Table-3", tableColumnIndex);
                    columnMetaData.setColumnName(tableColumn.getColumnName());
                }
            }
        } else if(column_10.equals("1")) {
            String tableColumnIndexStr = splitLine[2].trim();
            int tableColumnIndex = Integer.valueOf(tableColumnIndexStr);
            TableColumn tableColumn = tableHandler.getTableColumn("Table-3", tableColumnIndex);
            columnMetaData.setColumnName(tableColumn.getColumnName());
        }

        return columnMetaData;
    }
}
