package com.ideal.flyerteacafes.model.entity;

/**
 * 我的消息
 *
 * @author fly
 */
public class MessageBean extends BaseBean {

    // 私信用户的id 与登录用户私信对话的用户
    private int user_id;

    // 私信用户头像url地址
    private String user_icon_url;

    // 私信用户名称
    private String user_name;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_icon_url() {
        return user_icon_url;
    }

    public void setUser_icon_url(String user_icon_url) {
        this.user_icon_url = user_icon_url;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString() {
        return "MessageBean [user_id=" + user_id + ", user_icon_url="
                + user_icon_url + ", user_name=" + user_name + "]";
    }

}
