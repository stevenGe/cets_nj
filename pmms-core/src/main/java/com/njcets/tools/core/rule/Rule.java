package com.njcets.tools.core.rule;

import nu.xom.Element;
import nu.xom.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gexinl
 */

public class Rule {

    private String ruleName;

    private int columnNumber;

    private Map<String, List<RuleKVPair>> pairs;

    private int startIndex;

    private int lengthIndex;

    private int sequenceNumber;     // would be ignored

    public Rule(String ruleName) {
        this.ruleName = ruleName;
        pairs = new HashMap<String, List<RuleKVPair>>();
    }

    public Rule(Element ruleElement) {
        pairs = new HashMap<String, List<RuleKVPair>>();

        ruleName = ruleElement.getAttributeValue("name");

        Element columnNumElement = ruleElement.getFirstChildElement("columnNumber");
        columnNumber = Integer.valueOf(columnNumElement.getValue());

        Element startIndexElement = ruleElement.getFirstChildElement("start");
        startIndex = Integer.valueOf(startIndexElement.getValue());

        Element lengthIndexElement = ruleElement.getFirstChildElement("length");
        lengthIndex = Integer.valueOf(lengthIndexElement.getValue());

        Element columnNameElement = ruleElement.getFirstChildElement("columnName");
        Element pairsElement = columnNameElement.getFirstChildElement("pairs");
        Elements pairElements = pairsElement.getChildElements();

        for(int i = 0; i < pairElements.size(); i++) {
            List<RuleKVPair> ruleKVPairList = new ArrayList<RuleKVPair>();
            Element onePairElement = pairElements.get(i);
            String onePairType = onePairElement.getAttributeValue("type");

            if(onePairType != null ) {
                String onePairName = onePairElement.getAttributeValue("index");
                Elements KVElements = onePairElement.getChildElements();

                for(int j = 0; j < KVElements.size(); j++) {
                    Element oneKVElement = KVElements.get(j);
                    RuleKVPair ruleKVPair = new RuleKVPair(oneKVElement);
                    ruleKVPairList.add(ruleKVPair);
                }

                pairs.put(onePairName, ruleKVPairList);
            }


        }

    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Map<String, List<RuleKVPair>> getPairs() {
        return pairs;
    }

    public void setPairs(Map<String, List<RuleKVPair>> pairs) {
        this.pairs = pairs;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getLengthIndex() {
        return lengthIndex;
    }

    public void setLengthIndex(int lengthIndex) {
        this.lengthIndex = lengthIndex;
    }
}
