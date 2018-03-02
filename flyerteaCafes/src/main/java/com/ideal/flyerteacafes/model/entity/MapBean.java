package com.ideal.flyerteacafes.model.entity;

import android.text.TextUtils;

import java.util.Map;

/**
 * Created by fly on 2016/12/6.
 */

public class MapBean{

    private String success;

    private String code;

    private String message;

    private Map<String,String> data;

    public boolean isSuccessEquals1() {
        return TextUtils.equals(success, "1");
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
