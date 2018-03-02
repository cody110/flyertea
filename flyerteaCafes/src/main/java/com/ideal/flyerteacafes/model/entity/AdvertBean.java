package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;

/**
 * 广告
 *
 * @author fly
 */
@SuppressWarnings("serial")
public class AdvertBean extends BaseBean implements Serializable {

    private int id;

    private String title;
    // 图片路径
    private String img_path;
    // 跳转URL
    private String url;
    // 排序
    private int order_id;

    private String adtype;

    //执行的统计代码
    private String pvtrackcode;

    public String getPvtrackcode() {
        return pvtrackcode;
    }

    public String getAdtype() {
        return adtype;
    }

    public void setAdtype(String adtype) {
        this.adtype = adtype;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return empty(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_path() {
        return empty(img_path);
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getUrl() {
        return empty(url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    @Override
    public String toString() {
        return "AdvertInfo [id=" + id + ", title=" + title + ", img_path="
                + img_path + ", url=" + url + ", order_id=" + order_id + "]";
    }

}
