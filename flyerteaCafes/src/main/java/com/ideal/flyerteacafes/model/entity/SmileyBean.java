package com.ideal.flyerteacafes.model.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 表情
 *
 * @author fly
 */
@Table(name="SmileyBean")
public class SmileyBean extends BaseBean {

    @Column(name = "id",isId = true,autoGen = true)
    private int id;
    //表情code
    @Column(name = "code")
    private String code;
    //表情图片地址
    @Column(name = "image")
    private String image;
    //本地图片id
    @Column(name = "iid")
    private int iid;
    //网络图片路径
    @Column(name = "path")
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    @Override
    public String toString() {
        return "SmileyBean{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", image='" + image + '\'' +
                ", iid=" + iid +
                ", path='" + path + '\'' +
                '}';
    }
}
