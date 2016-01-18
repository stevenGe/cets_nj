package com.njcets.tools.core.translater;


import com.njcets.tools.core.data.ColumnMetaData;
import com.njcets.tools.core.table.TableColumn;

/**
 * @author gexinl
 * COMPONENT_ATTRIBUTES
 */

public class CATranslater extends AbstractTranslater {
    @Override
    public ColumnMetaData apply(String line) {
        logger.info("Parse COMPONENT_ATTRIBUTES Column Meta Data for line: " + line);
        ColumnMetaData columnMetaData = new ColumnMetaData();
        String[] splitLine = line.split(",");

        String columnIndex = splitLine[rule.getStartIndex() - 1].trim();

        columnMetaData.setColumnIndex(Integer.valueOf(columnIndex));
        String columnValueLength = splitLine[rule.getLengthIndex() - 1].trim();

        columnMetaData.setColumnValueLength(Integer.valueOf(columnValueLength));

        // get column name from line
        String column_11 = splitLine[10].trim();
        if(column_11.equals("0")) {
            return super.apply(line);
        } else if(column_11.equals("1")) {
            // Big edmon --> GCP --> Table-3
            String column_2 = splitLine[1].trim();
            int tableColumnIndex = Integer.valueOf(column_2);
            TableColumn tableColumn = tableHandler.getTableColumn("Table-3", tableColumnIndex);
            columnMetaData.setColumnName(tableColumn.getColumnName());
        } else if (column_11.equals("2")) {
            // small edmon --> RCP --> Table-6
            String column_2 = splitLine[1].trim();
            int tableColumnIndex = Integer.valueOf(column_2);
            TableColumn tableColumn = tableHandler.getTableColumn("Table-6", tableColumnIndex);
            columnMetaData.setColumnName(tableColumn.getColumnName());
        }

        return columnMetaData;
    }
}
