package com.ideal.flyerteacafes.model.entity;

import com.google.gson.annotations.SerializedName;
import com.ideal.flyerteacafes.utils.DataUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Cindy on 2016/11/22.
 */
public class ThreadSubjectBean implements Serializable {


    /**
     * 是否是正常帖
     *
     * @return 是否专业模式（0 否 1 是）
     */
    public boolean isNormal() {
        return DataUtils.isNormal(professional);
    }


    private String tid;
    private String fid;
    private String typeid;
    private String author;
    private String authorid;
    private String subject;
    private String lastposter;
    private String views;
    private String replies;
    private String displayorder;
    private String attachment;
    private String moderated;
    private String favtimes;
    private String cover;
    private String hotel_id;
    @SerializedName("new")
    private String newX;
    private String istoday;
    private String dbdateline;
    private String dblastpost;
    private String id;
    private String summary;
    private String forumname;
    private String professional;
    private AuthorprofileBean authorprofile;
    private String type;
    private String pages;
    private String url;
    private String title;
    private String order_id;
    private String sql;
    private List<PostsBean> posts;
    private List<String> attachments;
    private String islike;
    private String flowers;
    private String replycredit;
    private String location;
    private String adtype;
    private String pvtrackcode;
    private String price;
    private String showtypename;


    private String apptemplatetype;
    private String users;
    private String name;
    private String ctid;
    private String desc;

    private String digest;
    private String pushedaid;
    private String heatlevel;
    private ThreadVideoInfo videos;


    public ThreadVideoInfo getVideos() {
        return videos;
    }

    public void setVideos(ThreadVideoInfo videos) {
        this.videos = videos;
    }

    public String getPushedaid() {
        return pushedaid;
    }

    public String getDesc() {
        return desc;
    }

    public String getCtid() {
        return ctid;
    }

    public void setCtid(String ctid) {
        this.ctid = ctid;
    }

    public String getApptemplatetype() {
        return apptemplatetype;
    }

    public void setApptemplatetype(String apptemplatetype) {
        this.apptemplatetype = apptemplatetype;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShowtypename() {
        return showtypename;
    }

    public void setShowtypename(String showtypename) {
        this.showtypename = showtypename;
    }

    public String getPrice() {
        return price;
    }

    public String getPvtrackcode() {
        return pvtrackcode;
    }

    public String getAdtype() {
        return adtype;
    }

    public void setAdtype(String adtype) {
        this.adtype = adtype;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLastposter() {
        return lastposter;
    }

    public void setLastposter(String lastposter) {
        this.lastposter = lastposter;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getReplies() {
        return replies;
    }

    public void setReplies(String replies) {
        this.replies = replies;
    }

    public String getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(String displayorder) {
        this.displayorder = displayorder;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getModerated() {
        return moderated;
    }

    public void setModerated(String moderated) {
        this.moderated = moderated;
    }

    public String getFavtimes() {
        return favtimes;
    }

    public void setFavtimes(String favtimes) {
        this.favtimes = favtimes;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getNewX() {
        return newX;
    }

    public void setNewX(String newX) {
        this.newX = newX;
    }

    public String getHeatlevel() {
        return heatlevel;
    }

    public void setHeatlevel(String heatlevel) {
        this.heatlevel = heatlevel;
    }

    public String getIstoday() {
        return istoday;
    }

    public void setIstoday(String istoday) {
        this.istoday = istoday;
    }

    public String getDbdateline() {
        return dbdateline;
    }

    public void setDbdateline(String dbdateline) {
        this.dbdateline = dbdateline;
    }

    public String getDblastpost() {
        return dblastpost;
    }

    public void setDblastpost(String dblastpost) {
        this.dblastpost = dblastpost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getForumname() {
        return forumname;
    }

    public void setForumname(String forumname) {
        this.forumname = forumname;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public AuthorprofileBean getAuthorprofile() {
        return authorprofile;
    }

    public void setAuthorprofile(AuthorprofileBean authorprofile) {
        this.authorprofile = authorprofile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<PostsBean> getPosts() {
        return posts;
    }

    public void setPosts(List<PostsBean> posts) {
        this.posts = posts;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

    /**
     * 作者信息
     */
    public static class AuthorprofileBean {
        /**
         * groupname : 蓝钻会员
         * groupid : 28
         * gender : 1
         * has_sm : 0
         * avatar : http://ptf.flyert.com/avatar/000/04/68/42_avatar_small.jpg
         */

        private String groupname;
        private String groupid;
        private String gender;
        private String has_sm;
        private String avatar;

        public String getGroupname() {
            return groupname;
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
        }

        public String getGroupid() {
            return groupid;
        }

        public void setGroupid(String groupid) {
            this.groupid = groupid;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getHas_sm() {
            return has_sm;
        }

        public void setHas_sm(String has_sm) {
            this.has_sm = has_sm;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    /**
     * 回复
     */
    public static class PostsBean {
        /**
         * pid : 10836597
         * tid : 874918
         * fid : 98
         * author : kimi35
         * authorid : 139304
         * quote :
         * toauthor :
         * message : 抢个沙发，很不错的游记，点赞
         */

        private String pid;
        private String tid;
        private String fid;
        private String author;
        private String authorid;
        private String quote;
        private String toauthor;
        private String message;

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAuthorid() {
            return authorid;
        }

        public void setAuthorid(String authorid) {
            this.authorid = authorid;
        }

        public String getQuote() {
            return quote;
        }

        public void setQuote(String quote) {
            this.quote = quote;
        }

        public String getToauthor() {
            return toauthor;
        }

        public void setToauthor(String toauthor) {
            this.toauthor = toauthor;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public String getIslike() {
        return islike;
    }

    public void setIslike(String islike) {
        this.islike = islike;
    }

    public String getFlowers() {
        return flowers;
    }

    public void setFlowers(String flowers) {
        this.flowers = flowers;
    }

    public String getReplycredit() {
        return replycredit;
    }

    public void setReplycredit(String replycredit) {
        this.replycredit = replycredit;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
