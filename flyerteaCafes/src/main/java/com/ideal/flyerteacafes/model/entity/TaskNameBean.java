package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;

/**
 * Created by fly on 2017/4/13.
 */

public class TaskNameBean implements Serializable{


    private int cardMissionId;
    private String cardMissionTitle;
    private String cardMissionDesc;
    private int bankId;
    private int channelId;
    private String startTime;
    private String endTime;


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getCardMissionId() {
        return cardMissionId;
    }

    public void setCardMissionId(int cardMissionId) {
        this.cardMissionId = cardMissionId;
    }

    public String getCardMissionTitle() {
        return cardMissionTitle;
    }

    public void setCardMissionTitle(String cardMissionTitle) {
        this.cardMissionTitle = cardMissionTitle;
    }

    public String getCardMissionDesc() {
        return cardMissionDesc;
    }

    public void setCardMissionDesc(String cardMissionDesc) {
        this.cardMissionDesc = cardMissionDesc;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }
}
