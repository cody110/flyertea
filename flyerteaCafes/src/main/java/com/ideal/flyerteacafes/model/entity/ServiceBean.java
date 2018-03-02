package com.ideal.flyerteacafes.model.entity;

import java.util.List;

/**
 * Created by fly on 2017/4/1.
 */

public class ServiceBean {


    /**
     * id : 1
     * parentid : 0
     * name : 飞客服务
     * name_des : 官方自营 品质保证
     * status : 1
     * displayorder : 1
     * sub : [{"id":"1","subject":"星级酒店特价预订","description":"超低价格、累积SNP、无需预付、免费取消","catid":"1","url":"http://www.flyertrip.com","mobileurl":"ttps://m.flyertrip.com/index.html?utm_source=flyerteaApp&amp;utm_medium=referral&amp;utm_content=","cover":"http://ptf.flyert.com/flyerservice/booking@3x.png","mobilecover":"http://ptf.flyert.com/flyerservice/booking@3x.png","starttime":"1438185600","endtime":"0","views":"146","status":"1","platform":"2","dateline":"1490761516","serviceid":"1","subject_cutstr":"星级酒店特价预订"},{"id":"6","subject":"飞客优选商城","description":"优选商品 品质保证","catid":"1","url":"https://wap.koudaitong.com/v2/showcase/homepage?alias=23ht6s7n","mobileurl":"https://wap.koudaitong.com/v2/showcase/homepage?alias=23ht6s7n","cover":"http://ptf.flyert.com/flyerservice/youxuan@3x.png","mobilecover":"http://ptf.flyert.com/flyerservice/youxuan@3x.png","starttime":"1438185600","endtime":"0","views":"0","status":"1","platform":"1","dateline":"1490761515","serviceid":"4","subject_cutstr":"飞客优选商城"},{"id":"4","subject":"飞米返利","description":"酒店、保险、购物都能返利","catid":"1","url":"","mobileurl":"http://fanli.flyertrip.com/plugin.php?mod=wap","cover":"http://ptf.flyert.com/flyerservice/fanli@3x.png","mobilecover":"http://ptf.flyert.com/flyerservice/fanli@3x.png","starttime":"1438185600","endtime":"0","views":"1323","status":"0","platform":"2","dateline":"1490758932","serviceid":"2","subject_cutstr":"飞米返利"},{"id":"5","subject":"飞米商城","description":"飞米兑换航空里程、酒店积分","catid":"1","url":"","mobileurl":"http://139.196.9.164:8080/front/views/html/index.html","cover":"http://ptf.flyert.com/flyerservice/feimistore@3x.png","mobilecover":"http://ptf.flyert.com/flyerservice/feimistore@3x.png","starttime":"1438185600","endtime":"0","views":"0","status":"0","platform":"2","dateline":"1490761453","serviceid":"3","subject_cutstr":"飞米商城"}]
     */

    private String id;
    private String parentid;
    private String name;
    private String name_des;
    private String status;
    private String displayorder;
    private List<SubBean> sub;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_des() {
        return name_des;
    }

    public void setName_des(String name_des) {
        this.name_des = name_des;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(String displayorder) {
        this.displayorder = displayorder;
    }

    public List<SubBean> getSub() {
        return sub;
    }

    public void setSub(List<SubBean> sub) {
        this.sub = sub;
    }

    public static class SubBean {
        /**
         * id : 1
         * subject : 星级酒店特价预订
         * description : 超低价格、累积SNP、无需预付、免费取消
         * catid : 1
         * url : http://www.flyertrip.com
         * mobileurl : ttps://m.flyertrip.com/index.html?utm_source=flyerteaApp&amp;utm_medium=referral&amp;utm_content=
         * cover : http://ptf.flyert.com/flyerservice/booking@3x.png
         * mobilecover : http://ptf.flyert.com/flyerservice/booking@3x.png
         * starttime : 1438185600
         * endtime : 0
         * views : 146
         * status : 1
         * platform : 2
         * dateline : 1490761516
         * serviceid : 1
         * subject_cutstr : 星级酒店特价预订
         */

        private String id;
        private String subject;
        private String description;
        private String catid;
        private String url;
        private String mobileurl;
        private String cover;
        private String mobilecover;
        private String starttime;
        private String endtime;
        private String views;
        private String status;
        private String platform;
        private String dateline;
        private String serviceid;
        private String subject_cutstr;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCatid() {
            return catid;
        }

        public void setCatid(String catid) {
            this.catid = catid;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMobileurl() {
            return mobileurl;
        }

        public void setMobileurl(String mobileurl) {
            this.mobileurl = mobileurl;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getMobilecover() {
            return mobilecover;
        }

        public void setMobilecover(String mobilecover) {
            this.mobilecover = mobilecover;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getViews() {
            return views;
        }

        public void setViews(String views) {
            this.views = views;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getDateline() {
            return dateline;
        }

        public void setDateline(String dateline) {
            this.dateline = dateline;
        }

        public String getServiceid() {
            return serviceid;
        }

        public void setServiceid(String serviceid) {
            this.serviceid = serviceid;
        }

        public String getSubject_cutstr() {
            return subject_cutstr;
        }

        public void setSubject_cutstr(String subject_cutstr) {
            this.subject_cutstr = subject_cutstr;
        }
    }
}
