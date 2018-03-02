package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;

public class Attachments implements Serializable {

    private int aid;
    private String filesize;
    private String kwidth;
    private String kheight;
    private String swidth;
    private String sheight;
    private String mwidth;
    private String mheight;
    private String imageurl;
    private String simageurl;
    private String kimageurl;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getKwidth() {
        return kwidth;
    }

    public void setKwidth(String kwidth) {
        this.kwidth = kwidth;
    }

    public String getKheight() {
        return kheight;
    }

    public void setKheight(String kheight) {
        this.kheight = kheight;
    }

    public String getSwidth() {
        return swidth;
    }

    public void setSwidth(String swidth) {
        this.swidth = swidth;
    }

    public String getSheight() {
        return sheight;
    }

    public void setSheight(String sheight) {
        this.sheight = sheight;
    }

    public String getMwidth() {
        return mwidth;
    }

    public void setMwidth(String mwidth) {
        this.mwidth = mwidth;
    }

    public String getMheight() {
        return mheight;
    }

    public void setMheight(String mheight) {
        this.mheight = mheight;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getSimageurl() {
        return simageurl;
    }

    public void setSimageurl(String simageurl) {
        this.simageurl = simageurl;
    }

    public String getKimageurl() {
        return kimageurl;
    }

    public void setKimageurl(String kimageurl) {
        this.kimageurl = kimageurl;
    }
}
