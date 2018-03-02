package com.ideal.flyerteacafes.model.entity;

/**
 * Created by fly on 2016/6/6.
 */
public class ExerciseBean {

    private String id;

    private String subject;

    private String description;

    private String catid;

    private String url;

    private String cover;

    private String starttime;

    private String endtime;

    private String views;

    private String status;

    private String dateline;

    private String subject_cutstr;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setSubject(String subject){
        this.subject = subject;
    }
    public String getSubject(){
        return this.subject;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setCatid(String catid){
        this.catid = catid;
    }
    public String getCatid(){
        return this.catid;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
    public void setCover(String cover){
        this.cover = cover;
    }
    public String getCover(){
        return this.cover;
    }
    public void setStarttime(String starttime){
        this.starttime = starttime;
    }
    public String getStarttime(){
        return this.starttime;
    }
    public void setEndtime(String endtime){
        this.endtime = endtime;
    }
    public String getEndtime(){
        return this.endtime;
    }
    public void setViews(String views){
        this.views = views;
    }
    public String getViews(){
        return this.views;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setDateline(String dateline){
        this.dateline = dateline;
    }
    public String getDateline(){
        return this.dateline;
    }
    public void setSubject_cutstr(String subject_cutstr){
        this.subject_cutstr = subject_cutstr;
    }
    public String getSubject_cutstr(){
        return this.subject_cutstr;
    }

}
