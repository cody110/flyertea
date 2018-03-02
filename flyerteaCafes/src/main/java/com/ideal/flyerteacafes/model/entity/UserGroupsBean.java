package com.ideal.flyerteacafes.model.entity;

/**
 * Created by fly on 2017/5/23.
 */

public class UserGroupsBean {

//    groupid: "9",
//    type: "member",
//    groupname: "无卡会员",
//    creditshigher: "-999999999",
//    creditslower: "0",
//    icon: "http://ptf.flyert.com/usergroup/noCard@3x.png"

    private int groupid;
    private String type;
    private String groupname;
    private int creditshigher;
    private int creditslower;
    private String icon;


    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public int getCreditshigher() {
        return creditshigher;
    }

    public void setCreditshigher(int creditshigher) {
        this.creditshigher = creditshigher;
    }

    public int getCreditslower() {
        return creditslower;
    }

    public void setCreditslower(int creditslower) {
        this.creditslower = creditslower;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
