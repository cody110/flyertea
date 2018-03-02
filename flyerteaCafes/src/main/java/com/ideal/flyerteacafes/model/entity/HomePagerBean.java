package com.ideal.flyerteacafes.model.entity;

/**
 * Created by fly on 2016/1/20.
 */
public class HomePagerBean {

    public static final int TAB_TYPE_HOME=0,TAB_TYPE_ARTICLE=1,TAB_TYPE_HUI=3;


    public HomePagerBean(String tabName,  String tabUrl,int tabType) {
        this.tabName = tabName;
//        this.tabOrder = tabOrder;
        this.tabUrl = tabUrl;
        this.tabType = tabType;
    }

    private String tabName;
    private int tabOrder;
    private String tabUrl;
    private int tabType;


    public int getTabType() {
        return tabType;
    }

    public void setTabType(int tabType) {
        this.tabType = tabType;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public int getTabOrder() {
        return tabOrder;
    }

    public void setTabOrder(int tabOrder) {
        this.tabOrder = tabOrder;
    }

    public String getTabUrl() {
        return tabUrl;
    }

    public void setTabUrl(String tabUrl) {
        this.tabUrl = tabUrl;
    }
}
