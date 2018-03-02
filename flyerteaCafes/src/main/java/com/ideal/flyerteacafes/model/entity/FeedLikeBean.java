package com.ideal.flyerteacafes.model.entity;

/**
 * Created by fly on 2015/12/10.
 */
public class FeedLikeBean {

    private int feed_like_id;


    private int feed_id;


    private int user_like_id;


    private String user_like_name;


    private int user_liked_id;


    private String user_liked_name;


    private int like_or_not;


    private int created_at;


    private int updated_at;


    private String client_ip;


    private String groupname;


    private int has_sm;

    public int getFeed_like_id() {
        return feed_like_id;
    }

    public void setFeed_like_id(int feed_like_id) {
        this.feed_like_id = feed_like_id;
    }

    public int getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(int feed_id) {
        this.feed_id = feed_id;
    }

    public int getUser_like_id() {
        return user_like_id;
    }

    public void setUser_like_id(int user_like_id) {
        this.user_like_id = user_like_id;
    }

    public String getUser_like_name() {
        return user_like_name;
    }

    public void setUser_like_name(String user_like_name) {
        this.user_like_name = user_like_name;
    }

    public int getUser_liked_id() {
        return user_liked_id;
    }

    public void setUser_liked_id(int user_liked_id) {
        this.user_liked_id = user_liked_id;
    }

    public String getUser_liked_name() {
        return user_liked_name;
    }

    public void setUser_liked_name(String user_liked_name) {
        this.user_liked_name = user_liked_name;
    }

    public int getLike_or_not() {
        return like_or_not;
    }

    public void setLike_or_not(int like_or_not) {
        this.like_or_not = like_or_not;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(int updated_at) {
        this.updated_at = updated_at;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public int getHas_sm() {
        return has_sm;
    }

    public void setHas_sm(int has_sm) {
        this.has_sm = has_sm;
    }
}
