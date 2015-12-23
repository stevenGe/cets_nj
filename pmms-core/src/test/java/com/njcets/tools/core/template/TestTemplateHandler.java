package com.njcets.tools.core.template;

/**
 * @author gexinl
 */

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class TestTemplateHandler {
    private TemplateHandler templateHandler;

    @Before
    public void setup() {
        this.templateHandler = new TemplateHandler("mtorecord", "C:\\Work\\github\\cets_nj\\out\\PMMS\\templateSource\\mtorecord.txt" );
    }

    @After
    public void teardown() {

    }

    //@Test
    public void testgenerateTemplate() {
        templateHandler.generateTemplate();
        System.out.println(templateHandler.getTemplate().getTemplateName());
        System.out.println(templateHandler.getTemplate().getItems().size());
    }
}
