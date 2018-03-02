package com.ideal.flyerteacafes.model.entity;

import java.util.List;

public class SearchMemberBean {

    private String count;
    private String page;
    private String pagesize;
    private String formhash;

    private List<SearchMemberInfo> list;


    public String getFormhash() {
        return formhash;
    }


    public void setFormhash(String formhash) {
        this.formhash = formhash;
    }


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


    public String getPagesize() {
        return pagesize;
    }


    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }


    public List<SearchMemberInfo> getList() {
        return list;
    }


    public void setList(List<SearchMemberInfo> list) {
        this.list = list;
    }


    public static class SearchMemberInfo {

//	    "uid": "58",
//	    "username": "flyworld",
//	    "status": "0",
//	    "mobilestatus": "0",
//	    "emailstatus": "1",
//	    "avatarstatus": "1",
//	    "adminid": "2",
//	    "groupid": "2",
//	    "regdate": "1255698000",
//	    "credits": "3234",
//	    "mapx": "23",
//	    "mapy": "34",
//	    "locationtime": "1442041172",
//	    "isfriend": "0",
//	    "follow": "0",
//	    "face": "http://ptf.flyert.com/avatar/000/00/00/58_avatar_middle.jpg",
//	    "groupname": "羽客职员"

        private String uid;
        private String username;
        private String status;
        private String mobilestatus;
        private String emailstatus;
        private String avatarstatus;
        private String adminid;
        private String groupid;
        private String regdate;
        private String isfriend;
        private String face;

        private String sightml;

        public String getSightml() {
            return sightml;
        }

        public void setSightml(String sightml) {
            this.sightml = sightml;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMobilestatus() {
            return mobilestatus;
        }

        public void setMobilestatus(String mobilestatus) {
            this.mobilestatus = mobilestatus;
        }

        public String getEmailstatus() {
            return emailstatus;
        }

        public void setEmailstatus(String emailstatus) {
            this.emailstatus = emailstatus;
        }

        public String getAvatarstatus() {
            return avatarstatus;
        }

        public void setAvatarstatus(String avatarstatus) {
            this.avatarstatus = avatarstatus;
        }

        public String getAdminid() {
            return adminid;
        }

        public void setAdminid(String adminid) {
            this.adminid = adminid;
        }

        public String getGroupid() {
            return groupid;
        }

        public void setGroupid(String groupid) {
            this.groupid = groupid;
        }

        public String getRegdate() {
            return regdate;
        }

        public void setRegdate(String regdate) {
            this.regdate = regdate;
        }

        public String getIsfriend() {
            return isfriend;
        }

        public void setIsfriend(String isfriend) {
            this.isfriend = isfriend;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }
    }

}
