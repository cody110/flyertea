package com.ideal.flyerteacafes.model.loca;

import com.ideal.flyerteacafes.adapters.AlbumImageAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2016/11/15.
 */

public class AlbumItemInfo implements Serializable{



    private String dateline;
    private List<String> imgList=new ArrayList<>();
    private int type=ImageFloder.TYPE_IMG;

    public List<String> getImgList() {
        return imgList;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
