package com.njcets.tools.core.rule;

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
