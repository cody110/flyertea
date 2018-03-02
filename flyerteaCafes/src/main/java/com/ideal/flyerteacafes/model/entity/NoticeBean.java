package com.ideal.flyerteacafes.model.entity;

/**
 * 公告列表
 *
 * @author fly
 */
public class NoticeBean extends BaseBean {

    private int id;
    //公告的内容
    private String subject;
    //公告发布的时间戳
    private String starttime;
    //公告的类型
    private int type;
    //公告跳转的url地址
    private String message;

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
