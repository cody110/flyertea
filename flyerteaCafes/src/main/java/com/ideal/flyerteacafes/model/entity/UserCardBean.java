package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fly on 2017/5/24.
 */

public class UserCardBean implements Serializable {

    private List<CardInfo> vips;
    private String status;
    private String status_desc;

    public List<CardInfo> getVips() {
        return vips;
    }

    public void setVips(List<CardInfo> vips) {
        this.vips = vips;
    }

    public String getStatus() {
        return status;
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public static class CardInfo implements Serializable {
        private String id;
        private String uid;
        private String groupid;
        private String mid;
        private String code;
        private String img_url;
        private String type;
        private String status;
        private String status_desc;
        private String created_at;
        private String updated_at;
        private String group_name;
        private String membership_name;
        private String name;
        private String icon;

        public String getStatus_desc() {
            return status_desc;
        }

        public String getId() {
            return id;
        }

        public String getUid() {
            return uid;
        }

        public String getGroupid() {
            return groupid;
        }

        public String getMid() {
            return mid;
        }

        public String getCode() {
            return code;
        }

        public String getImg_url() {
            return img_url;
        }

        public String getType() {
            return type;
        }

        public String getStatus() {
            return status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getGroup_name() {
            return group_name;
        }

        public String getMembership_name() {
            return membership_name;
        }

        public String getName() {
            return name;
        }

        public String getIcon() {
            return icon;
        }

        @Override
        public String toString() {
            return "CardInfo{" +
                    "id='" + id + '\'' +
                    ", uid='" + uid + '\'' +
                    ", groupid='" + groupid + '\'' +
                    ", mid='" + mid + '\'' +
                    ", code='" + code + '\'' +
                    ", img_url='" + img_url + '\'' +
                    ", type='" + type + '\'' +
                    ", status='" + status + '\'' +
                    ", created_at='" + created_at + '\'' +
                    ", updated_at='" + updated_at + '\'' +
                    ", group_name='" + group_name + '\'' +
                    ", membership_name='" + membership_name + '\'' +
                    ", name='" + name + '\'' +
                    ", icon='" + icon + '\'' +
                    '}';
        }
    }
}
