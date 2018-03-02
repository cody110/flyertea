package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;

/**
 * Created by fly on 2016/12/14.
 */

public class UpgradeBean implements Serializable {

    private String vid;
    private String platform;
    private String title;
    private String updates;
    private String[] vtoupdate;
    private String version;
    private String uplink;
    private String app;
    private String hasupdate;
    private String forceupdate;


    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdates() {
        return updates;
    }

    public void setUpdates(String updates) {
        this.updates = updates;
    }

    public String[] getVtoupdate() {
        return vtoupdate;
    }

    public void setVtoupdate(String[] vtoupdate) {
        this.vtoupdate = vtoupdate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUplink() {
        return uplink;
    }

    public void setUplink(String uplink) {
        this.uplink = uplink;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getHasupdate() {
        return hasupdate;
    }

    public void setHasupdate(String hasupdate) {
        this.hasupdate = hasupdate;
    }

    public String getForceupdate() {
        return forceupdate;
    }

    public void setForceupdate(String forceupdate) {
        this.forceupdate = forceupdate;
    }
}
