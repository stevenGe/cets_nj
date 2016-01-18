package com.njcets.tools.core.translater;


import com.njcets.tools.core.data.ColumnMetaData;
import com.njcets.tools.core.table.TableColumn;

/**
 * @author gexinl
 * GENERATED_ITEMS
 */
public class GITranslater extends AbstractTranslater {

    @Override
    public ColumnMetaData apply(String line) {
        logger.info("Parse GENERATED_ITEMS Column Meta Data for line: " + line);
        ColumnMetaData columnMetaData = new ColumnMetaData();
        String[] splitLine = line.split(",");

        String column_12 = splitLine[11].trim();

        if(column_12.equals("0")) {
            return super.apply(line);
        } else {
            String columnIndex = splitLine[rule.getStartIndex() - 1].trim();

            columnMetaData.setColumnIndex(Integer.valueOf(columnIndex));
            String columnValueLength = splitLine[rule.getLengthIndex() - 1].trim();

            columnMetaData.setColumnValueLength(Integer.valueOf(columnValueLength));

            // get column name from line
            String column_2 = splitLine[1].trim();
            int tableColumnIndex = Integer.valueOf(column_2);
            TableColumn tableColumn = tableHandler.getTableColumn("Table-7", tableColumnIndex);
            columnMetaData.setColumnName(tableColumn.getColumnName());
        }

        return columnMetaData;    //To change body of overridden methods use File | Settings | File Templates.
    }
}
