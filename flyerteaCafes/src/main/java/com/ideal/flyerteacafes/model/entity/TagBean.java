package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;

/**
 * Created by fly on 2017/10/18.
 */

public class TagBean implements Serializable{

    private String tagid;
    private String type;
    private String tagname;
    private boolean select;

    public String getTagid() {
        return tagid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
