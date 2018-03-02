package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fly on 2017/10/20.
 */

public class MyMedalsBean {

    private String name;

    private List<MyMedalsTypeBean> medals;

    public String getName() {
        return name;
    }

    public List<MyMedalsTypeBean> getMedals() {
        return medals;
    }



    public static class MyMedalsTypeBean implements Serializable{
        private String groupid;
        private String groupname;
        private String eraned;
        private List<MyMedalsSubBean> medals;

        public String getGroupid() {
            return groupid;
        }

        public String getGroupname() {
            return groupname;
        }

        public List<MyMedalsSubBean> getMedals() {
            return medals;
        }

        public String getEraned() {
            return eraned;
        }
    }




}
