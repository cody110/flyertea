package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 *
 * @author fly
 */
@SuppressWarnings("serial")
public class UserBean extends BaseBean implements Serializable {

    public static String bind = "yes";


    //防止重复提交,方便传输，跟用户信息无关
    private String formhash;

    //标识对应用户的id
    private String member_uid;
    //用户名
    private String member_username;
    //用户头像URL地址
    private String face;
    //v用户常用邮箱
    private String email;
    //v用户性别 1男2女
    private int gender;
    //用户论坛等级
    private int groupid;
    private String groupname;
    private String groupicon;
    //用户阅读权限值
    private int readaccess;
    //用户论坛积分
    private int credits;
    //用户飞米值
    private String extcredits6;
    //用户鲜花数
    private String user_flower;
    //
    private String cookiepre;

    private String token;//融云


    //省
    private String resideprovince;
    //市
    private String residecity;
    //兴趣
    private String interest;
    // 行业
    private String field7;
    //签名
    private String sightml;
    //公司
    private String company;
    //职业
    private String occupation;

    private String has_sm;//实名认证  0 没有，1认证中，2认证成功


    private String bind_webchat;
    private String bind_weibo;
    private String bind_qq;
    private String bind_mobile;
    private String bind_mobile_no;
    private String bind_email;
    private String bind_code;//绑定结果码
    private String bind_result;//绑定结果描述
    private String mobile;
    private String password_status;
    private String authed;//是否实名认证
    private String medals;//勋章

    private String allowpostvideo;//1 表示可以发视频贴 0 表示不可以


    public String getAllowpostvideo() {
        return allowpostvideo;
    }

    public void setAllowpostvideo(String allowpostvideo) {
        this.allowpostvideo = allowpostvideo;
    }

    public String getMedals() {
        return medals;
    }

    private List<Extgroups> extgroups;

    private Token ps_token;

    public String getAuthed() {
        return authed;
    }

    public void setAuthed(String authed) {
        this.authed = authed;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword_status() {
        return password_status;
    }

    public String getBind_email() {
        return bind_email;
    }

    public String getGroupicon() {
        return groupicon;
    }

    public static String getBind() {
        return bind;
    }

    public List<Extgroups> getExtgroups() {
        return extgroups;
    }

    public Token getPs_token() {
        return ps_token;
    }

    public String getBind_code() {
        return bind_code;
    }

    public void setBind_code(String bind_code) {
        this.bind_code = bind_code;
    }

    public String getBind_result() {
        return bind_result;
    }

    public void setBind_result(String bind_result) {
        this.bind_result = bind_result;
    }

    public String getFormhash() {
        return empty(formhash);
    }

    public void setFormhash(String formhash) {
        this.formhash = formhash;
    }

    public String getMember_uid() {
        return empty(member_uid);
    }

    public void setMember_uid(String member_uid) {
        this.member_uid = member_uid;
    }

    public String getMember_username() {
        return empty(member_username);
    }

    public void setMember_username(String member_username) {
        this.member_username = member_username;
    }

    public String getFace() {
        return empty(face);
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getEmail() {
        return empty(email);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return empty(groupname);
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public int getReadaccess() {
        return readaccess;
    }

    public void setReadaccess(int readaccess) {
        this.readaccess = readaccess;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getExtcredits6() {
        return empty(extcredits6);
    }

    public void setExtcredits6(String extcredits6) {
        this.extcredits6 = extcredits6;
    }

    public String getUser_flower() {
        return empty(user_flower);
    }

    public void setUser_flower(String user_flower) {
        this.user_flower = user_flower;
    }

    public String getCookiepre() {
        return empty(cookiepre);
    }

    public void setCookiepre(String cookiepre) {
        this.cookiepre = cookiepre;
    }

    public String getToken() {
        return empty(token);
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getResideprovince() {
        return empty(resideprovince);
    }

    public void setResideprovince(String resideprovince) {
        this.resideprovince = resideprovince;
    }

    public String getResidecity() {
        return empty(residecity);
    }

    public void setResidecity(String residecity) {
        this.residecity = residecity;
    }

    public String getInterest() {
        return empty(interest);
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getField7() {
        return empty(field7);
    }

    public void setField7(String field7) {
        this.field7 = field7;
    }

    public String getSightml() {
        return empty(sightml);
    }

    public void setSightml(String sightml) {
        this.sightml = sightml;
    }

    public String getCompany() {
        return empty(company);
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getOccupation() {
        return empty(occupation);
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getHas_sm() {
        return empty(has_sm);
    }

    public void setHas_sm(String has_sm) {
        this.has_sm = has_sm;
    }

    public String getBind_webchat() {
        return empty(bind_webchat);
    }

    public void setBind_webchat(String bind_webchat) {
        this.bind_webchat = bind_webchat;
    }

    public String getBind_weibo() {
        return empty(bind_weibo);
    }

    public void setBind_weibo(String bind_weibo) {
        this.bind_weibo = bind_weibo;
    }

    public String getBind_qq() {
        return empty(bind_qq);
    }

    public void setBind_qq(String bind_qq) {
        this.bind_qq = bind_qq;
    }

    public String getBind_mobile() {
        return empty(bind_mobile);
    }

    public void setBind_mobile(String bind_mobile) {
        this.bind_mobile = bind_mobile;
    }

    public String getBind_mobile_no() {
        return empty(bind_mobile_no);
    }

    public void setBind_mobile_no(String bind_mobile_no) {
        this.bind_mobile_no = bind_mobile_no;
    }


    public static class Token implements Serializable {
        private String access_token;
        private String expires_in;
        private String refresh_token;
        private String token_type;

        public String getAccess_token() {
            return access_token;
        }

        public String getExpires_in() {
            return expires_in;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public String getToken_type() {
            return token_type;
        }
    }

    public static class Extgroups implements Serializable {
        private String groupicon;
        private String groupid;
        private String groupname;

        public String getGroupicon() {
            return groupicon;
        }

        public String getGroupid() {
            return groupid;
        }

        public String getGroupname() {
            return groupname;
        }
    }

}
