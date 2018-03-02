package com.ideal.flyerteacafes.model.request;

/**
 * Created by young on 2015/12/9.
 */
public class SendFeedComRequest {

    private int feed_id;
    //评论该feed 的用户ID
    private int from_user_id;
    //评论该feed 的用户昵称
    private String from_user_name;
    //发表该feed 的用户ID
    private int to_user_id;
    //发表该feed 的用户昵称
    private String to_user_name;
    //0直接回复 1是@回复
    private int direct_reply;
    //评论内容
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
