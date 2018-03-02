package com.ideal.flyerteacafes.model.request;

/**
 * Created by fly on 2015/12/8.
 */
public class FeedLikeParams {


    private int feed_id;
    private int user_like_id;
    private String user_like_name;
    private int user_liked_id;
    private String user_liked_name;
    private boolean like_or_not;

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

    public boolean isLike_or_not() {
        return like_or_not;
    }

    public void setLike_or_not(boolean like_or_not) {
        this.like_or_not = like_or_not;
    }
}
