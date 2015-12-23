package com.njcets.tools.core.rule;

/**
 * @author gexinl
 */

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestRuleHandler {

    private RuleHandler ruleHandler;

    @Before
    public void setup() {
        this.ruleHandler = new RuleHandler("C:\\Work\\github\\cets_nj\\out\\PMMS\\resources\\PMMS-Rules.xml");
    }

    @After
    public void teardown() {

    }

    //@Test
    public void testHandle() {
        ruleHandler.parseRuleXML();
        Map<String, Rule> rules = ruleHandler.getRulesMap();
    }


}
