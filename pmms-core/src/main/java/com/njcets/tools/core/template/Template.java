package com.njcets.tools.core.template;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gexinl
 * Bean class for template
 */

public class Template {

    private String templateName;

    private List<TemplateItem> items;

    public Template(String templateName) {
        this.templateName = templateName;
        this.items = new ArrayList<TemplateItem>();
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public List<TemplateItem> getItems() {
        return items;
    }

    public void setItems(List<TemplateItem> items) {
        this.items = items;
    }
}
