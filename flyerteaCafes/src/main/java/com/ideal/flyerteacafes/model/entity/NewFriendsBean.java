package com.ideal.flyerteacafes.model.entity;

import java.util.List;

public class NewFriendsBean extends BaseBean {

    private List<NewFriendsInfo> list;

    public List<NewFriendsInfo> getList() {
        return list;
    }


    public void setList(List<NewFriendsInfo> list) {
        this.list = list;
    }

    public static class NewFriendsInfo extends BaseBean {

        private String dateline;

        private String face;

        private String fuid;

        private String fusername;

        private String gid;

        private String groupname;

        private String note;

        private String uid;

        public String getDateline() {
            return empty(dateline);
        }

        public void setDateline(String dateline) {
            this.dateline = dateline;
        }

        public String getFace() {
            return empty(face);
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getFuid() {
            return empty(fuid);
        }

        public void setFuid(String fuid) {
            this.fuid = fuid;
        }

        public String getFusername() {
            return empty(fusername);
        }

        public void setFusername(String fusername) {
            this.fusername = fusername;
        }

        public String getGid() {
            return empty(gid);
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getGroupname() {
            return empty(groupname);
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
        }

        public String getNote() {
            return empty(note);
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getUid() {
            return empty(uid);
        }

        public void setUid(String uid) {
            this.uid = uid;
        }


    }


}
