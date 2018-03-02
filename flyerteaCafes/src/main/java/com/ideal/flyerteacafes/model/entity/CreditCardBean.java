package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;

/**
 * 信用卡数据
 *
 * @author fly
 */
@SuppressWarnings("serial")
public class CreditCardBean implements Serializable {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
