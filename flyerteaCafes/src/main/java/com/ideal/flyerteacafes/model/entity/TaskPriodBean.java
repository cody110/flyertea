package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fly on 2017/4/17.
 * 刷卡周期
 */

public class TaskPriodBean implements Serializable {
    /**
     * myCardMissionPeriodId : 45
     * myCardMissionId : 256
     * cardMissionId : 2
     * cardMissionPeroidId : 0
     * userId : 1616086
     * posValue : 0
     * posTime : 0
     * isDone : 0
     * startTime : 0
     * endTime : 0
     * timeDone : 0
     * valueDone : 0.00
     * progresses : []
     */

    public static final int DONESTATUS_COMPLETED = 1, DONESTATUS_NOT_COMPLETED = 2, DONESTATUS_ING = 3, DONESTATUS_NOT_START = 4;

    private String doneDescription;
    private int doneStatus;
    private String myCardMissionPeriodId;
    private String myCardMissionId;
    private String cardMissionId;
    private String cardMissionPeroidId;
    private String userId;
    private int posValue;
    private int posTime;
    private float pertimeLimit;
    private String isDone;
    private String startTime;
    private String endTime;
    private int timeDone;
    private float valueDone;
    private List<TaskPriodProgressesBean> progresses;

    public float getPertimeLimit() {
        return pertimeLimit;
    }

    public void setPertimeLimit(float pertimeLimit) {
        this.pertimeLimit = pertimeLimit;
    }

    public String getDoneDescription() {
        return doneDescription;
    }

    public void setDoneDescription(String doneDescription) {
        this.doneDescription = doneDescription;
    }

    public int getDoneStatus() {
        return doneStatus;
    }

    public void setDoneStatus(int doneStatus) {
        this.doneStatus = doneStatus;
    }

    public int getPosValue() {
        return posValue;
    }

    public void setPosValue(int posValue) {
        this.posValue = posValue;
    }

    public int getPosTime() {
        return posTime;
    }

    public void setPosTime(int posTime) {
        this.posTime = posTime;
    }

    public int getTimeDone() {
        return timeDone;
    }

    public void setTimeDone(int timeDone) {
        this.timeDone = timeDone;
    }

    public float getValueDone() {
        return valueDone;
    }

    public void setValueDone(float valueDone) {
        this.valueDone = valueDone;
    }

    public String getMyCardMissionPeriodId() {
        return myCardMissionPeriodId;
    }

    public void setMyCardMissionPeriodId(String myCardMissionPeriodId) {
        this.myCardMissionPeriodId = myCardMissionPeriodId;
    }

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

    public String getCardMissionPeroidId() {
        return cardMissionPeroidId;
    }

    public void setCardMissionPeroidId(String cardMissionPeroidId) {
        this.cardMissionPeroidId = cardMissionPeroidId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsDone() {
        return isDone;
    }

    public void setIsDone(String isDone) {
        this.isDone = isDone;
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

    public List<TaskPriodProgressesBean> getProgresses() {
        return progresses;
    }

    public void setProgresses(List<TaskPriodProgressesBean> progresses) {
        this.progresses = progresses;
    }
}
