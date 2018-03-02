package com.ideal.flyerteacafes.model.entity;

/**
 * Created by fly on 2017/12/7.
 */

public class SystemMessageBean {

    private String id;
    private String authorid;
    private String author;
    private String dateline;
    private String message;
    private String numbers;
    private String lastsummary;
    private String daterange;
    private String face;
    private String note;


    public String getNote() {
        return note;
    }

    private String isnew;

    public String getIsnew() {
        return isnew;
    }

    public void setIsnew(String isnew) {
        this.isnew = isnew;
    }

    public String getId() {
        return id;
    }

    public String getAuthorid() {
        return authorid;
    }

    public String getAuthor() {
        return author;
    }

    public String getDateline() {
        return dateline;
    }

    public String getMessage() {
        return message;
    }

    public String getNumbers() {
        return numbers;
    }

    public String getLastsummary() {
        return lastsummary;
    }

    public String getDaterange() {
        return daterange;
    }

    public String getFace() {
        return face;
    }
}
