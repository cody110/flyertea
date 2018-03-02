package com.ideal.flyerteacafes.model.entity;

/**
 * 收藏列表
 *
 * @author fly
 */
public class CollectionBean {

    // 我收藏的帖子id
    private int tid;

    // 用户头像url地址
    private String face;

    // 发帖用户名称
    private String author;

    // 发帖时间戳
    private String dateline;

    // 发帖所属的子模块分类
    private String forum_name;

    // 此贴的最新回复数
    private int replies;

    // 此贴对应的标题或摘要
    private String title;

    // 此贴对应的类型
    private int post_type;

    //收藏的ID
    private int favid;

    public int getFavid() {
        return favid;
    }

    public void setFavid(int favid) {
        this.favid = favid;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getForum_name() {
        return forum_name;
    }

    public void setForum_name(String forum_name) {
        this.forum_name = forum_name;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPost_type() {
        return post_type;
    }

    public void setPost_type(int post_type) {
        this.post_type = post_type;
    }

}
