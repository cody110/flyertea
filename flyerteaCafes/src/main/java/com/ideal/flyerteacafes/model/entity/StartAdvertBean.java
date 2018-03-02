package com.ideal.flyerteacafes.model.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by fly on 2016/8/16.
 */
@Table(name = "StartAdvertBean")
public class StartAdvertBean {
    @Column(name = "id", isId = true, autoGen = true)
    private int id;
    @Column(name = "img_path")
    private String img_path;
    @Column(name = "url")
    private String url;
    @Column(name = "order_id")
    private String order_id;
    @Column(name = "adtype")
    private String adtype;
    @Column(name = "pvtrackcode")
    private String pvtrackcode;


    public String getPvtrackcode() {
        return pvtrackcode;
    }

    public void setPvtrackcode(String pvtrackcode) {
        this.pvtrackcode = pvtrackcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdtype() {
        return adtype;
    }

    public void setAdtype(String adtype) {
        this.adtype = adtype;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
