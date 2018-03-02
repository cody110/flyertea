package com.ideal.flyerteacafes.model.entity;

/**
 * 帖子信息，比如，主页listview item 内容
 *
 * @author fly
 */
public class InvitationInfo {

    private int id;

    // 论坛子模块对应的id(新帖，热帖，精华帖，推荐 对应 -1 -2 -3 -4 )
    private int fid = 0;

    // 根据tid查询子模块详情
    private int tid;
    // 标题
    private String subject;
    // 作者
    private String author;
    // 回复数
    private int replies;
    // 发布时间
    private String dbdateline;
    //
    private String lastpost;

    // 图片URL
    private String imgUrl;
    // ico的url
    private String icoUrl;

    private String summary;
    // 阅读权限
    private String readperm;

    //头像
    private String face;

    private int typeId;


    private int displayorder;//判断是否置顶	不为0表示置顶，值越大越靠上
    private int digest; //是否为精华帖		不为0表示为精华帖
    private int heatlevel;//是否为热门帖		不为0表示为热门帖

    private String authorid = "";

    private String forum_icon;

    public String getForum_icon() {
        return forum_icon;
    }

    public void setForum_icon(String forum_icon) {
        this.forum_icon = forum_icon;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public int getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(int displayorder) {
        this.displayorder = displayorder;
    }

    public int getDigest() {
        return digest;
    }

    public void setDigest(int digest) {
        this.digest = digest;
    }

    public int getHeatlevel() {
        return heatlevel;
    }

    public void setHeatlevel(int heatlevel) {
        this.heatlevel = heatlevel;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getLastpost() {
        return lastpost;
    }

    public void setLastpost(String lastpost) {
        this.lastpost = lastpost;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getReadperm() {
        return readperm;
    }

    public void setReadperm(String readperm) {
        this.readperm = readperm;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getIcoUrl() {
        return icoUrl;
    }

    public void setIcoUrl(String icoUrl) {
        this.icoUrl = icoUrl;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public String getDbdateline() {
        return dbdateline;
    }

    public void setDbdateline(String dbdateline) {
        this.dbdateline = dbdateline;
    }

    @Override
    public String toString() {
        return "InvitationInfo [subject=" + subject + ", author=" + author
                + ", replies=" + replies + ", dbdateline=" + dbdateline
                + ", imgUrl=" + imgUrl + ", icoUrl=" + icoUrl + "]";
    }

}
