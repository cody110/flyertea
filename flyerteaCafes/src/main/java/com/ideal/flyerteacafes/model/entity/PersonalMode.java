package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;

/**
 * Created by Cindy on 2017/3/21.
 */
public class PersonalMode implements Serializable {

    private String tid;
    private String author;//作者
    private String content;
    private String endtime;//结束时间
    private String fromuser;
    private String groups;
    private String id;//活动id
    private String message;//活动内容
    private String note;
    private String starttime;//开始时间
    private String subject;//标题
    private String type;//活动类型
    private String url;//活动类型
    private String dateline;
    private String isnew;

    public void setIsnew(String isnew) {
        this.isnew = isnew;
    }

    public String getIsnew() {
        return isnew;
    }

    public String getTid() {
        return tid;
    }

    public String getDateline() {
        return dateline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFromuser() {
        return fromuser;
    }

    public void setFromuser(String fromuser) {
        this.fromuser = fromuser;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
