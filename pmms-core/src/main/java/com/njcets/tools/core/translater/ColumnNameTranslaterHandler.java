package com.njcets.tools.core.translater;

import com.njcets.tools.core.data.ColumnMetaData;
import com.njcets.tools.core.rule.Rule;
import com.njcets.tools.core.rule.RuleHandler;
import com.njcets.tools.core.table.TableHandler;
import com.njcets.tools.core.template.Template;
import com.njcets.tools.core.template.TemplateHandler;
import com.njcets.tools.core.template.TemplateItem;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gexinl
 */
public class ColumnNameTranslaterHandler {
    private Logger logger = Logger.getLogger(ColumnNameTranslaterHandler.class);

    private List<ColumnMetaData> columnMetaDataList;

    private TemplateHandler templateHandler;            // handle template object parsed from template file

    private TableHandler tableHandler;                  // handle tables provided by company to hold standard table and columns

    private RuleHandler ruleHandler;                    // handle rules generated in rule xml file


    public ColumnNameTranslaterHandler() {
        this.columnMetaDataList = new ArrayList<ColumnMetaData>();
    }

    public void translate() {
        logger.info("Translate column names");
        Template template = templateHandler.getTemplate();
        List<TemplateItem> templateItemsList = template.getItems();
        for(TemplateItem oneTemplateItem : templateItemsList) {
            generateColumnNameFromTemplate(oneTemplateItem);
        }
    }

    private void generateColumnNameFromTemplate(TemplateItem templateItem) {
        AbstractTranslater columnTranslater = TranslaterFactory.getTranslater(templateItem);
        Rule rule = ruleHandler.getRuleByName(templateItem.getItemName());

        columnTranslater.setRule(rule);
        columnTranslater.setTableHandler(tableHandler);

        List<String> templateLines = templateItem.getPureColumnDefinition();
        for(String line : templateLines) {
            logger.info("Start to handle line: " + line);
            ColumnMetaData columnMetaData = columnTranslater.apply(line);
            if (!isColumnMetadataContained(columnMetaData)) {
                columnMetaDataList.add(columnMetaData);
            }
        }
    }

    public void setTemplateHandler(TemplateHandler templateHandler) {
        this.templateHandler = templateHandler;
    }

    public void setTableHandler(TableHandler tableHandler) {
        this.tableHandler = tableHandler;
    }

    public void setRuleHandler(RuleHandler ruleHandler) {
        this.ruleHandler = ruleHandler;
    }

    public List<ColumnMetaData> getColumnMetaDataList() {
        return columnMetaDataList;
    }

    public boolean isColumnMetadataContained(ColumnMetaData columnMetaData) {
        boolean isContained = false;
        if(this.columnMetaDataList != null) {
            for(ColumnMetaData oneColumnMD : this.columnMetaDataList) {
                if(oneColumnMD.getColumnName().equalsIgnoreCase(columnMetaData.getColumnName())) {
                    logger.warn("Duplicate column Name: " + oneColumnMD.getColumnName());
                    isContained = true;
                }
            }
        }
        return  isContained;
    }
}
