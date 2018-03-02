package com.ideal.flyerteacafes.model.loca;

import android.app.Activity;

/**
 * Created by fly on 2016/6/1.
 */
public class HomeFmBtnInfo {

    private int imgId;

    private String name;

    private Class<? extends Activity> activity;

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Class<? extends Activity> getActivity() {
        return activity;
    }

    public void setActivity(Class<? extends Activity> activity) {
        this.activity = activity;
    }

    public HomeFmBtnInfo(int imgId, String name, Class<? extends Activity> activity) {
        this.imgId = imgId;
        this.name = name;
        this.activity = activity;
    }
}
