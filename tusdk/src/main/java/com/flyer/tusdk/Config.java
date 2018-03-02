package com.flyer.tusdk;

import org.lasque.tusdk.core.utils.TuSdkWaterMarkOption;

public class Config {

    //空间名
    public final static String BUCKET = "flyerteaphoto";
    //操作员
    public final static String OPERATER = "dale";
    //密码
    public final static String PASSWORD = "Icepoint1";


    public static int RECORDWIDTH;
    public static int RECORHEIGHT;
    public static int RECORBITRATE;
    public static int RECORDFPS;


    public static TuSdkWaterMarkOption.WaterMarkPosition POSITION;


    /**
     * 视频质量，默认高清
     */
    public static int EDITORWIDTH = 1280;
    public static int EDITORHEIGHT = 720;
    public static int EDITORBITRATE = 2560;//视频清晰度主要和比特率有关,不清晰就调高他
    public static int EDITORFPS = 25;


    /**
     * 本地缓存路径
     */
    private String localCachePath;

    public String getLocalCachePath() {
        return localCachePath;
    }

    public void setLocalCachePath(String path) {
        localCachePath = path;
    }


}
