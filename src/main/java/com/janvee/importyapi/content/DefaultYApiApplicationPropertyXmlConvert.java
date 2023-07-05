package com.janvee.importyapi.content;

import org.apache.commons.lang3.StringUtils;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

public class DefaultYApiApplicationPropertyXmlConvert implements
        YApiPropertyXmlConvert<YApiApplicationProperty> {

    private static final String KEY_VERSION = "version";

    @Override
    public Element serialize(@NotNull YApiApplicationProperty property) {
        Element element = new Element("app-import-to-yApi");
        String version = property.getVersion();
        if (StringUtils.isNotBlank(version)) {
            element.setAttribute(KEY_VERSION, version);
        }
        return element;
    }

    @Override
    public YApiApplicationProperty deserialize(@NotNull Element element) {
        YApiApplicationProperty property = new YApiApplicationProperty();
        String version = element.getAttributeValue(KEY_VERSION);
        if (StringUtils.isNotBlank(version)) {
            property.setVersion(version);
        }
        return property;
    }
}
