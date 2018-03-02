package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;

/**
 * Created by fly on 2017/5/24.
 */

public class UserProfileBean implements Serializable {

    private String uid;
    private String username;
    private String email;
    private String mobile;
    private JobInfo job;
    private RealnameInfo realename;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public JobInfo getJob() {
        return job;
    }

    public void setJob(JobInfo job) {
        this.job = job;
    }

    public RealnameInfo getRealename() {
        return realename;
    }

    public void setRealename(RealnameInfo realename) {
        this.realename = realename;
    }

    public static class JobInfo implements Serializable {
        private String job_status;
        private String job_type;
        private String job_title;
        private String job_ctype;
        private String company;
        private String status;
        private String profession;


        @Override
        public String toString() {
            return "JobInfo{" +
                    "job_status='" + job_status + '\'' +
                    ", job_type='" + job_type + '\'' +
                    ", job_title='" + job_title + '\'' +
                    ", job_ctype='" + job_ctype + '\'' +
                    ", company='" + company + '\'' +
                    ", status='" + status + '\'' +
                    ", profession='" + profession + '\'' +
                    '}';
        }

        public String getProfession() {
            return profession;
        }

        public String getJob_status() {
            return job_status;
        }

        public void setJob_status(String job_status) {
            this.job_status = job_status;
        }

        public String getJob_type() {
            return job_type;
        }

        public void setJob_type(String job_type) {
            this.job_type = job_type;
        }

        public String getJob_title() {
            return job_title;
        }

        public void setJob_title(String job_title) {
            this.job_title = job_title;
        }

        public String getJob_ctype() {
            return job_ctype;
        }

        public void setJob_ctype(String job_ctype) {
            this.job_ctype = job_ctype;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }


    }

    public static class RealnameInfo implements Serializable {
        private String sex;
        private String birthprovince;
        private String birthcity;
        private String birthdist;
        private String birthcommunity;
        private String address;
        private String userid;
        private String status;
        private String birthyear;
        private String birthmonth;
        private String birthday;
        private String name;
        private String status_desc;

        public String getBirthdist() {
            return birthdist;
        }

        public String getStatus_desc() {
            return status_desc;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthprovince() {
            return birthprovince;
        }

        public void setBirthprovince(String birthprovince) {
            this.birthprovince = birthprovince;
        }

        public String getBirthcity() {
            return birthcity;
        }

        public void setBirthcity(String birthcity) {
            this.birthcity = birthcity;
        }

        public String getBirthcommunity() {
            return birthcommunity;
        }

        public void setBirthcommunity(String birthcommunity) {
            this.birthcommunity = birthcommunity;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBirthyear() {
            return birthyear;
        }

        public void setBirthyear(String birthyear) {
            this.birthyear = birthyear;
        }

        public String getBirthmonth() {
            return birthmonth;
        }

        public void setBirthmonth(String birthmonth) {
            this.birthmonth = birthmonth;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
