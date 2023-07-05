package com.janvee.importyapi.content;

import org.apache.commons.lang3.StringUtils;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

public class DefaultYApiPropertyXmlConvert implements YApiPropertyXmlConvert<YApiProjectProperty> {

    private static final String KEY_URL = "url";
    private static final String KEY_LOCAL_URL = "local-url";
    private static final String KEY_TOKEN = "token";

    @Override
    public Element serialize(@NotNull YApiProjectProperty property) {
        String url = property.getUrl();
        String localUrl = property.getLocalUrl();
        String token = property.getToken();
        Element element = new Element("importYApi");
        if (StringUtils.isNotBlank(url)) {
            element.setAttribute(KEY_URL, url);
        }
        if (StringUtils.isNotBlank(localUrl)) {
            element.setAttribute(KEY_LOCAL_URL, localUrl);
        }
        if (StringUtils.isNotBlank(token)) {
            element.setAttribute(KEY_TOKEN, token);
        }
        return element;
    }

    @Override
    public YApiProjectProperty deserialize(@NotNull Element element) {
        YApiProjectProperty property = new YApiProjectProperty();
        String url = element.getAttributeValue(KEY_URL);
        if (StringUtils.isNotBlank(url)) {
            property.setUrl(url);
        }

        String localUrl = element.getAttributeValue(KEY_LOCAL_URL);
        if (StringUtils.isNotBlank(localUrl)) {
            property.setLocalUrl(localUrl);
        }

        String token = element.getAttributeValue(KEY_TOKEN);
        if (StringUtils.isNotBlank(token)) {
            property.setToken(token);
        }

        return property;
    }
}
