package com.ideal.flyerteacafes.model.entity;

/**
 * 私信
 *
 * @author fly
 */
public class PersonalLetterBean extends BaseBean {


    private int id;
    private String touid;    //私信用户的id
    private String face;//私信用户头像url地址
    private String msgfrom;//	私信用户名称
    private String isnew; //已读未读
    private String subject;//最后一条私信消息内容
    private String dateline;//最后一条私信消息的发生时间戳
    private int pmnum;//总条数

    public int getPmnum() {
        return pmnum;
    }

    public void setPmnum(int pmnum) {
        this.pmnum = pmnum;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getIsnew() {
        return isnew;
    }

    public void setIsnew(String isnew) {
        this.isnew = isnew;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTouid() {
        return touid;
    }

    public void setTouid(String touid) {
        this.touid = touid;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getMsgfrom() {
        return msgfrom;
    }

    public void setMsgfrom(String msgfrom) {
        this.msgfrom = msgfrom;
    }

}
