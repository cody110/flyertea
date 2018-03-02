package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 资讯文章，评论
 *
 * @author fly
 */
@SuppressWarnings("serial")
public class ArticleCommentBean implements Serializable {

    // 评论用户
    private String username;
    // 评论时间戳
    private String dateline;
    // 评论内容
    private String message;

    private String face;

    private String authorid;

    private ArrayList<String> urlList;

    public ArrayList<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(ArrayList<String> urlList) {
        this.urlList = urlList;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

}
