package com.njcets.tools.core;

import com.njcets.tools.core.data.ColumnMetaData;
import com.njcets.tools.core.reader.AbstractFileReader;
import com.njcets.tools.core.reader.FileReaderFactory;
import com.njcets.tools.core.rule.Rule;
import com.njcets.tools.core.rule.RuleHandler;
import com.njcets.tools.core.table.Table;
import com.njcets.tools.core.table.TableColumn;
import com.njcets.tools.core.table.TableHandler;
import com.njcets.tools.core.template.Template;
import com.njcets.tools.core.template.TemplateHandler;
import com.njcets.tools.core.template.TemplateItem;
import com.njcets.tools.core.translater.ColumnNameTranslaterHandler;

import java.util.List;
import java.util.Map;

/**
 * @author gexinl
 */
public class MainTranslateColumn {

    public static void main(String[] args) {


        // parse Template File
        TemplateHandler templateHandler  = new TemplateHandler("mtorecord", "C:\\Work\\github\\cets_nj\\out\\PMMS\\templateSource\\mtorecord.txt" );
        templateHandler.generateTemplate();
        Template template = templateHandler.getTemplate();
//        System.out.println(template.getTemplateName());

        List<TemplateItem> templateItems = template.getItems();
        for(TemplateItem oneTemplateItem : templateItems) {
//            System.out.println(oneTemplateItem.getItemName());
            List<String> columnDefnitions = oneTemplateItem.getColumnDefinition();
            for(String oneColumnDef : columnDefnitions) {
//                System.out.println(oneColumnDef);
            }
        }


        // parse Table File
        TableHandler tableHandler = new TableHandler("C:/Work/github/cets_nj/out/PMMS/resources/Tables.xml");
        tableHandler.parseTableXML();
        Map<String, Table> tables = tableHandler.getTables();
//        System.out.println(tables.size());
        TableColumn tableColumn = tableHandler.getTableColumn("Table67", 1);
//        System.out.println(tableColumn.getColumnName());


        // parse Rule File
        RuleHandler ruleHandler = new RuleHandler("C:\\Work\\github\\cets_nj\\out\\PMMS\\resources\\PMMS-Rules.xml");
        ruleHandler.parseRuleXML();
        Map<String, Rule> rules = ruleHandler.getRulesMap();
//        System.out.println("========== Rule's size is: " + rules.size());

        // read lines from .b file
        // TODO: input should be a directory not a regular file
        AbstractFileReader fileReader = FileReaderFactory.getFileReader("TXT");
        fileReader.open("C:\\Work\\github\\cets_nj\\out\\PMMS\\material_files\\scl025001.b1");
        List<String> lines = fileReader.readLines();
        fileReader.close();
//        System.out.println(lines.size());

        // translate column name
        ColumnNameTranslaterHandler columnNameTranslater = new ColumnNameTranslaterHandler();

        columnNameTranslater.setRuleHandler(ruleHandler);
        columnNameTranslater.setTableHandler(tableHandler);
        columnNameTranslater.setTemplateHandler(templateHandler);

        columnNameTranslater.translate();
        List<ColumnMetaData> columnsMetaDataList = columnNameTranslater.getColumnMetaDataList();
//        System.out.println(columnsMetaDataList.size());
        for(ColumnMetaData columnMetaData : columnsMetaDataList) {
            System.out.println(columnMetaData.getColumnName());
        }

        // generate MaterialTable Object

    }
}
