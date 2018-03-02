package com.ideal.flyerteacafes.model.entity;

import android.text.TextUtils;

import com.ideal.flyerteacafes.utils.DataUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fly on 2016/11/29.
 * 我的帖子
 */

public class MyThreadBean implements Serializable{

    /**
     * 是否是正常帖
     *
     * @return 是否专业模式（0 否 1 是）
     */
    public boolean isNormal() {
        return DataUtils.isNormal(professional);
    }


    private String pid;
    private String tid;
    private String fid;
    private String authorid;
    private String author;
    private String subject;
    private String dbdateline;
    private String professional;
    private String type;
    private String favtimes;
    private String flowers;
    private String forumname;
    private String replies;
    private String displayorder;
    private String digest;
    private String pushedaid;
    private String heatlevel;
    private List<Attachments> attachments;

    public String getDigest() {
        return digest;
    }

    public String getPushedaid() {
        return pushedaid;
    }

    public String getHeatlevel() {
        return heatlevel;
    }

    public String getDisplayorder() {
        return displayorder;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDbdateline() {
        return dbdateline;
    }

    public void setDbdateline(String dbdateline) {
        this.dbdateline = dbdateline;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFavtimes() {
        return favtimes;
    }

    public void setFavtimes(String favtimes) {
        this.favtimes = favtimes;
    }

    public String getFlowers() {
        return flowers;
    }

    public void setFlowers(String flowers) {
        this.flowers = flowers;
    }

    public String getForumname() {
        return forumname;
    }

    public void setForumname(String forumname) {
        this.forumname = forumname;
    }

    public List<Attachments> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachments> attachments) {
        this.attachments = attachments;
    }

    public String getReplies() {
        return replies;
    }

    public void setReplies(String replies) {
        this.replies = replies;
    }
}

