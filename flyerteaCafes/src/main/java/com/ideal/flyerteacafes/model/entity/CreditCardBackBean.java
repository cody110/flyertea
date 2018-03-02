package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;

/**
 * 信用卡积分查询，返回
 *
 * @author fly
 */
public class CreditCardBackBean implements Serializable {

    // 银行id
    private int type_id;
    // 获取积分的方式或渠道
    private String pay_type;
    // 积分提供的作者名称
    private String username;
    // 积分信息发布的时间 时间戳
    private String date;
    // 积分类型id
    private int scores;
    //银行
    private String bankname;
    //积分
    private String typename;

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

}
