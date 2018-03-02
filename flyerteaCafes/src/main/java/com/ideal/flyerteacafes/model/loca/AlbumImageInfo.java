package com.ideal.flyerteacafes.model.loca;

import java.io.Serializable;

/**
 * Created by fly on 2016/11/15.
 */

public class AlbumImageInfo implements Serializable {


    private String path;
    private boolean isSelect;


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
