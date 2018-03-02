package com.ideal.flyerteacafes.model.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 其他用户信息
 *
 * @author fly
 */
@Table(name = "userinfo")
public class Userinfo extends BaseBean implements Serializable {


    @Column(
            name = "ID",
            isId = true,
            autoGen = true
    )
    private int id;
    @Column(name = "uid")
    private String uid;
    @Column(name = "username")
    private String username;
    @Column(name = "face")
    private String face;
    private String credit;
    private String extcredits6;
    private String threads;
    private String posts;
    private String friends;
    private String recentnote;
    private String spacenote;
    private String readaccess;
    private String sightml;
    private String gender;
    private String resideprovince;
    private String residecity;
    private String interest;
    private String field7;
    private String occupation;
    private String bigface;
    private String user_flower;
    private String groupname;
    private String isfriend;//0不是，1是
    private String has_sm;//实名认证  0 没有，1认证中，2认证成功
    private String hastoken;//0没有 1有
    private String Registrationtime;
    private List<MedalsBean> medals;
    private List<MyMedalsSubBean> showmedals;

    public List<MyMedalsSubBean> getShowmedals() {
        return showmedals;
    }

    public List<MedalsBean> getMedals() {
        return medals;
    }

    public String getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(String isfriend) {
        this.isfriend = isfriend;
    }

    public String getHas_sm() {
        return has_sm;
    }

    public void setHas_sm(String has_sm) {
        this.has_sm = has_sm;
    }

    public String getHastoken() {
        return hastoken;
    }

    public void setHastoken(String hastoken) {
        this.hastoken = hastoken;
    }

    public String getGroupname() {
        return empty(groupname);
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getUid() {
        return empty(uid);
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return empty(username);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCredit() {
        return empty(credit);
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getExtcredits6() {
        return empty(extcredits6);
    }

    public void setExtcredits6(String extcredits6) {
        this.extcredits6 = extcredits6;
    }

    public String getThreads() {
        return empty(threads);
    }

    public void setThreads(String threads) {
        this.threads = threads;
    }

    public String getPosts() {
        return empty(posts);
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }

    public String getFriends() {
        return empty(friends);
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }

    public String getRecentnote() {
        return empty(recentnote);
    }

    public void setRecentnote(String recentnote) {
        this.recentnote = recentnote;
    }

    public String getSpacenote() {
        return empty(spacenote);
    }

    public void setSpacenote(String spacenote) {
        this.spacenote = spacenote;
    }

    public String getReadaccess() {
        return empty(readaccess);
    }

    public void setReadaccess(String readaccess) {
        this.readaccess = readaccess;
    }

    public String getSightml() {
        return empty(sightml);
    }

    public void setSightml(String sightml) {
        this.sightml = sightml;
    }

    //	public String getMedals() {
//		return empty(medals);
//	}
//	public void setMedals(String medals) {
//		this.medals = medals;
//	}
    public String getGender() {
        return empty(gender);
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getOccupation() {
        return empty(occupation);
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getFace() {
        return empty(face);
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getBigface() {
        return empty(bigface);
    }

    public void setBigface(String bigface) {
        this.bigface = bigface;
    }

    public String getUser_flower() {
        return empty(user_flower);
    }

    public void setUser_flower(String user_flower) {
        this.user_flower = user_flower;
    }

    public String getRegistrationtime() {
        return Registrationtime;
    }

    public void setRegistrationtime(String registrationtime) {
        Registrationtime = registrationtime;
    }


}
