package com.ideal.flyerteacafes.model.loca;

import com.flyer.tusdk.model.VideoInfo;

import java.io.Serializable;

/**
 * Created by fly on 2017/3/13.
 * 写复杂帖
 */

public class TuwenInfo implements Serializable{

    public static final int TYPE_TU = 1;//图
    public static final int TYPE_WEN = 2;//文
    public static final int TYPE_VIDEO=3;

    private int type;
    private String imgPath;
    private String text;
    private String videoPath;
    private VideoInfo videoInfo;
    private int imgStatus=UploadImgInfo.STATUS_NOT_BEGIN;


    public int getImgStatus() {
        return imgStatus;
    }

    public void setImgStatus(int imgStatus) {
        this.imgStatus = imgStatus;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public VideoInfo getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(VideoInfo videoInfo) {
        this.videoInfo = videoInfo;
    }
}
