package com.ideal.flyerteacafes.model.entity;

import java.util.List;

public class RaidersModel {
    private int has_next;

    private List<RaidersBean> lists;

    private int count;

    private MessageBean Message;

    public int getHas_next() {
        return has_next;
    }

    public void setHas_next(int has_next) {
        this.has_next = has_next;
    }

    public List<RaidersBean> getLists() {
        return lists;
    }

    public void setLists(List<RaidersBean> lists) {
        this.lists = lists;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public MessageBean getMessage() {
        return Message;
    }

    public void setMessage(MessageBean message) {
        Message = message;
    }
}
