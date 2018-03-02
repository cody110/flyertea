package com.ideal.flyerteacafes.model.entity;

/**
 * Created by Cindy on 2017/3/14.
 */
public class NotificationBean {

    private String content;//消息内容
    private String dateline;//时间
    private String from_id;//帖子id
    private String from_idtype;//消息子类型
    private String from_num;
    private String fromuid;//来自用户id
    private String fromuser;//来自用户名
    private String id;//消息id
    private String isnew;//是否新消息
    private String note;//消息原始内容
    private String pid;//回复内容的pid
    private String tid;//回复帖子的tid
    private String type;//消息类型
    private String uid;//用户id
    private String postindex;
    private String postpage;
    private String url;
    boolean isChoose=false;

    public String getIsnew() {
        return isnew;
    }

    public void setIsnew(String isnew) {
        this.isnew = isnew;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public String getFrom_idtype() {
        return from_idtype;
    }

    public void setFrom_idtype(String from_idtype) {
        this.from_idtype = from_idtype;
    }

    public String getFrom_num() {
        return from_num;
    }

    public void setFrom_num(String from_num) {
        this.from_num = from_num;
    }

    public String getFromuid() {
        return fromuid;
    }

    public void setFromuid(String fromuid) {
        this.fromuid = fromuid;
    }

    public String getFromuser() {
        return fromuser;
    }

    public void setFromuser(String fromuser) {
        this.fromuser = fromuser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPostindex() {
        return postindex;
    }

    public void setPostindex(String postindex) {
        this.postindex = postindex;
    }

    public String getPostpage() {
        return postpage;
    }

    public void setPostpage(String postpage) {
        this.postpage = postpage;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
