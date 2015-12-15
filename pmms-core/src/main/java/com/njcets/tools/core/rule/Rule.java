package com.njcets.tools.core.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gexinl
 */

public class Rule {

    private String ruleName;

    private Map<String, List<RuleKVPair>> pairs;

    private String startIndex;

    private String lengthIndex;

    public Rule(String ruleName) {
        this.ruleName = ruleName;
        pairs = new HashMap<String, List<RuleKVPair>>();
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

    public String getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(String startIndex) {
        this.startIndex = startIndex;
    }

    public String getLengthIndex() {
        return lengthIndex;
    }

    public void setLengthIndex(String lengthIndex) {
        this.lengthIndex = lengthIndex;
    }
}
