package com.ideal.flyerteacafes.model.entity;

import com.ideal.flyerteacafes.model.loca.DataBean;

import java.util.List;

/**
 * Created by fly on 2017/9/5.
 */

public class RaidersRootBean extends BaseBean {


    private RaidersBean credit;
    private RaidersBean hotel;
    private RaidersBean airline;
    private RaidersBean hot;

    public RaidersBean getCredit() {
        return credit;
    }

    public RaidersBean getHotel() {
        return hotel;
    }

    public RaidersBean getAirline() {
        return airline;
    }

    public RaidersBean getHot() {
        return hot;
    }

    public static class RaidersBean {
        private String identifier;
        private String name;
        private List<CatsBean> cats;
        private List<ArticlesBean> articles;

        public String getName() {
            return name;
        }

        public List<CatsBean> getCats() {
            return cats;
        }

        public List<ArticlesBean> getArticles() {
            return articles;
        }

        public String getIdentifier() {
            return identifier;
        }
    }


    public static class CatsBean {
        private String name;
        private String icon;
        private String cid;
        private String catid;
        private String sortid;
        private String identifier;

        public String getIdentifier() {
            return identifier;
        }

        public String getCid() {
            return cid;
        }

        public String getSortid() {
            return sortid;
        }

        public String getName() {
            return name;
        }

        public String getCatid() {
            return catid;
        }

        public String getIcon() {
            return icon;
        }
    }


    public static class ArticlesBean {

        private String aid;
        private String type;
        private String subject;
        private String author;
        private String summary;
        private String views;
        private String authorid;
        private String dateline;
        private String[] attachments;

        public String getAid() {
            return aid;
        }

        public String getType() {
            return type;
        }

        public String getSubject() {
            return subject;
        }

        public String getAuthor() {
            return author;
        }

        public String getSummary() {
            return summary;
        }

        public String getViews() {
            return views;
        }

        public String getAuthorid() {
            return authorid;
        }

        public String getDateline() {
            return dateline;
        }

        public String[] getAttachments() {
            return attachments;
        }
    }


}
