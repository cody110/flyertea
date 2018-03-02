package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Cidny on 2017/3/21.
 */
public class PersonalBean implements Serializable{

//    activities ：活动数据 activity 新的活动树龄  recommends ：推荐好文数据  recommend新的好文数量
//  posts 回帖   flowers 送花  friends  好友  reward ：帖子成就  system  ：心痛

    private List<PersonalMode> activities;//活动数据
    private List<PersonalMode> recommends;//好文推荐（只显示第一条）
    private String activity;//新的活动数量
    private String recommend;//新的好文数量
    private String flowers;//送花消息数
    private String friends;//好友消息数
    private String posts;//回帖相关消息数
    private String reward;//成就
    private String system;//系统消息


    public String getActivity() {
        return activity;
    }

    public List<PersonalMode> getRecommends() {
        return recommends;
    }

    public String getRecommend() {
        return recommend;
    }

    public String getReward() {
        return reward;
    }

    public String getSystem() {
        return system;
    }

    public List<PersonalMode> getActivities() {
        return activities;
    }

    public String getFlowers() {
        return flowers;
    }

    public void setFlowers(String flowers) {
        this.flowers = flowers;
    }

    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }


    public String getPosts() {
        return posts;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }
}
