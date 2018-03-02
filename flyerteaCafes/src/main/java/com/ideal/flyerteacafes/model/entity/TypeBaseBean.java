package com.ideal.flyerteacafes.model.entity;

import java.util.List;

/**
 * Created by fly on 2017/6/28.
 */

public class TypeBaseBean {

    private TypeInfoBean1 CAT_2;//2酒店攻略",
    private TypeInfoBean1 CAT_3;//3航空攻略
    private TypeInfoBean1 CAT_125;//酒店report
    private TypeInfoBean1 CAT_130;//飞行report
    private TypeInfoBean1 CAT_314;//314信用卡攻略
    private TypeInfoBean1 CAT_322;//322优惠


    public TypeInfoBean1 getHotelRaiders() {
        return CAT_2;
    }


    public TypeInfoBean1 getAviationRaiders() {
        return CAT_3;
    }


    public TypeInfoBean1 getHotelReport() {
        return CAT_125;
    }

    public TypeInfoBean1 getAviationReport() {
        return CAT_130;
    }


    public TypeInfoBean1 getCardRaiders() {
        return CAT_314;
    }


    public TypeInfoBean1 getDiscountRaiders() {
        return CAT_322;
    }


    public static class TypeInfoBean1 {

        private String catid;
        private String upid;
        private String catname;
        private String articles;
        private String allowcomment;
        private String displayorder;
        private String keyword;
        private String sortid;
        private String level;
        private String topid;
        private List<TypeInfoBean2> sortoption;

        public String getCatid() {
            return catid;
        }

        public String getUpid() {
            return upid;
        }

        public String getCatname() {
            return catname;
        }

        public String getArticles() {
            return articles;
        }

        public String getAllowcomment() {
            return allowcomment;
        }

        public String getDisplayorder() {
            return displayorder;
        }

        public String getKeyword() {
            return keyword;
        }

        public String getSortid() {
            return sortid;
        }

        public String getLevel() {
            return level;
        }

        public String getTopid() {
            return topid;
        }

        public List<TypeInfoBean2> getSortoption() {
            return sortoption;
        }
    }

    public static class TypeInfoBean2 {
        private String name;
        private String identifier;
        private String optionid;
        private List<TypeInfo> subchoices;


        public String getName() {
            return name;
        }

        public String getIdentifier() {
            return identifier;
        }

        public String getOptionid() {
            return optionid;
        }

        public List<TypeInfo> getSubchoices() {
            return subchoices;
        }
    }


    public static class TypeInfo {
        private String cid;
        private String name;
        private String pid;
        private List<TypeInfo> subchoices;


        public String getCid() {
            return cid;
        }

        public String getName() {
            return name;
        }

        public String getPid() {
            return pid;
        }

        public List<TypeInfo> getSubchoices() {
            return subchoices;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public void setSubchoices(List<TypeInfo> subchoices) {
            this.subchoices = subchoices;
        }
    }
}
