package com.njcets.tools.core.template;

import com.njcets.tools.core.reader.AbstractFileReader;
import com.njcets.tools.core.reader.FileReaderFactory;
import com.njcets.tools.core.settings.SettingManagement;

import java.util.List;

/**
 * @author gexinl
 */

public class TemplateHandler {

    private String templateName;

    private String templateFilePath;

    public TemplateHandler(String templateName, String templateFilePath) {
        this.templateName = templateName;
        this.templateFilePath = templateFilePath;
    }

    public Template generateTemplate() {
        List<String> lines = readTemplateFile();
        return parseTemplate(lines);
    }

    private List<String> readTemplateFile() {
        AbstractFileReader templateFileReader = FileReaderFactory.getFileReader("");
        templateFileReader.open(templateFilePath);
        List<String> lines = templateFileReader.readLines();
        templateFileReader.close();
        return lines;
    }

    private Template parseTemplate(List<String> lines) {
        Template template = new Template(this.templateName);
        if(lines.contains(SettingManagement.COMMON_RECORD_DATA_TEMPLATE)) {
            int index = lines.indexOf(SettingManagement.COMMON_RECORD_DATA_TEMPLATE);
            int columnNumber = Integer.valueOf(lines.get(index + 1));

            TemplateItem templateItem = new TemplateItem(SettingManagement.COMMON_RECORD_DATA_TEMPLATE, columnNumber);
            List<String> columnDefinition = parseColumns(index, columnNumber, lines);
            templateItem.setColumnDefinition(columnDefinition);
            template.getItems().add(templateItem);
        }

        if(lines.contains(SettingManagement.COMPONENT_ATTRIBUTES_TEMPLATE)) {
            int index = lines.indexOf(SettingManagement.COMPONENT_ATTRIBUTES_TEMPLATE);
            int columnNumber = Integer.valueOf(lines.get(index + 1));

            TemplateItem templateItem = new TemplateItem(SettingManagement.COMPONENT_ATTRIBUTES_TEMPLATE, columnNumber);
            List<String> columnDefinition = parseColumns(index, columnNumber, lines);
            templateItem.setColumnDefinition(columnDefinition);
            template.getItems().add(templateItem);
        }

        if(lines.contains(SettingManagement.GENERATED_ITEMS_TEMPLATE)) {
            int index = lines.indexOf(SettingManagement.GENERATED_ITEMS_TEMPLATE);
            int columnNumber = Integer.valueOf(lines.get(index + 1));

            TemplateItem templateItem = new TemplateItem(SettingManagement.GENERATED_ITEMS_TEMPLATE, columnNumber);
            List<String> columnDefinition = parseColumns(index, columnNumber, lines);
            templateItem.setColumnDefinition(columnDefinition);
            template.getItems().add(templateItem);
        }

        if(lines.contains(SettingManagement.IMPLIED_ITEMS_TEMPLATE)) {
            int index = lines.indexOf(SettingManagement.IMPLIED_ITEMS_TEMPLATE);
            int columnNumber = Integer.valueOf(lines.get(index + 1));

            TemplateItem templateItem = new TemplateItem(SettingManagement.IMPLIED_ITEMS_TEMPLATE, columnNumber);
            List<String> columnDefinition = parseColumns(index, columnNumber, lines);
            templateItem.setColumnDefinition(columnDefinition);
            template.getItems().add(templateItem);
        }

        return template;
    }

    private List<String> parseColumns(int itemIndex, int columnNumber, List<String> lines) {

        List<String> result = lines.subList(itemIndex + 2, itemIndex + 2 + columnNumber);

        return result;
    }
}
