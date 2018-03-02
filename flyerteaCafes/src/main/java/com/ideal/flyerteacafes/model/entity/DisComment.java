package com.ideal.flyerteacafes.model.entity;

import java.util.ArrayList;
import java.util.List;

/***
 * 飞客惠评论列表
 **/
public class DisComment {

    /*  "count": "1",
        "page": "1",
        "perpage": "10",
        "has_next": "0",
        "postdata": [
            {
                "id": "44",
                "sid": "230",
                "uid": "95033",
                "author": "sll811117",
                "title": "美洲地区优惠，第二晚住宿6折优惠",
                "message": "北美包括美国吗？",
                "pay": "0",
                "moneytype": "",
                "display": "1",
                "dateline": "1443084467",
                "count": "0",
                "face": "http://ptf.flyert.com/avatar/000/09/50/33_avatar_middle.jpg"
            }
        ]*/
    private String count;
    private String page;
    private String perpage;
    private String has_next;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPerpage() {
        return perpage;
    }

    public void setPerpage(String perpage) {
        this.perpage = perpage;
    }

    public String getHas_next() {
        return has_next;
    }

    public void setHas_next(String has_next) {
        this.has_next = has_next;
    }

    List<Comment> postdata = new ArrayList<Comment>();

    public List<Comment> getPostdata() {
        return postdata;
    }

    public void setPostdata(List<Comment> postdata) {
        this.postdata = postdata;
    }

    public static class Comment {

        private String id;
        private String sid;
        private String uid;
        private String author;
        private String title;
        private String message;
        private String pay;
        private String moneytype;
        private String display;
        private String dateline;
        private String count;
        private String face;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPay() {
            return pay;
        }

        public void setPay(String pay) {
            this.pay = pay;
        }

        public String getMoneytype() {
            return moneytype;
        }

        public void setMoneytype(String moneytype) {
            this.moneytype = moneytype;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public String getDateline() {
            return dateline;
        }

        public void setDateline(String dateline) {
            this.dateline = dateline;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }
    }


}
