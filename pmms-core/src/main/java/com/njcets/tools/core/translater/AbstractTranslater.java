package com.njcets.tools.core.translater;

import com.njcets.tools.core.data.ColumnMetaData;
import com.njcets.tools.core.rule.Rule;
import com.njcets.tools.core.rule.RuleKVPair;
import com.njcets.tools.core.table.TableColumn;
import com.njcets.tools.core.table.TableHandler;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author gexinl
 * translate one line a time
 */
public abstract class AbstractTranslater {
    protected Logger logger = Logger.getLogger(AbstractTranslater.class);

    protected TableHandler tableHandler;

    protected Rule rule;


    public ColumnMetaData apply(String line){
        logger.info("apply rule on line: " + line);
        ColumnMetaData columnMetaData = new ColumnMetaData();
        String[] splitLine = line.split(",");

        String columnIndex = splitLine[rule.getStartIndex() - 1].trim();
        columnMetaData.setColumnIndex(Integer.valueOf(columnIndex));

        String columnValueLength = splitLine[rule.getLengthIndex() - 1].trim();
        columnMetaData.setColumnValueLength(Integer.valueOf(columnValueLength));

        // get the column name, using while loop until find the column name
        Map<String, List<RuleKVPair>> pairs = rule.getPairs();
        String columnName = null;
        Set<String> keySet = pairs.keySet();
        Iterator<String> it = keySet.iterator();
        while(it.hasNext()) {
            String onePair = it.next();

            List<RuleKVPair> kvPairs = pairs.get(onePair);
            columnName = retrieveColumnName(splitLine, onePair, kvPairs);
            if(columnName != null) {
                columnMetaData.setColumnName(columnName);
                break;
            }
        }
        logger.info("Column Name is: " + columnMetaData.getColumnName());
        logger.info("Column Index is: " + columnMetaData.getColumnIndex());
        logger.info("Column Value Length is: " + columnMetaData.getColumnValueLength());
        return columnMetaData;
    }

    protected String retrieveColumnName(String[] line, String pairIndex, List<RuleKVPair> kvPairs) {
        String result = null;
        String[] indexArray = pairIndex.split(",");
        if(indexArray.length == 2) {

            String indexKey = indexArray[0].trim();
            String indexValue = indexArray[1].trim();

            String key = line[Integer.valueOf(indexKey) - 1].trim();
            String value = line[Integer.valueOf(indexValue) - 1].trim();

            RuleKVPair ruleKVPair = getRuleKVPairByKeyIndex(key, kvPairs);

            String tableName = ruleKVPair.getValue();


            if(tableName.equals("NOT_RELEVANT")) {
                return null;
            }

            int tableColumnIndex = Integer.valueOf(value);


            TableColumn tableColumn = tableHandler.getTableColumn(tableName, tableColumnIndex);
            result = tableColumn.getColumnName();

        }

        return result;
    }

    protected RuleKVPair getRuleKVPairByKeyIndex(String key, List<RuleKVPair> kvPairs) {
        for(RuleKVPair oneRuleKVPair : kvPairs) {
            if(oneRuleKVPair.getKey().equals(key)) {
                return oneRuleKVPair;
            }
        }
        return null;
    }

    public void setTableHandler(TableHandler tableHandler) {
        this.tableHandler = tableHandler;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }
}
