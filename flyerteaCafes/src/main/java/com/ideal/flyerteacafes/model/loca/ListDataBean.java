package com.ideal.flyerteacafes.model.loca;

import com.ideal.flyerteacafes.model.entity.BaseBean;

import java.util.List;

/**
 * Created by fly on 2016/5/24.
 */
public class ListDataBean extends BaseBean{


    //总页数
    private int totalpage;

    public boolean getHasNext(){
        return getHas_next()==1;
    }

    private List dataList;

    private String ver;

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public List getDataList() {
        return dataList;
    }

    public void setDataList(List dataList) {
        this.dataList = dataList;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }
}
