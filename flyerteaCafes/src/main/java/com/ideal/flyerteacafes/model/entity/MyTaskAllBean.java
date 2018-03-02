package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by fly on 2017/5/17.
 */

public class MyTaskAllBean {

    public static final String KEY_URL = "url", KEY_IMAGE_URL = "image_url";

    private List<MyTaskBean> advanced_tasks;//活动任务
    private List<MyTaskBean> freshman_tasks;//新手任务
    private List<MyTaskBean> recommend_tasks;//原推荐任务
    private List<MyTaskBean> daily_tasks;//每日任务
    private List<MyTaskBean> alldaily_tasks;//所有每日任务
    private List<MyTaskBean> birth_tasks;//生日任务
    private List<MyTaskBean> allfreshman_tasks;//固定8个新手任务
    private List<BannerBean> banner;
    private Map<String, String> flyerrights;


    public List<MyTaskBean> getAlldaily_tasks() {
        return alldaily_tasks;
    }

    public List<MyTaskBean> getBirth_tasks() {
        return birth_tasks;
    }

    public List<MyTaskBean> getAllfreshman_tasks() {
        return allfreshman_tasks;
    }

    public List<MyTaskBean> getDaily_tasks() {
        return daily_tasks;
    }

    public List<BannerBean> getBanner() {
        return banner;
    }

    public Map<String, String> getFlyerrights() {
        return flyerrights;
    }

    public List<MyTaskBean> getAdvanced_tasks() {
        return advanced_tasks;
    }


    public List<MyTaskBean> getFreshman_tasks() {
        return freshman_tasks;
    }


    public List<MyTaskBean> getRecommend_tasks() {
        return recommend_tasks;
    }

    public static class BannerBean implements Serializable{
        private String image_url;
        private String type;
        private String taskid;

        public String getImage_url() {
            return image_url;
        }

        public String getType() {
            return type;
        }

        public String getTaskid() {
            return taskid;
        }
    }

}
