package com.ideal.flyerteacafes.model.loca;

import android.support.annotation.ColorInt;
import android.text.TextUtils;

import com.ideal.flyerteacafes.model.entity.LocationListBean;
import com.ideal.flyerteacafes.model.entity.TagBean;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.DateUtil;
import com.ideal.flyerteacafes.utils.tools.GsonTools;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fly on 2016/11/29.
 * 草稿 保存数据
 */
@Table(name = "DraftInfo")
public class DraftInfo implements Serializable {


    @Column(name = "id", isId = true, autoGen = true)
    private int id;

    /**
     * 用户id
     */
    @Column(name = "uid")
    private String uid;

    /**
     * 论坛 1 2 3
     */
    @Column(name = "fid1")
    private String fid1;

    @Column(name = "fid2")
    private String fid2;

    @Column(name = "fid3")
    private String fid3;

    /**
     * 论坛 1 2 3
     */
    @Column(name = "fname1")
    private String fname1;

    @Column(name = "fname2")
    private String fname2;

    @Column(name = "fname3")
    private String fname3;


    /**
     * 帖子标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 帖子内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 保存草稿的時間
     */
    @Column(name = "time")
    private long time;

    /**
     * 帖子图片
     */
    @Column(name = "imgList")
    private String imgText;

    /**
     * 帖子类型
     */
    @Column(name = "type")
    private int type = TYPE_NORMAL;
    public static final int TYPE_NORMAL = 1, TYPE_MAJOR = 2;

    @Column(name = "subject")
    private String subject;

    @Column(name = "uploadImgInfo")
    private String uploadImgInfo;

    @Column(name = "location")
    private String location;


    /**
     * 茶馆搜索出的位置
     */
    @Column(name = "locationBeanInfo")
    private String locaBean;

    /**
     * 评分跳转需要的tag
     */
    @Column(name = "tagList")
    private String tagText;

    /**
     * 评的分
     */
    @Column(name = "star")
    private float star;

    /**
     * 话题名称
     */
    @Column(name = "topicName")
    private String topicName;

    /**
     * 话题id
     */
    @Column(name = "collectionid")
    private String collectionid;

    /**
     * 机场航班
     */
    @Column(name = "flight")
    private String flight;

    /**
     * 机场航班
     */
    @Column(name = "flightid")
    private String flightid;


    public LocationListBean.LocationBean getLocationBean() {
        return GsonTools.jsonToBean(locaBean, LocationListBean.LocationBean.class);
    }

    public void setLocationBean(LocationListBean.LocationBean bean) {
        this.locaBean = GsonTools.objectToJsonString(bean);
    }

    private List<TagBean> tagList;

    public List<TagBean> getTagList() {
        if (tagList == null)
            tagList = GsonTools.jsonToList(tagText, TagBean.class);
        return tagList;
    }

    public void setTagList(List<TagBean> tagList) {
        this.tagList = tagList;
        tagText = GsonTools.objectToJsonString(tagList);
    }


    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getCollectionid() {
        return collectionid;
    }

    public void setCollectionid(String collectionid) {
        this.collectionid = collectionid;
    }

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public String getFlightid() {
        return flightid;
    }

    public void setFlightid(String flightid) {
        this.flightid = flightid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * 是否是正常帖
     *
     * @return
     */
    public boolean isNormal() {
        return type == TYPE_NORMAL;
    }


    /**
     * 帖子图片字符串，拆分出图片路径
     */
    private List<String> imgList;

    public List<String> getImgList() {
        String[] imgArray = DataUtils.splitMark(imgText);
        if (imgArray != null)
            imgList = Arrays.asList(imgArray);
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
        imgText = DataUtils.splicMark(imgList);
    }


    private List<TuwenInfo> tuwenList;

    public List<TuwenInfo> getTuwenList() {
        if (tuwenList == null)
            tuwenList = GsonTools.jsonToList(imgText, TuwenInfo.class);
        return tuwenList;
    }

    public void setTuwenList(List<TuwenInfo> tuwenList) {
        this.tuwenList = tuwenList;
        imgText = GsonTools.objectToJsonString(tuwenList);
    }

    private List<UploadLocaInfo> uploadImgInfoList;

    public List<UploadLocaInfo> getUploadLocaInfoList() {
        if (uploadImgInfoList == null && !TextUtils.isEmpty(uploadImgInfo)) {
            uploadImgInfoList = GsonTools.jsonToList(uploadImgInfo, UploadLocaInfo.class);
        }
        return uploadImgInfoList;
    }

    public void setUploadImgInfoList(List<UploadImgInfo> uploadImgInfoList) {
        List<UploadLocaInfo> locaInfos = new ArrayList<>();
        for (UploadImgInfo info : uploadImgInfoList) {
            UploadLocaInfo loca = new UploadLocaInfo();
            loca.setLocaPath(info.getLocaPath());
            loca.setWebPath(info.getWebPath());
            loca.setStatus(info.getStatus());
            locaInfos.add(loca);
        }
        uploadImgInfo = GsonTools.objectToJsonString(locaInfos);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImgText() {
        return imgText;
    }

    public void setImgText(String imgText) {
        this.imgText = imgText;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getFid1() {
        return fid1;
    }

    public void setFid1(String fid1) {
        this.fid1 = fid1;
    }

    public String getFid2() {
        return fid2;
    }

    public void setFid2(String fid2) {
        this.fid2 = fid2;
    }

    public String getFid3() {
        return fid3;
    }

    public void setFid3(String fid3) {
        this.fid3 = fid3;
    }

    public String getFname1() {
        return fname1;
    }

    public void setFname1(String fname1) {
        this.fname1 = fname1;
    }

    public String getFname2() {
        return fname2;
    }

    public void setFname2(String fname2) {
        this.fname2 = fname2;
    }

    public String getFname3() {
        return fname3;
    }

    public void setFname3(String fname3) {
        this.fname3 = fname3;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public static class UploadLocaInfo implements Serializable {
        private String locaPath;
        private String webPath;
        private int status;

        public String getLocaPath() {
            return locaPath;
        }

        public void setLocaPath(String locaPath) {
            this.locaPath = locaPath;
        }

        public String getWebPath() {
            return webPath;
        }

        public void setWebPath(String webPath) {
            this.webPath = webPath;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    @Override
    public String toString() {
        return "DraftInfo{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", fid1='" + fid1 + '\'' +
                ", fid2='" + fid2 + '\'' +
                ", fid3='" + fid3 + '\'' +
                ", fname1='" + fname1 + '\'' +
                ", fname2='" + fname2 + '\'' +
                ", fname3='" + fname3 + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", imgText='" + imgText + '\'' +
                ", type=" + type +
                ", subject='" + subject + '\'' +
                ", uploadImgInfo='" + uploadImgInfo + '\'' +
                ", location='" + location + '\'' +
                ", locaBean='" + locaBean + '\'' +
                ", tagText='" + tagText + '\'' +
                ", star=" + star +
                ", topicName='" + topicName + '\'' +
                ", collectionid='" + collectionid + '\'' +
                ", flight='" + flight + '\'' +
                ", flightid='" + flightid + '\'' +
                ", tagList=" + tagList +
                ", imgList=" + imgList +
                ", tuwenList=" + tuwenList +
                ", uploadImgInfoList=" + uploadImgInfoList +
                '}';
    }
}
