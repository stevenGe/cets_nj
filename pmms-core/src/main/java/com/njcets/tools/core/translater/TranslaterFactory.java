package com.njcets.tools.core.translater;

import com.njcets.tools.core.settings.SettingManagement;
import com.njcets.tools.core.template.TemplateItem;

/**
 * @author gexinl
 */
public class TranslaterFactory {
    public static AbstractTranslater getTranslater(TemplateItem templateItem) {
        if(templateItem.getItemName().toUpperCase().equals(SettingManagement.COMPONENT_ATTRIBUTES_TEMPLATE)){
            return new CATranslater();
        } else if(templateItem.getItemName().toUpperCase().equals(SettingManagement.COMMON_RECORD_DATA_TEMPLATE)) {
            return new CRDTranslater();
        } else if(templateItem.getItemName().toUpperCase().equals(SettingManagement.GENERATED_ITEMS_TEMPLATE)) {
            return new GITranslater();
        } else if(templateItem.getItemName().toUpperCase().equals(SettingManagement.IMPLIED_ITEMS_TEMPLATE)) {
            return new IITranslater();
        }

        return null;
    }
}
