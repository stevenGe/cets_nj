package com.njcets.tools.core.rule;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.njcets.tools.core.conf.EnvManager;
import nu.xom.*;
import org.apache.log4j.Logger;

/**
 * @author gexinl
 * This Handler handle the Rule XML file
 */
public class RuleHandler {
    private Logger logger = Logger.getLogger(RuleHandler.class);

    private String ruleXMLFilePath;

    private Map<String, Rule> rulesMap;

    public RuleHandler(String ruleXMLFilePath) {
        this.ruleXMLFilePath = ruleXMLFilePath;
        this.rulesMap = new HashMap<String, Rule>();
    }

    public RuleHandler() {
        this.ruleXMLFilePath = EnvManager.getInstance().getPMMS_HOME() + File.separator + "resources" + File.separator + "PMMS-Rules.xml";
        this.rulesMap = new HashMap<String, Rule>();
    }

    public void parseRuleXML() {
        logger.info("Start to parse Rule XML file: " + ruleXMLFilePath);
        Document doc;

        try {
            Builder parser = new Builder();
            doc = parser.build(new File(ruleXMLFilePath));

            Element rootElement = doc.getRootElement();
            Elements ruleElements = rootElement.getChildElements();

            for(int i = 0; i < ruleElements.size(); i++) {
                Element oneRuleElement = ruleElements.get(i);
                Rule rule = new Rule(oneRuleElement);
                rulesMap.put(rule.getRuleName(), rule);
            }
        } catch (ValidityException ex) {
            ex.printStackTrace();
            System.err.println("Invalid Rules.xml file");
        } catch (ParsingException ex) {
            ex.printStackTrace();
            System.err.println("Error parsing the Rules.xml file");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Could not open Rules.xml file.");
        }
        logger.info("End to parse Rule XML file: " + ruleXMLFilePath);
    }

    public String getRuleXMLFilePath() {
        return ruleXMLFilePath;
    }

    public Map<String, Rule> getRulesMap() {
        return rulesMap;
    }

    public Rule getRuleByName(String ruleName) {
        return this.rulesMap.get(ruleName);
    }
}
