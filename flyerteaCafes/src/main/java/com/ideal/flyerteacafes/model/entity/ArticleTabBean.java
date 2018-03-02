package com.ideal.flyerteacafes.model.entity;

import java.util.List;

/**
 * Created by fly on 2016/1/20.
 */
public class ArticleTabBean {

    private int id;
    private int aid;
    private String title;
    private String author;
    private String summary;
    private String pic;
    private String dateline;
    private int catid;
    private String cat_name;
    private String comment_qty;
    private String views;
    private String tid;
    private String type;//区分类别
    private int time;//存储数据的时间
    private String begindateline;
    private String enddateline;
    private List<SortoptionsBean> sortoptions;


    public List<SortoptionsBean> getSortoptions() {
        return sortoptions;
    }

    public String getBegindateline() {
        return begindateline;
    }

    public String getEnddateline() {
        return enddateline;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getComment_qty() {
        return comment_qty;
    }

    public void setComment_qty(String comment_qty) {
        this.comment_qty = comment_qty;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }


    public static class SortoptionsBean {
        private String identifier;
        private String sortid;
        private String sorttitle;
        private String title;

        public String getIdentifier() {
            return identifier;
        }

        public String getSortid() {
            return sortid;
        }

        public String getSorttitle() {
            return sorttitle;
        }

        public String getTitle() {
            return title;
        }
    }

}
