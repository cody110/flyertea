package com.ideal.flyerteacafes.model;

import java.io.Serializable;

/**
 * Created by fly on 2016/5/7.
 */
public class TypeMode implements Serializable{

    private String name;
    private String type;
    private boolean isChoose;

    public boolean isChoose() {
        return isChoose;
    }

    public void setIsChoose(boolean isChoose) {
        this.isChoose = isChoose;
    }

    public String getName() {
        return name;
    }

    public TypeMode setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TypeMode(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public TypeMode() {

    }
}
