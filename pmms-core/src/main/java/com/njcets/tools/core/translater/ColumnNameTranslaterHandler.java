package com.njcets.tools.core.translater;

import com.njcets.tools.core.data.ColumnMetaData;
import com.njcets.tools.core.rule.Rule;
import com.njcets.tools.core.rule.RuleHandler;
import com.njcets.tools.core.table.TableHandler;
import com.njcets.tools.core.template.Template;
import com.njcets.tools.core.template.TemplateHandler;
import com.njcets.tools.core.template.TemplateItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gexinl
 */
public class ColumnNameTranslaterHandler {

    private List<ColumnMetaData> columnMetaDataList;

    private TemplateHandler templateHandler;            // handle template object parsed from template file

    private TableHandler tableHandler;                  // handle tables provided by company to hold standard table and columns

    private RuleHandler ruleHandler;                    // handle rules generated in rule xml file


    public ColumnNameTranslaterHandler() {
        this.columnMetaDataList = new ArrayList<ColumnMetaData>();
    }

    public void translate() {
        Template template = templateHandler.getTemplate();
        List<TemplateItem> templateItemsList = template.getItems();
        for(TemplateItem oneTemplateItem : templateItemsList) {
            generateColumnNameFromTemplate(oneTemplateItem);
        }
    }

    private void generateColumnNameFromTemplate(TemplateItem templateItem) {
        AbstractTranslater columnTranslater = TranslaterFactory.getTranslater(templateItem);
        System.out.println("=========== template item name is: " + templateItem.getItemName());
        Rule rule = ruleHandler.getRuleByName(templateItem.getItemName());
        System.out.println("========== Current rule is: " + rule.getRuleName());
        columnTranslater.setRule(rule);
        columnTranslater.setTableHandler(tableHandler);

        List<String> templateLines = templateItem.getPureColumnDefinition();
        for(String line : templateLines) {
            System.out.println("========= Start to translate line: " + line);
            ColumnMetaData columnMetaData = columnTranslater.apply(line);
            columnMetaDataList.add(columnMetaData);
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
}
