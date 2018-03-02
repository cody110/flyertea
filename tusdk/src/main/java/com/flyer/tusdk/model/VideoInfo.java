package com.flyer.tusdk.model;

import java.io.Serializable;

/**
 * Created by fly on 2018/1/18.
 * 视频信息
 */

public class VideoInfo implements Serializable{

    //初始视频
    private String videoInitPath;
    //视频本地路径
    private String videoLocalPath;
    //视频本地缩略图
    private String thumbnailLocalPath;
    //视频云路径
    private String videoUrl;
    //缩略图云路径
    private String thumbnailUrl;
    private int videoWidth;
    private int videoHeight;
    private int videosize;

    //you盘云视频id
    private String vids;

    private int timelength;

    public int getTimelength() {
        return timelength;
    }

    public void setTimelength(int timelength) {
        this.timelength = timelength;
    }

    public String getVids() {
        return vids;
    }

    public void setVids(String vids) {
        this.vids = vids;
    }

    public int getVideosize() {
        return videosize;
    }

    public void setVideosize(int videosize) {
        this.videosize = videosize;
    }

    public int getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(int videoWidth) {
        this.videoWidth = videoWidth;
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(int videoHeight) {
        this.videoHeight = videoHeight;
    }

    public String getVideoInitPath() {
        return videoInitPath;
    }

    public void setVideoInitPath(String videoInitPath) {
        this.videoInitPath = videoInitPath;
    }

    public String getVideoLocalPath() {
        return videoLocalPath;
    }

    public void setVideoLocalPath(String videoLocalPath) {
        this.videoLocalPath = videoLocalPath;
    }

    public String getThumbnailLocalPath() {
        return thumbnailLocalPath;
    }

    public void setThumbnailLocalPath(String thumbnailLocalPath) {
        this.thumbnailLocalPath = thumbnailLocalPath;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public String toString() {
        return "VideoInfo{" +
                "videoInitPath='" + videoInitPath + '\'' +
                ", videoLocalPath='" + videoLocalPath + '\'' +
                ", thumbnailLocalPath='" + thumbnailLocalPath + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", videoWidth=" + videoWidth +
                ", videoHeight=" + videoHeight +
                '}';
    }
}
