package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fly on 2017/4/17.
 */

public class TaskDetailsBean implements Serializable{


    /**
     * myCardMissionId : 256
     * cardMissionId : 2
     * userId : 1616086
     * bankId : 19
     * channelId : 1
     * cardMissionTitle : 刷卡满额得Waterpik洁碧水牙线
     * cardMissionDesc : 仅限部分信用卡使用
     * 限2016年5月1日（含）前核卡成功的花旗人民币信用卡持卡人
     * 1、2016年5月1日前核卡成功的持卡人，通过“花旗银行信用卡”微信公众号，绑卡，进入“全球优惠”-“我要参加活动页面注册享：
     * 1）连续2个自然月内，每月消费6笔且每笔满188元，且累积有积分消费满指定金额享：
     * 满28000元赠Waterpik洁碧水牙线（市场价399元）
     * 满60000元赠Kindle Paperwhite亚马逊电子书阅读器（市场价958元）
     * 2、持卡人必须在2016年7月1日至8月31日成功注册本
     * startTime : 0
     * endTime : 0
     * prefix : 8552
     * priods : [{"myCardMissionPeriodId":"45","myCardMissionId":"256","cardMissionId":"2","cardMissionPeroidId":"0","userId":"1616086","posValue":"0","posTime":"0","isDone":"0","startTime":"0","endTime":"0","timeDone":"0","valueDone":"0.00","progresses":[]},{"myCardMissionPeriodId":"46","myCardMissionId":"256","cardMissionId":"2","cardMissionPeroidId":"0","userId":"1616086","posValue":"0","posTime":"0","isDone":"0","startTime":"0","endTime":"0","timeDone":"0","valueDone":"0.00","progresses":[]},{"myCardMissionPeriodId":"47","myCardMissionId":"256","cardMissionId":"2","cardMissionPeroidId":"0","userId":"1616086","posValue":"0","posTime":"0","isDone":"0","startTime":"0","endTime":"0","timeDone":"0","valueDone":"0.00","progresses":[]},{"myCardMissionPeriodId":"48","myCardMissionId":"256","cardMissionId":"2","cardMissionPeroidId":"0","userId":"1616086","posValue":"0","posTime":"0","isDone":"0","startTime":"0","endTime":"0","timeDone":"0","valueDone":"0.00","progresses":[]},{"myCardMissionPeriodId":"49","myCardMissionId":"256","cardMissionId":"2","cardMissionPeroidId":"0","userId":"1616086","posValue":"0","posTime":"0","isDone":"0","startTime":"0","endTime":"0","timeDone":"0","valueDone":"0.00","progresses":[]},{"myCardMissionPeriodId":"50","myCardMissionId":"256","cardMissionId":"2","cardMissionPeroidId":"0","userId":"1616086","posValue":"0","posTime":"0","isDone":"0","startTime":"0","endTime":"0","timeDone":"0","valueDone":"0.00","progresses":[]}]
     */

    private String myCardMissionId;
    private String cardMissionId;
    private String userId;
    private String bankId;
    private String channelId;
    private String cardMissionTitle;
    private String cardMissionDesc;
    private String startTime;
    private String endTime;
    private String prefix;
    private List<TaskPriodBean> periods;

    public String getMyCardMissionId() {
        return myCardMissionId;
    }

    public void setMyCardMissionId(String myCardMissionId) {
        this.myCardMissionId = myCardMissionId;
    }

    public String getCardMissionId() {
        return cardMissionId;
    }

    public void setCardMissionId(String cardMissionId) {
        this.cardMissionId = cardMissionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public List<TaskPriodBean> getPeriods() {
        return periods;
    }

    public void setPeriods(List<TaskPriodBean> periods) {
        this.periods = periods;
    }
}
