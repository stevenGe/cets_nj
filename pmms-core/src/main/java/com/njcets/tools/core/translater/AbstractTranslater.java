package com.njcets.tools.core.translater;

import com.njcets.tools.core.data.ColumnMetaData;
import com.njcets.tools.core.rule.Rule;
import com.njcets.tools.core.rule.RuleKVPair;
import com.njcets.tools.core.table.TableColumn;
import com.njcets.tools.core.table.TableHandler;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author gexinl
 * translate one line a time
 */
public abstract class AbstractTranslater {

    protected TableHandler tableHandler;

    protected Rule rule;


    public ColumnMetaData apply(String line){
        ColumnMetaData columnMetaData = new ColumnMetaData();
        String[] splitLine = line.split(",");

        String columnIndex = splitLine[rule.getStartIndex() - 1].trim();
        System.out.println("=========== columnIndex is: " + columnIndex);
        columnMetaData.setColumnIndex(Integer.valueOf(columnIndex));
        String columnValueLength = splitLine[rule.getLengthIndex() - 1].trim();
        System.out.println("=========== columnValueLength is: " + columnValueLength);
        columnMetaData.setColumnValueLength(Integer.valueOf(columnValueLength));

        // get the column name, using while loop until find the column name
        Map<String, List<RuleKVPair>> pairs = rule.getPairs();
        System.out.println("============ rule pair size is: " + pairs.size());
        String columnName = null;
        Set<String> keySet = pairs.keySet();
        Iterator<String> it = keySet.iterator();
        while(it.hasNext()) {
            String onePair = it.next();

            System.out.println("=============== Handle Pair Index: " + onePair + " ===============");

            List<RuleKVPair> kvPairs = pairs.get(onePair);

            System.out.println("=============== get KVPairs length is: " + kvPairs.size() + " ===============");

            columnName = retrieveColumnName(splitLine, onePair, kvPairs);
            if(columnName != null) {
                columnMetaData.setColumnName(columnName);
                break;
            }
        }

        return columnMetaData;
    }

    protected String retrieveColumnName(String[] line, String pairIndex, List<RuleKVPair> kvPairs) {
        String result = null;
        String[] indexArray = pairIndex.split(",");
        if(indexArray.length == 2) {
            System.out.println("=============== Index Array length is 2 ===============");
            String indexKey = indexArray[0].trim();
            String indexValue = indexArray[1].trim();

            System.out.println("========== indexKey is: " + indexKey);
            System.out.println("========== indexValue is: " + indexValue);

            String key = line[Integer.valueOf(indexKey) - 1].trim();
            String value = line[Integer.valueOf(indexValue) - 1].trim();

            System.out.println("========== key is: " + key);
            System.out.println("========== value is: " + value);

            RuleKVPair ruleKVPair = getRuleKVPairByKeyIndex(key, kvPairs);

            String tableName = ruleKVPair.getValue();
            System.out.println("========== searched tableName is: " + tableName);
            int tableColumnIndex = Integer.valueOf(value);
            System.out.println("========== searched tableColumnIndex is: " + tableColumnIndex);

            TableColumn tableColumn = tableHandler.getTableColumn(tableName, tableColumnIndex);
            result = tableColumn.getColumnName();
            System.out.println("========== searched result is: " + result);
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
