package com.janvee.importyapi.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class YApiProjectResponse implements Serializable  {
    /**
     * id
     */
    @SerializedName("_id")
    private Integer _id;
    /**
     * 名称
     */
    @SerializedName("name")
    private String name;
    /**
     * uid
     */
    @SerializedName("uid")
    private Integer uid;

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}
