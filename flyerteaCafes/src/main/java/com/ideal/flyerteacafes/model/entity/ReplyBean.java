package com.ideal.flyerteacafes.model.entity;

/**
 * 鲜花
 *
 * @author fly
 */
public class ReplyBean {

    // 回复时间
    private String dateline;
    // 回复内容
    private String message;
    // 用户
    private String author;
    // 用户头像
    private String face;

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }


}
