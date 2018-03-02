package com.ideal.flyerteacafes.model.entity;

import java.util.List;

/**
 * Created by fly on 2017/5/19.
 */

public class MyTaskDetailsBean {

    private String app_url;
    private String app_url_id;
    private String h5_url;

    private String taskid;
    private String tasktype;
    private String available;
    private String name;
    private String csc;
    private String status;
    private String lastupdate;
    private String applied;
    private String description;
    private String icon;
    private String starttime;
    private String endtime;
    private String period;
    private String periodtype;
    private String reward;
    private String prize;
    private String reward2;
    private String prize2;
    private String rewardurl;
    private String rewardurl2;
    private String nextapplytime;
    private String currentapplytime;
    private String current_taskperiods;
    private String rewardtext;
    private String rewardother;
    private List<CreditlogBean> creditlogs;

    public String getRewardother() {
        return rewardother;
    }

    public String getRewardtext() {
        return rewardtext;
    }

    public String getNextapplytime() {
        return nextapplytime;
    }

    public String getCurrentapplytime() {
        return currentapplytime;
    }

    public String getCurrent_taskperiods() {
        return current_taskperiods;
    }

    public String getPeriodtype() {
        return periodtype;
    }

    public String getH5_url() {
        return h5_url;
    }

    public String getApp_url() {
        return app_url;
    }

    public String getApp_url_id() {
        return app_url_id;
    }

    public List<CreditlogBean> getCreditlogs() {
        return creditlogs;
    }

    public void setCreditlogs(List<CreditlogBean> creditlogs) {
        this.creditlogs = creditlogs;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getTasktype() {
        return tasktype;
    }

    public void setTasktype(String tasktype) {
        this.tasktype = tasktype;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCsc() {
        return csc;
    }

    public void setCsc(String csc) {
        this.csc = csc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(String lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getApplied() {
        return applied;
    }

    public void setApplied(String applied) {
        this.applied = applied;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getReward2() {
        return reward2;
    }

    public void setReward2(String reward2) {
        this.reward2 = reward2;
    }

    public String getPrize2() {
        return prize2;
    }

    public void setPrize2(String prize2) {
        this.prize2 = prize2;
    }

    public String getRewardurl() {
        return rewardurl;
    }

    public void setRewardurl(String rewardurl) {
        this.rewardurl = rewardurl;
    }

    public String getRewardurl2() {
        return rewardurl2;
    }

    public void setRewardurl2(String rewardurl2) {
        this.rewardurl2 = rewardurl2;
    }

    public static class CreditlogBean{
        private String dateline;
        private String extcredits1;
        private String extcredits6;

        public String getDateline() {
            return dateline;
        }

        public void setDateline(String dateline) {
            this.dateline = dateline;
        }

        public String getExtcredits1() {
            return extcredits1;
        }

        public void setExtcredits1(String extcredits1) {
            this.extcredits1 = extcredits1;
        }

        public String getExtcredits6() {
            return extcredits6;
        }

        public void setExtcredits6(String extcredits6) {
            this.extcredits6 = extcredits6;
        }
    }
}
