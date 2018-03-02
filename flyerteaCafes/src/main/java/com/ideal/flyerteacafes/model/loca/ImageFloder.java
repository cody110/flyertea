package com.ideal.flyerteacafes.model.loca;

import com.ideal.flyerteacafes.model.loca.AlbumItemInfo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ImageFloder implements Serializable {

    public static final int TYPE_IMG=0,TYPE_VIDEO=1;

    private int type=TYPE_IMG;


    /**
     * 图片的文件夹路径
     */
    private String dir;

    /**
     * 第一张图片的路径
     */
    private String firstImagePath;

    /**
     * 文件夹的名称
     */
    private String name;

    /**
     * 图片的数量
     */
    private int count;


    /**
     * 每一个item数据
     */
    private Map<String,AlbumItemInfo> item=new HashMap<>();

    public Map<String, AlbumItemInfo> getItem() {
        return item;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
        int lastIndexOf = this.dir.lastIndexOf("/");
        this.name = this.dir.substring(lastIndexOf);
    }

    public String getFirstImagePath() {
        return firstImagePath;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
