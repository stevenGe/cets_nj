package com.njcets.tools.core.xls.template;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gexinl
 */
public class XLSTemplateHandler {
    private Logger logger = Logger.getLogger(XLSTemplateHandler.class);

    private String xlsTemplateFilePath;

    private List<XLSTemplateItem> xlsTemplateItems;

    public XLSTemplateHandler(String xlsTemplateFilePath) {
        this.xlsTemplateFilePath = xlsTemplateFilePath;
        this.xlsTemplateItems = new ArrayList<XLSTemplateItem>();
    }

    public void readTemplate() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(
                DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            //Convert object to JSON string and save into file directly
            logger.info("Start to handle xls template file: " + xlsTemplateFilePath);
            XLSTemplateContainer templateContainer = mapper.readValue(new File(xlsTemplateFilePath), XLSTemplateContainer.class);
            if(templateContainer.getTemplateItems() != null && templateContainer.getTemplateItems().size() != 0) {   //  search the template item sql statement one by one
                this.xlsTemplateItems = templateContainer.getTemplateItems();
            } else {    // search out all of the records
                logger.warn("There is no xls template defined in template file: " + this.xlsTemplateFilePath);
            }
        }catch (JsonGenerationException e) {
            logger.error("Failed to parse xls template file.");
            logger.error(e.getMessage());
        } catch (JsonMappingException e) {
            logger.error("Failed to map xls template file.");
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error("Failed to map xls template file.");
            logger.error(e.getMessage());
        }
    }

    public String getXlsTemplateFilePath() {
        return xlsTemplateFilePath;
    }

    public List<XLSTemplateItem> getXlsTemplateItems() {
        return xlsTemplateItems;
    }
}