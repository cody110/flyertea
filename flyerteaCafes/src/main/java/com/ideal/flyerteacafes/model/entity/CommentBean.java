package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 评论列表
 *
 * @author fly
 */
public class CommentBean implements Serializable{


    private String pid;
    private String fid;
    private String tid;
    private String author;
    private String authorid;
    private String dbdateline;
    private String groupid;
    private String subject;
    private String toauthor;
    private String quote;
    private String message;
    private String quotemsg;
    private String quoteid;
    private String authortitle;
    private String avatar;
    private String favtimes;
    private String has_sm;
    private String isfriend;
    private String forumname;
    private String subforumname;
    private String views;
    private String replies;
    private String flowers;
    private String islike;
    private String favid;
    private String gender;
    private String tomessage;
    private String isbest;
    private String isexcellent;
    private List<Attachments> attachments;

    public String getIsbest() {
        return isbest;
    }

    public void setIsbest(String isbest) {
        this.isbest = isbest;
    }

    public String getIsexcellent() {
        return isexcellent;
    }

    public void setIsexcellent(String isexcellent) {
        this.isexcellent = isexcellent;
    }

    public String getTomessage() {
        return tomessage;
    }

    public void setTomessage(String tomessage) {
        this.tomessage = tomessage;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public String getDbdateline() {
        return dbdateline;
    }

    public void setDbdateline(String dbdateline) {
        this.dbdateline = dbdateline;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getToauthor() {
        return toauthor;
    }

    public void setToauthor(String toauthor) {
        this.toauthor = toauthor;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getQuotemsg() {
        return quotemsg;
    }

    public void setQuotemsg(String quotemsg) {
        this.quotemsg = quotemsg;
    }

    public String getQuoteid() {
        return quoteid;
    }

    public void setQuoteid(String quoteid) {
        this.quoteid = quoteid;
    }

    public String getAuthortitle() {
        return authortitle;
    }

    public void setAuthortitle(String authortitle) {
        this.authortitle = authortitle;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFavtimes() {
        return favtimes;
    }

    public void setFavtimes(String favtimes) {
        this.favtimes = favtimes;
    }

    public String getHas_sm() {
        return has_sm;
    }

    public void setHas_sm(String has_sm) {
        this.has_sm = has_sm;
    }

    public String getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(String isfriend) {
        this.isfriend = isfriend;
    }

    public String getForumname() {
        return forumname;
    }

    public void setForumname(String forumname) {
        this.forumname = forumname;
    }

    public String getSubforumname() {
        return subforumname;
    }

    public void setSubforumname(String subforumname) {
        this.subforumname = subforumname;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getReplies() {
        return replies;
    }

    public void setReplies(String replies) {
        this.replies = replies;
    }

    public String getFlowers() {
        return flowers;
    }

    public void setFlowers(String flowers) {
        this.flowers = flowers;
    }

    public String getIslike() {
        return islike;
    }

    public void setIslike(String islike) {
        this.islike = islike;
    }

    public String getFavid() {
        return favid;
    }

    public void setFavid(String favid) {
        this.favid = favid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Attachments> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachments> attachments) {
        this.attachments = attachments;
    }
}
