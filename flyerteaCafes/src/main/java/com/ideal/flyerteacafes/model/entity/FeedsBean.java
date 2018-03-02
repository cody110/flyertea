package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fly on 2015/11/19.
 */
public class FeedsBean implements Serializable {


    private String feed_id;
    private String user_id;
    private String user_name;
    private String content;
    private String location;
    private int like_num;
    private int comment_num;
    private int status;
    private boolean like;
    private String created_at;
    private String updated_at;
    private String attachment_urls;
    private String tags;
    private String feed_topic_id;
    private String feed_topic_title;
    private List<String> urlList;
    private List<ReplysBean> commentlist;
    private int attachment_type;
    private String video_url;

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public int getAttachment_type() {
        return attachment_type;
    }

    public void setAttachment_type(int attachment_type) {
        this.attachment_type = attachment_type;
    }

    public List<ReplysBean> getCommentlist() {
        return commentlist;
    }

    public void setCommentlist(List<ReplysBean> commentlist) {
        this.commentlist = commentlist;
    }

    public String getFeed_topic_id() {
        return feed_topic_id;
    }

    public void setFeed_topic_id(String feed_topic_id) {
        this.feed_topic_id = feed_topic_id;
    }

    public String getFeed_topic_title() {
        return feed_topic_title;
    }

    public void setFeed_topic_title(String feed_topic_title) {
        this.feed_topic_title = feed_topic_title;
    }

    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

    public String getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(String feed_id) {
        this.feed_id = feed_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public int getLike_num() {
        return like_num;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isLiked() {
        return like;
    }

    public void setLiked(boolean liked) {
        this.like = liked;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getAttachment_urls() {
        return attachment_urls;
    }

    public void setAttachment_urls(String attachment_urls) {
        this.attachment_urls = attachment_urls;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
