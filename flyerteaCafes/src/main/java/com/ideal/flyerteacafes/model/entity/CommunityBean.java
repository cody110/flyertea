package com.ideal.flyerteacafes.model.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Cindy on 2016/11/15.
 */
@Table(name = "CommunityBean")
public class CommunityBean implements Serializable {

    private List<CommunitySubBean> subforums;
    @Column(name = "fid")
    private String fid;//: "19",
    @Column(name = "type")
    private String type;//: "group",
    @Column(name = "parentfid")
    private String parentfid;//: "0",
    @Column(name = "themecolor")
    private String themecolor;//: "0x2993FB",
    @Column(name = "icon")
    private String icon;//: "",
    @Column(name = "name")
    private String name;//: "酒店常客"

    private Map<String, String> adv;

    public Map<String, String> getAdv() {
        return adv;
    }

    /**
     * 本地判断逻辑 ，点击展开，显示全部
     */
    private boolean showAll = false;

    public boolean isShowAll() {
        return showAll;
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }

    public List<CommunitySubBean> getSubforums() {
        return subforums;
    }

    public void setSubforums(List<CommunitySubBean> subforums) {
        this.subforums = subforums;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getParentfid() {
        return parentfid;
    }

    public void setParentfid(String parentfid) {
        this.parentfid = parentfid;
    }

    public String getThemecolor() {
        return themecolor;
    }

    public void setThemecolor(String themecolor) {
        this.themecolor = themecolor;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
