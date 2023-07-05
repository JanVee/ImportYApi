package com.janvee.importyapi.content;

import java.util.Objects;

public class YApiProjectProperty {

    private final static String DEFAULT_URL = "";
    private final static String DEFAULT_LOCAL_URL = "http://127.0.0.1:8000";
    private final static String DEFAULT_TOKEN = "";


    private String url = DEFAULT_URL;
    private String localUrl = DEFAULT_LOCAL_URL;
    private String token = DEFAULT_TOKEN;
    public YApiProjectProperty() {

    }
    public YApiProjectProperty(String url, String token, String localUrl) {
        this.token = token;
        this.setUrl(url);
        this.setLocalUrl(localUrl);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (url.matches(".*/+$")) {
            this.url = url.replaceAll("/+$", "");
        } else {
            this.url = url;
        }
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String url) {
        if (url.matches(".*/+$")) {
            this.localUrl = url.replaceAll("/+$", "");
        } else {
            this.localUrl = url;
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(this.url, this.localUrl, this.token);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        YApiProjectProperty that = (YApiProjectProperty) obj;
        return Objects.equals(url, that.url) &&
                Objects.equals(localUrl, that.localUrl) &&
                Objects.equals(token, that.token);
    }
}
