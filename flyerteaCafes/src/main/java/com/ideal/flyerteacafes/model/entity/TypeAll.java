package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TypeAll implements Serializable {

    private int id;
    private int cid;//代表是子标签ID
    private int mark;//0为父标签, 1为子标签
    private String typeName;
    private String value;
    private List<TypeAll> types = new ArrayList<TypeAll>();

    private List<TypeAll> diquType;//地区
    private List<TypeAll> shangjiaType;//商家

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<TypeAll> getDiquType() {
        return diquType;
    }

    public void setDiquType(List<TypeAll> diquType) {
        this.diquType = diquType;
    }

    public List<TypeAll> getShangjiaType() {
        return shangjiaType;
    }

    public void setShangjiaType(List<TypeAll> shangjiaType) {
        this.shangjiaType = shangjiaType;
    }

    public List<TypeAll> getTypes() {
        return types;
    }

    public void setTypes(List<TypeAll> types) {
        this.types = types;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


}
