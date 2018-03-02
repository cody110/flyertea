package com.ideal.flyerteacafes.model.entity;

/**
 * 咨询模块
 *
 * @author fly
 */
public class ArticleBean extends BaseBean {

    private int id;
    //资讯文章id
    private int aid;
    //资讯文章对应的logo url地址
    private String logo;
    //资讯文章标题
    private String title;
    //资讯文章对应的摘要
    private String summary;
    //资讯文章发布的时间戳
    private String dateline;

    private int tid;

    private String comment_qty;//回复数
    private String cat_name;//类型

    private String views;

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getComment_qty() {
        return empty(comment_qty);
    }

    public void setComment_qty(String comment_qty) {
        this.comment_qty = comment_qty;
    }

    public String getCat_name() {
        return empty(cat_name);
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getLogo() {
        return empty(logo);
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return empty(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return empty(summary);
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDateline() {
        return empty(dateline);
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    @Override
    public String toString() {
        return "ArticleBean [aid=" + aid + ", logo=" + logo + ", title="
                + title + ", summary=" + summary + ", dateline=" + dateline
                + "]";
    }

}
