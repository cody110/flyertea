package com.ideal.flyerteacafes.model.loca;

import java.io.PrintStream;

/**
 * Created by fly on 2016/11/26.
 */

/**
 * 上传图片附件的信息
 */
public class UploadAttachInfo {

//    "filename": "dfafsdafsd1.jpg",
//            "filesize": 321321,
//            "attachment": "201607/14/173320hk8ulk3uk3lp0kl1.png",
//            "width": 640

    private String filename;
    private String filesize;
    private String attachment;
    private int width;

    public UploadAttachInfo(String filename, String filesize, String attachment, int width) {
        this.filename = filename;
        this.filesize = filesize;
        this.attachment = attachment;
        this.width = width;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
