package com.ideal.flyerteacafes.model.entity;

import java.util.List;

/**
 * Created by fly on 2016/6/6.
 */
public class PostBean {

    private String id;

    private String tid;

    private String uid;

    private String username;

    private String subject;

    private String dateline;

    private List<String> pic;

    private String summary;

    private String face;

    private String is_friend;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setTid(String tid){
        this.tid = tid;
    }
    public String getTid(){
        return this.tid;
    }
    public void setUid(String uid){
        this.uid = uid;
    }
    public String getUid(){
        return this.uid;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }
    public void setSubject(String subject){
        this.subject = subject;
    }
    public String getSubject(){
        return this.subject;
    }
    public void setDateline(String dateline){
        this.dateline = dateline;
    }
    public String getDateline(){
        return this.dateline;
    }
    public void setSummary(String summary){
        this.summary = summary;
    }
    public String getSummary(){
        return this.summary;
    }
    public void setFace(String face){
        this.face = face;
    }
    public String getFace(){
        return this.face;
    }
    public void setIs_friend(String is_friend){
        this.is_friend = is_friend;
    }
    public String getIs_friend(){
        return this.is_friend;
    }

    public List<String> getPic() {
        return pic;
    }

    public void setPic(List<String> pic) {
        this.pic = pic;
    }
}
