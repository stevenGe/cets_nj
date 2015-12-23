package com.njcets.tools.core.translater;

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

    private List<String> columnNameList;

    private TemplateHandler templateHandler;            // handle template object parsed from template file

    private TableHandler tableHandler;                  // handle tables provided by company to hold standard table and columns

    private RuleHandler ruleHandler;                    // handle rules generated in rule xml file


    public ColumnNameTranslaterHandler() {
        this.columnNameList = new ArrayList<String>();
    }

    public void translate() {
        Template template = templateHandler.getTemplate();
        List<TemplateItem> templateItemsList = template.getItems();
        for(TemplateItem oneTemplateItem : templateItemsList) {
            generateColumnNameFromTemplate(oneTemplateItem);
        }
    }

    private void generateColumnNameFromTemplate(TemplateItem templateItem) {
        // TODO: Use Translate Factory to handle the translate

        AbstractTranslater columnTranslater = TranslaterFactory.getTranslater(templateItem);
        List<String> templateLines = templateItem.getPureColumnDefinition();

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
}
