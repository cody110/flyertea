package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;

/**
 * Created by fly on 2017/4/17.
 * 刷卡任务 周期  进度
 */

public class TaskPriodProgressesBean implements Serializable{

    private String MCC;//	MCC码	string	@mock=$order('423423','423423','423423','423423','423423','423423','423423','423423','423423','423423','423423')
    private String bankId;//	银行ID	string	@mock=$order('17','17','17','17','17','17','17','17','17','17','17')
    private String billPic;//	刷卡凭据	string	@mock=$order('http://pic.newssc.org/upload/ori/20160602/1464820435341.jpg','http://pic.newssc.org/upload/ori/20160602/1464820435341.jpg','http://pic.newssc.org/upload/ori/20160602/1464820435341.jpg','http://pic.newssc.org/upload/ori/20160602/1464820435341.jpg','http://pic.newssc.org/upload/ori/20160602/1464820435341.jpg','http://pic.newssc.org/upload/ori/20160602/1464820435341.jpg','http://pic.newssc.org/upload/ori/20160602/1464820435341.jpg','http://pic.newssc.org/upload/ori/20160602/1464820435341.jpg','http://pic.newssc.org/upload/ori/20160602/1464820435341.jpg','http://pic.newssc.org/upload/ori/20160602/1464820435341.jpg','http://pic.newssc.org/upload/ori/20160602/1464820435341.jpg')
    private String  cardMissionId;//		string	@mock=$order('78','78','78','78','78','78','78','78','78','78','78')
    private String cardMissionPeroidId;//		string	@mock=$order('19','19','19','19','19','19','19','19','19','19','19')
    private String channelId;//		string	@mock=$order('2','2','2','2','2','2','2','2','2','2','2')
    private String createTime;//		string	@mock=$order('1492060363','1492060642','1492060565','1492060541','1492060464','1492060453','1492060432','1492060431','1492060428','1492060377','1492060658')
    private String isDelete;//		string	@mock=$order('0','0','0','0','0','0','0','0','0','0','0')
    private String isValid;//	是否有效	string	@mock=$order('0','1','1','1','1','0','0','0','0','0','1')
    private String myCardMissionId;//		string	@mock=$order('249','249','249','249','249','249','249','249','249','249','249')
    private String myCardMissionProgressId;//		string	@mock=$order('483','492','491','490','489','488','487','486','485','484','493')
    private String posTime;//	刷卡时间	string	@mock=$order('1492046623','1492046623','1492046623','1492046623','1492046623','1492046623','1492046623','1492046623','1492046623','1492046623','1492046623')
    private String shop;//	消费商户	string	@mock=$order('屈臣氏','屈臣氏','屈臣氏','屈臣氏','屈臣氏','屈臣氏','屈臣氏','屈臣氏','屈臣氏','屈臣氏','屈臣氏')
    private String value;//	刷卡值

    public String getMCC() {
        return MCC;
    }

    public void setMCC(String MCC) {
        this.MCC = MCC;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBillPic() {
        return billPic;
    }

    public void setBillPic(String billPic) {
        this.billPic = billPic;
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

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getMyCardMissionId() {
        return myCardMissionId;
    }

    public void setMyCardMissionId(String myCardMissionId) {
        this.myCardMissionId = myCardMissionId;
    }

    public String getMyCardMissionProgressId() {
        return myCardMissionProgressId;
    }

    public void setMyCardMissionProgressId(String myCardMissionProgressId) {
        this.myCardMissionProgressId = myCardMissionProgressId;
    }

    public String getPosTime() {
        return posTime;
    }

    public void setPosTime(String posTime) {
        this.posTime = posTime;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
