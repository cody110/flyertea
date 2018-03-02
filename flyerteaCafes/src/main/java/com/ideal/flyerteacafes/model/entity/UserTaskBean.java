package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;

/**
 * Created by fly on 2017/4/17.
 * 用户任务
 */

public class UserTaskBean implements Serializable {
    private String cardMissionDesc;
    private String cardMissionTitle;
    private int myCardMissionId;
    private int periodnum;//总数
    private int periodDone;//完成的
    private int periodUnDone;//未完成的
    private int periodDing;//进行中的
    private int periodTodo;//将要做的周期
    private String prefix;//后四位
    private TaskPriodBean currentperiod;
    private TaskPriodBean nextperiod;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getPeriodTodo() {
        return periodTodo;
    }

    public void setPeriodTodo(int periodTodo) {
        this.periodTodo = periodTodo;
    }

    public int getPeriodDing() {
        return periodDing;
    }

    public void setPeriodDing(int periodDing) {
        this.periodDing = periodDing;
    }

    public TaskPriodBean getCurrentperiod() {
        return currentperiod;
    }

    public void setCurrentperiod(TaskPriodBean currentperiod) {
        this.currentperiod = currentperiod;
    }

    public int getPeriodDone() {
        return periodDone;
    }

    public void setPeriodDone(int periodDone) {
        this.periodDone = periodDone;
    }

    public int getPeriodUnDone() {
        return periodUnDone;
    }

    public void setPeriodUnDone(int periodUnDone) {
        this.periodUnDone = periodUnDone;
    }

    public TaskPriodBean getNextperiod() {
        return nextperiod;
    }

    public void setNextperiod(TaskPriodBean nextperiod) {
        this.nextperiod = nextperiod;
    }

    public String getCardMissionDesc() {
        return cardMissionDesc;
    }

    public void setCardMissionDesc(String cardMissionDesc) {
        this.cardMissionDesc = cardMissionDesc;
    }

    public String getCardMissionTitle() {
        return cardMissionTitle;
    }

    public void setCardMissionTitle(String cardMissionTitle) {
        this.cardMissionTitle = cardMissionTitle;
    }

    public int getMyCardMissionId() {
        return myCardMissionId;
    }

    public void setMyCardMissionId(int myCardMissionId) {
        this.myCardMissionId = myCardMissionId;
    }

    public int getPeriodnum() {
        return periodnum;
    }

    public void setPeriodnum(int periodnum) {
        this.periodnum = periodnum;
    }
}
