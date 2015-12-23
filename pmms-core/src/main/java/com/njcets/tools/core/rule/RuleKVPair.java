package com.njcets.tools.core.rule;

import nu.xom.Element;

/**
 * @author gexinl
 */
public class RuleKVPair {

    private String key;

    private String value;

    public RuleKVPair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public RuleKVPair(Element kvPairElement) {
         this.key = kvPairElement.getAttributeValue("value");
         this.value = kvPairElement.getValue();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
