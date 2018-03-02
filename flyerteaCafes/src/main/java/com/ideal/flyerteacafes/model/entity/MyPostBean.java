package com.ideal.flyerteacafes.model.entity;

/**
 * 我的评论列表
 *
 * @author fly
 */
public class MyPostBean {

    private int id;

    //我的主贴（回复）帖子id
    private int tid;

//    //用户头像url地址		如果post_type为0，则没有此字段
//    private String user_icon_url;

    //发帖用户名称		如果post_type为0，则没有此字段
    private String author;
    //发帖时间戳
//    private String dateline;
    //此贴对应的标题或摘要
    private String subject;

    private String authorid;
    private String favtimes;
    private String fid;
    private String forumname;
    private String professional;
    private String type;
    private String dbdateline;

//    //发帖所属的子模块分类
//    private String forum_name;
//
//    //此贴的最新回复数
//    private int replies;
//
//    //此贴对应的类型		0：普通贴；1普通精华；2热门贴；3：热门精华帖
//    private int post_type;
//
//    //我的帖子与回复标识 0我的帖子 1 回复
//    private int mark;

    private String flowers;

    public String getFlowers() {
        return flowers;
    }

    public void setFlowers(String flowers) {
        this.flowers = flowers;
    }

//    public int getMark() {
//        return mark;
//    }
//
//    public void setMark(int mark) {
//        this.mark = mark;
//    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

//    public String getUser_icon_url() {
//        return user_icon_url;
//    }
//
//    public void setUser_icon_url(String user_icon_url) {
//        this.user_icon_url = user_icon_url;
//    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

//    public String getDateline() {
//        return dateline;
//    }
//
//    public void setDateline(String dateline) {
//        this.dateline = dateline;
//    }

//    public String getForum_name() {
//        return forum_name;
//    }
//
//    public void setForum_name(String forum_name) {
//        this.forum_name = forum_name;
//    }
//
//    public int getReplies() {
//        return replies;
//    }
//
//    public void setReplies(int replies) {
//        this.replies = replies;
//    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
//
//    public int getPost_type() {
//        return post_type;
//    }
//
//    public void setPost_type(int post_type) {
//        this.post_type = post_type;
//    }


    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public String getFavtimes() {
        return favtimes;
    }

    public void setFavtimes(String favtimes) {
        this.favtimes = favtimes;
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

    public String getDbdateline() {
        return dbdateline;
    }

    public void setDbdateline(String dbdateline) {
        this.dbdateline = dbdateline;
    }
}
