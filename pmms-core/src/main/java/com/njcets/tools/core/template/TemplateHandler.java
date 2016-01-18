package com.njcets.tools.core.template;

import com.njcets.tools.core.reader.AbstractFileReader;
import com.njcets.tools.core.reader.FileReaderFactory;
import com.njcets.tools.core.settings.SettingManagement;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author gexinl
 */

public class TemplateHandler {
    private Logger logger = Logger.getLogger(TemplateHandler.class);

    private Template template;

    private String templateName;

    private String templateFilePath;

    public TemplateHandler(String templateName, String templateFilePath) {
        logger.info("Start to handle template file: " + templateFilePath);
        this.templateName = templateName;
        this.templateFilePath = templateFilePath;
        template = new Template(templateName);
    }

    public void generateTemplate() {
        logger.info("Generate template: " + templateName);
        List<String> lines = readTemplateFile();
        parseTemplate(lines);
    }

    private List<String> readTemplateFile() {
        AbstractFileReader templateFileReader = FileReaderFactory.getFileReader("");
        templateFileReader.open(templateFilePath);
        List<String> lines = templateFileReader.readLines();
        templateFileReader.close();
        return lines;
    }

    private void parseTemplate(List<String> lines) {
        logger.info("Start to parse line for template: " + templateFilePath);
        if(lines.contains(SettingManagement.COMMON_RECORD_DATA_TEMPLATE)) {
            logger.info("Handle COMMON_RECORD_DATA_TEMPLATE...");
            int index = lines.indexOf(SettingManagement.COMMON_RECORD_DATA_TEMPLATE);
            int columnNumber = Integer.valueOf(lines.get(index + 1));

            TemplateItem templateItem = new TemplateItem(SettingManagement.COMMON_RECORD_DATA_TEMPLATE, columnNumber);
            List<String> columnDefinition = parseColumns(index, columnNumber, lines);
            templateItem.setColumnDefinition(columnDefinition);
            template.getItems().add(templateItem);
        }

        if(lines.contains(SettingManagement.COMPONENT_ATTRIBUTES_TEMPLATE)) {
            logger.info("Handle COMPONENT_ATTRIBUTES_TEMPLATE...");
            int index = lines.indexOf(SettingManagement.COMPONENT_ATTRIBUTES_TEMPLATE);
            int columnNumber = Integer.valueOf(lines.get(index + 1));

            TemplateItem templateItem = new TemplateItem(SettingManagement.COMPONENT_ATTRIBUTES_TEMPLATE, columnNumber);
            List<String> columnDefinition = parseColumns(index, columnNumber, lines);
            templateItem.setColumnDefinition(columnDefinition);
            template.getItems().add(templateItem);
        }

        if(lines.contains(SettingManagement.GENERATED_ITEMS_TEMPLATE)) {
            logger.info("Handle GENERATED_ITEMS_TEMPLATE...");
            int index = lines.indexOf(SettingManagement.GENERATED_ITEMS_TEMPLATE);
            int columnNumber = Integer.valueOf(lines.get(index + 1));

            TemplateItem templateItem = new TemplateItem(SettingManagement.GENERATED_ITEMS_TEMPLATE, columnNumber);
            List<String> columnDefinition = parseColumns(index, columnNumber, lines);
            templateItem.setColumnDefinition(columnDefinition);
            template.getItems().add(templateItem);
        }

        if(lines.contains(SettingManagement.IMPLIED_ITEMS_TEMPLATE)) {
            logger.info("Handle IMPLIED_ITEMS_TEMPLATE...");
            int index = lines.indexOf(SettingManagement.IMPLIED_ITEMS_TEMPLATE);
            int columnNumber = Integer.valueOf(lines.get(index + 1));

            TemplateItem templateItem = new TemplateItem(SettingManagement.IMPLIED_ITEMS_TEMPLATE, columnNumber);
            List<String> columnDefinition = parseColumns(index, columnNumber, lines);
            templateItem.setColumnDefinition(columnDefinition);
            template.getItems().add(templateItem);
        }
    }

    private List<String> parseColumns(int itemIndex, int columnNumber, List<String> lines) {

       return lines.subList(itemIndex + 2, itemIndex + 2 + columnNumber);

    }

    public Template getTemplate() {
        return template;
    }
}
