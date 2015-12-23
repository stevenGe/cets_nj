package com.njcets.tools.core.rule;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import nu.xom.*;

/**
 * @author gexinl
 * This Handler handle the Rule XML file
 */
public class RuleHandler {

    private String ruleXMLFilePath;

    private Map<String, Rule> rulesMap;

    public RuleHandler(String ruleXMLFilePath) {
        this.ruleXMLFilePath = ruleXMLFilePath;
        this.rulesMap = new HashMap<String, Rule>();
    }

    public void parseRuleXML() {
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
    }

    public String getRuleXMLFilePath() {
        return ruleXMLFilePath;
    }

    public Map<String, Rule> getRulesMap() {
        return rulesMap;
    }
}
