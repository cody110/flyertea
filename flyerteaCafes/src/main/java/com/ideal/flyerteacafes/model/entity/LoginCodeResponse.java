package com.ideal.flyerteacafes.model.entity;

/**
 * Created by young on 2016/1/5.
 */
public class LoginCodeResponse {
    //手机号
    private String mobile_no;
    //是否发送了SMS
    private String sendsms;
    //结果状态返回0-失败，1-成功
    private String resultstatus;
    //结果描述
    private String result;
    //验证码 MD5
    private String codemd5;

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getSendsms() {
        return sendsms;
    }

    public void setSendsms(String sendsms) {
        this.sendsms = sendsms;
    }

    public String getResultstatus() {
        return resultstatus;
    }

    public void setResultstatus(String resultstatus) {
        this.resultstatus = resultstatus;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCodemd5() {
        return codemd5;
    }

    public void setCodemd5(String codemd5) {
        this.codemd5 = codemd5;
    }
}
