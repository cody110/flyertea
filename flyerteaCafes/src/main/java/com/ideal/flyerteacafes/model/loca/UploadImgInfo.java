package com.ideal.flyerteacafes.model.loca;

import com.ideal.flyerteacafes.utils.Utils;

import java.io.Serializable;

/**
 * Created by fly on 2016/12/5.
 * 上传到u拍云需要的信息
 */

public class UploadImgInfo implements Serializable {


    public IUploadStatus iUploadStatus;

    public interface IUploadStatus {
        void uploadStatusChange(UploadImgInfo info);
    }

    public IUploadStatus getiUploadStatus() {
        return iUploadStatus;
    }

    public void setiUploadStatus(IUploadStatus iUploadStatus) {
        this.iUploadStatus = iUploadStatus;
    }

    /**
     * bucket  U盘云的秘钥
     * formApiSecret U盘云的密匙
     * locaPath 本地图片路径
     * webPath 服务器路径
     */
    private String bucket, formApiSecret, locaPath, webPath;

    /**
     * 上传状态
     */
    private int status = STATUS_NOT_BEGIN;
    public static final int STATUS_NOT_BEGIN = 0, STATUS_UPLOAD_ING = 1, STATUS_SUCCESS = 2, STATUS_FAIL = 3;

    /**
     * 在帖子中占的次数
     */
    private int time = 1;


    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    /**
     * 是否需要压缩
     */
    private boolean isNeedCompress = false;


    public UploadImgInfo(String locaPath, String webPath) {
        this.locaPath = locaPath;
        this.webPath = webPath;
    }


    /**
     * 头像
     */
    public UploadImgInfo uploadFace() {
        bucket = Utils.bucket;
        formApiSecret = Utils.formApiSecret;
        return this;
    }

    /**
     * 直播上传图片
     */
    public UploadImgInfo uploadFeed() {
        bucket = "flyertea-app";
        formApiSecret = "9GmD4rotirmOPrxhO6b0NOx4NRk=";
        return this;
    }

    /**
     * 帖子上传图片
     */
    public UploadImgInfo uploadThread() {
        bucket = "flyerteaphoto";
        formApiSecret = "t2BcNsAtV1+vRKJDTAtiQgD/hv0=";
        return this;
    }

    public String getBucket() {
        return bucket;
    }

    public String getFormApiSecret() {
        return formApiSecret;
    }

    public String getLocaPath() {
        return locaPath;
    }

    public String getWebPath() {
        return webPath;
    }

    public boolean isNeedCompress() {
        return isNeedCompress;
    }

    public void setNeedCompress(boolean needCompress) {
        isNeedCompress = needCompress;
    }

    public void setLocaPath(String locaPath) {
        this.locaPath = locaPath;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        if (this.status == status) return;
        this.status = status;
        if (iUploadStatus != null) {
            iUploadStatus.uploadStatusChange(this);
        }
    }

    @Override
    public String toString() {
        return "UploadImgInfo{" +
                "iUploadStatus=" + iUploadStatus +
                ", bucket='" + bucket + '\'' +
                ", formApiSecret='" + formApiSecret + '\'' +
                ", locaPath='" + locaPath + '\'' +
                ", webPath='" + webPath + '\'' +
                ", status=" + status +
                ", time=" + time +
                ", isNeedCompress=" + isNeedCompress +
                '}';
    }
}
