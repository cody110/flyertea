package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 帖子详情
 *
 * @author fly
 */
public class ThreadDetailsBean extends BaseBean implements Serializable {


    private int pid;

    private int fid;

    // 标识对应子模块分类对应帖子id
    private int tid;

    // 发帖用户名称
    private String author;

    //作者ID
    private String authorid;

    // 发帖时间戳
    private String dbdateline;

    // 此贴的最新回复数
    private int replies;

    // 此贴对应的标题或摘要
    private String subject;

    // 正文
    private String message;

    private int isflower;

    //鲜花数量
    private int flowers;

    //浏览数量
    private int views;

    //论坛id
    private String groupid;

    private String authortitle;

    private String has_sm;

    private String isfriend;

    private String avatar;

    private String location;

    private String favtimes;

    //>0已收藏
    private String favid;

    private String islike;

    private String gender;

    private String forumname;

    private String subforumname;

    private String forumicon;

    private boolean professional;

    private String bestable;
    private String excellentable;

    private String typeid;

    private String bestpid;//最佳答案的pid

    private TopicBean collection;

    private List<ThreadTagBean> tags;

    private String digest;
    private String pushedaid;
    private String heatlevel;

    private List<VideoInfo> videos;

    public List<VideoInfo> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoInfo> videos) {
        this.videos = videos;
    }

    public String getDigest() {
        return digest;
    }

    public String getPushedaid() {
        return pushedaid;
    }

    public String getHeatlevel() {
        return heatlevel;
    }

    public List<ThreadTagBean> getTags() {
        return tags;
    }

    public TopicBean getCollection() {
        return collection;
    }

    public void setBestable(String bestable) {
        this.bestable = bestable;
    }

    public String getTypeid() {
        return typeid;
    }

    public String getBestable() {
        return bestable;
    }

    public String getExcellentable() {
        return excellentable;
    }

    public boolean isProfessional() {
        return professional;
    }

    public void setProfessional(boolean professional) {
        this.professional = professional;
    }

    public String getForumicon() {
        return forumicon;
    }

    public void setForumicon(String forumicon) {
        this.forumicon = forumicon;
    }

    public String getForumname() {
        return forumname;
    }

    public void setForumname(String forumname) {
        this.forumname = forumname;
    }

    public String getSubforumname() {
        return subforumname;
    }

    public void setSubforumname(String subforumname) {
        this.subforumname = subforumname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFavid() {
        return favid;
    }

    public void setFavid(String favid) {
        this.favid = favid;
    }

    public String getIslike() {
        return islike;
    }

    public void setIslike(String islike) {
        this.islike = islike;
    }

    public String getFavtimes() {
        return favtimes;
    }

    public void setFavtimes(String favtimes) {
        this.favtimes = favtimes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private String adv;

    public int getFlowers() {
        return flowers;
    }

    public void setFlowers(int flowers) {
        this.flowers = flowers;
    }

    public String getAdv() {
        return adv;
    }

    public void setAdv(String adv) {
        this.adv = adv;
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

    public String getAuthortitle() {
        return authortitle;
    }

    public void setAuthortitle(String authortitle) {
        this.authortitle = authortitle;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    private List<Attachments> attachments;

    //图片url
    private ArrayList<String> urlList;

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getIsflower() {
        return isflower;
    }

    public void setIsflower(int isflower) {
        this.isflower = isflower;
    }

    public ArrayList<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(ArrayList<String> urlList) {
        this.urlList = urlList;
    }

    public List<Attachments> getAttachList() {
        return attachments;
    }

    public void setAttachList(List<Attachments> attachList) {
        this.attachments = attachList;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }


    private List<ReplyBean> replyList;


    public List<ReplyBean> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<ReplyBean> replyList) {
        this.replyList = replyList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDbdateline() {
        return dbdateline;
    }

    public void setDbdateline(String dbdateline) {
        this.dbdateline = dbdateline;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public static class VideoInfo{
        private String vid;
        private String videourl;

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getVideourl() {
            return videourl;
        }

        public void setVideourl(String videourl) {
            this.videourl = videourl;
        }
    }

}
