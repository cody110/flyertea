package com.ideal.flyerteacafes.model.request;

import java.io.Serializable;

/**
 * Created by fly on 2015/12/3.
 */
public class FoodRequest implements Serializable{

    private String user_id;
    private String user_name;
    private String content;
    private String latitude;
    private String longitude;
    private String location;
    private int attachment_type;
    private String attachment_urls;
    private String tags;
    private int feed_topic_id;
    private String feed_topic_title;
    private String video_url;

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getFeed_topic_title() {
        return feed_topic_title;
    }

    public void setFeed_topic_title(String feed_topic_title) {
        this.feed_topic_title = feed_topic_title;
    }

    public int getFeed_topic_id() {
        return feed_topic_id;
    }

    public void setFeed_topic_id(int feed_topic_id) {
        this.feed_topic_id = feed_topic_id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getAttachment_type() {
        return attachment_type;
    }

    public void setAttachment_type(int attachment_type) {
        this.attachment_type = attachment_type;
    }

    public String getAttachment_urls() {
        return attachment_urls;
    }

    public void setAttachment_urls(String attachment_urls) {
        this.attachment_urls = attachment_urls;
    }
}
