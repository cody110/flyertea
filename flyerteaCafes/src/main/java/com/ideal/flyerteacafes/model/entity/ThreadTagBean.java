package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;

/**
 * Created by fly on 2017/11/30.
 */

public class ThreadTagBean extends BaseBean implements Serializable{

    private String tagid;
    private String type;
    private String tagname;
    private String catname;
    private String status;
    private String tagcount;
    private String description;
    private String sametagid;
    private String tagurl;
    private String taglogo;
    private String admin;
    private String tf;
    private String idf;
    private String parenttagid;
    private String sortidfiltervalue;

    public String getTagid() {
        return tagid;
    }

    public String getType() {
        return type;
    }

    public String getTagname() {
        return tagname;
    }

    public String getCatname() {
        return catname;
    }

    public String getStatus() {
        return status;
    }

    public String getTagcount() {
        return tagcount;
    }

    public String getDescription() {
        return description;
    }

    public String getSametagid() {
        return sametagid;
    }

    public String getTagurl() {
        return tagurl;
    }

    public String getTaglogo() {
        return taglogo;
    }

    public String getAdmin() {
        return admin;
    }

    public String getTf() {
        return tf;
    }

    public String getIdf() {
        return idf;
    }

    public String getParenttagid() {
        return parenttagid;
    }

    public String getSortidfiltervalue() {
        return sortidfiltervalue;
    }
}
