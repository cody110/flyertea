package com.ideal.flyerteacafes.model.entity;

import android.support.annotation.ColorInt;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by fly on 2016/11/29.
 * 阅读记录
 */
@Table(name = "ReadsBean")
public class ReadsBean {

    public static String IDTYPE_TID = "tid", IDTYPE_AID = "aid";

    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "fid")
    private String fid;
    @Column(name = "fname")
    private String fname;
    @Column(name = "idtype")
    private String idtype;
    @Column(name = "readtime")
    private String dbdateline;
    @Column(name = "subject")
    private String subject;
    @Column(name = "tid")
    private String tid;
    @Column(name = "uid")
    private String uid;
    @Column(name = "professional")
    private String professional;

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getIdtype() {
        return idtype;
    }

    public void setIdtype(String idtype) {
        this.idtype = idtype;
    }

    public String getDbdateline() {
        return dbdateline;
    }

    public void setDbdateline(String dbdateline) {
        this.dbdateline = dbdateline;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
