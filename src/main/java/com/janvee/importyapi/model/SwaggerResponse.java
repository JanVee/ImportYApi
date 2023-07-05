package com.janvee.importyapi.model;

import java.io.Serializable;
import java.util.Map;

public class SwaggerResponse implements Serializable {
    //  OpenApi    interface{}            `json:"openapi"`
    //	Components interface{}            `json:"components"`
    //	Info       interface{}            `json:"info"`
    //	Paths      map[string]interface{} `json:"paths"`

    private String openapi;
    private Map<String, Object> components;
    private Map<String, Object> info;
    private Map<String, Object> paths;

    public String getOpenapi() {
        return openapi;
    }

    public void setOpenapi(String openapi) {
        this.openapi = openapi;
    }

    public Map<String, Object> getComponents() {
        return components;
    }

    public void setComponents(Map<String, Object> components) {
        this.components = components;
    }

    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }

    public Map<String, Object> getPaths() {
        return paths;
    }

    public void setPaths(Map<String, Object> paths) {
        this.paths = paths;
    }

    public String toString() {
        return "SwaggerResponse{openapi='" + openapi + "', info=" + info + "}";
    }
}
