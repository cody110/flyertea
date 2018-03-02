package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fly on 2016/11/25.
 */

public class ArticleContentBean implements Serializable{

    private String aid;
    private String uid;
    private String gender;
    private String has_sm;
    private String groupid;
    private String groupname;
    private String useravatar;
    private String username;
    private String subject;
    private String author;
    private String authorid;
    private String avatar;
    private String summary;
    private String tid;
    private String dbdateline;
    private String fid;
    private String forumname;
    private String subfid;
    private String subforumfname;
    private String subforumname;
    private String message;
    private String views;
    private String replies;
    private String favtimes;
    private String favid;
    private String isLike;
    private String professional;
    private String location;//位置
    private String isfriend;
    private String flowers;

    public String getFlowers() {
        return flowers;
    }

    public void setFlowers(String flowers) {
        this.flowers = flowers;
    }

    public String getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(String isfriend) {
        this.isfriend = isfriend;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * aid : 1018933
     * filesize : 932886
     * kwidth : 480
     * kheight : 640
     * swidth : 210
     * sheight : 280
     * mwidth : 750
     * mheight : 1000
     * imageurl : http://ptf.flyert.com/forum/201611/25/011921zz2jsfzzsspvfr8v.jpg
     * simageurl : http://ptf.flyert.com/forum/201611/25/011921zz2jsfzzsspvfr8v.jpg!s
     * kimageurl : http://ptf.flyert.com/forum/201611/25/011921zz2jsfzzsspvfr8v.jpg!k
     */



    private List<Attachments> attachments;

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHas_sm() {
        return has_sm;
    }

    public void setHas_sm(String has_sm) {
        this.has_sm = has_sm;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getUseravatar() {
        return useravatar;
    }

    public void setUseravatar(String useravatar) {
        this.useravatar = useravatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getDbdateline() {
        return dbdateline;
    }

    public void setDbdateline(String dbdateline) {
        this.dbdateline = dbdateline;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getForumname() {
        return forumname;
    }

    public void setForumname(String forumname) {
        this.forumname = forumname;
    }

    public String getSubfid() {
        return subfid;
    }

    public void setSubfid(String subfid) {
        this.subfid = subfid;
    }

    public String getSubforumfname() {
        return subforumfname;
    }

    public void setSubforumfname(String subforumfname) {
        this.subforumfname = subforumfname;
    }

    public String getSubforumname() {
        return subforumname;
    }

    public void setSubforumname(String subforumname) {
        this.subforumname = subforumname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getFavtimes() {
        return favtimes;
    }

    public void setFavtimes(String favtimes) {
        this.favtimes = favtimes;
    }

    public String getFavid() {
        return favid;
    }

    public void setFavid(String favid) {
        this.favid = favid;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public List<Attachments> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachments> attachments) {
        this.attachments = attachments;
    }


}
