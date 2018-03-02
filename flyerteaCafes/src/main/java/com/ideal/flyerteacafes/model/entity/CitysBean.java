package com.ideal.flyerteacafes.model.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name="CitysBean")
public class CitysBean {

    @Column(name = "id",isId = true,autoGen = true)
    private int id;
    @Column(name = "cid")
    private int cid;//代表是哪个省的
    @Column(name = "mark")
    private int mark;//0为省级 1为室级
    @Column(name = "city")
    private String city;//名称

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "CitysBean [id=" + id + ", cid=" + cid + ", mark=" + mark
                + ", city=" + city + "]";
    }

}
