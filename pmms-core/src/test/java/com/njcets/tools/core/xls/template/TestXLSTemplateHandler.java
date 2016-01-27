package com.njcets.tools.core.xls.template;



import org.junit.Before;
import org.junit.After;
import org.junit.Test;


/**
 * @author gexinl
 */
public class TestXLSTemplateHandler {

    private XLSTemplateHandler xlsTemplateHandler;

    @Before
    public void setup() {
        this.xlsTemplateHandler = new XLSTemplateHandler("/Users/gexinlu/development/github/cets_nj/out/PMMS/xls-template/test_xls_template.json");
    }

    @After
    public void teardown() {

    }

    //@Test
    public void testReadXLSTemplate() {
        xlsTemplateHandler.readTemplate();
    }

}
