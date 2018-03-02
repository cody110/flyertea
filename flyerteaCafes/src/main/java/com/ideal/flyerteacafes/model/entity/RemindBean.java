package com.ideal.flyerteacafes.model.entity;

public class RemindBean extends BaseBean {

    private int id;
    private String face;//头像
    private String note;//提醒的内容
    private String dateline;
    private String type;//post为一个帖子 at表示在主题中提到了你 pcomment为点评了帖子 system为系统提醒
    private String isread;
    private String author;//提醒用户头像url地址
    private int uid;//提醒用户的id
    private int from_id;//帖子id
    private String from_idtype;//直播

    public String getFrom_idtype() {
        return from_idtype;
    }

    public void setFrom_idtype(String from_idtype) {
        this.from_idtype = from_idtype;
    }

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsread() {
        return isread;
    }

    public void setIsread(String isread) {
        this.isread = isread;
    }


}
