package com.ideal.flyerteacafes.model.entity;

import android.support.annotation.TransitionRes;
import android.text.TextUtils;

import org.xutils.db.annotation.Column;


/**
 * 基礎字段
 *
 * @author fly
 */
public class BaseBean {

    public final static int type_error = -1, type_1 = 1, type_2 = 2, type_3 = 3, type_4 = 4;//当前json类型，1为论坛类型，2为直播新规范,直接为json数组格式，没有最外层数据,4论坛新类型

    private int jsonType = type_error;//默认-1，解析出错
    public static final String OK_CODE = "110";

    private String messageval = "";//code
    private String messagestr = "";//msg
    private int version = 0;
    private int has_next = 0;
    private int count = 0;
    private int page = 0;
    private String data;
    private String success;
    private String jsonString;


    public boolean isSuccessEquals1() {
        return TextUtils.equals(success, "1");
    }


    public void setSuccess(String success) {
        this.success = success;
    }

    public int getJsonType() {
        return jsonType;
    }

    public void setJsonType(int jsonType) {
        this.jsonType = jsonType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCode() {
        return empty(messageval);
    }

    public void setCode(String ret_code) {
        this.messageval = ret_code;
    }

    public String getMessage() {
        return messagestr;
    }

    public void setMessage(String ret_msg) {
        this.messagestr = ret_msg;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getHas_next() {
        return has_next;
    }

    public void setHas_next(int has_next) {
        this.has_next = has_next;
    }

    public String empty(String str) {
        if (str == null)
            return "";
        return str;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "messageval='" + messageval + '\'' +
                ", messagestr='" + messagestr + '\'' +
                ", jsonType=" + jsonType +
                ", version=" + version +
                ", has_next=" + has_next +
                ", count=" + count +
                ", page=" + page +
                ", data='" + data + '\'' +
                '}';
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }
}
