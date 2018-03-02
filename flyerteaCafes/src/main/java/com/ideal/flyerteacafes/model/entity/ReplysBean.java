package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;

/**
 * Created by young on 2015/12/8.
 */
public class ReplysBean implements Serializable{

    private int feed_reply_id;
    //评论的feedID
    private int feed_id;
    //评论该feed 的用户ID
    private int from_user_id;
    //评论该feed 用户的昵称
    private String from_user_name;
    //发表该feed用户ID
    private int to_user_id;
    //发表该feed用户昵称
    private String to_user_name;
    private int direct_reply;
    private long created_at;
    private long updated_at;
    private String content;

    public int getFeed_reply_id() {
        return feed_reply_id;
    }

    public void setFeed_reply_id(int feed_reply_id) {
        this.feed_reply_id = feed_reply_id;
    }

    public int getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(int feed_id) {
        this.feed_id = feed_id;
    }

    public int getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(int from_user_id) {
        this.from_user_id = from_user_id;
    }

    public String getFrom_user_name() {
        return from_user_name;
    }

    public void setFrom_user_name(String from_user_name) {
        this.from_user_name = from_user_name;
    }

    public int getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(int to_user_id) {
        this.to_user_id = to_user_id;
    }

    public String getTo_user_name() {
        return to_user_name;
    }

    public void setTo_user_name(String to_user_name) {
        this.to_user_name = to_user_name;
    }

    public int getDirect_reply() {
        return direct_reply;
    }

    public void setDirect_reply(int direct_reply) {
        this.direct_reply = direct_reply;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
