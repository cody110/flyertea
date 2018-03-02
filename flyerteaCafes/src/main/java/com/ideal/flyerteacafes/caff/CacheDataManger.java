package com.ideal.flyerteacafes.caff;

import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.FileUtil;
import com.ideal.flyerteacafes.utils.tools.FolderUtils;

/**
 * 数据SD卡缓存管理
 * Created by fly on 2017/9/4.
 */

public class CacheDataManger {

    private static CacheDataManger instance;

    private CacheDataManger() {
    }

    public static CacheDataManger getInstance() {
        if (instance == null) {
            synchronized (CacheDataManger.class) {
                instance = new CacheDataManger();
            }
        }
        return instance;
    }


    /**
     * 根据文件名，返回完整路径
     * @param txtName
     * @return
     */
    public static String getCacheDataBySdcard(String txtName) {
        return FileUtil.readSDFile(CacheFileManger.getDataCachePath() + "/" + txtName + ".txt");
    }

    /**
     * 缓存数据
     * @param txtName
     * @param data
     * @return
     */
    public static boolean cacheDataSdcard(String txtName, String data) {
        String txtPath = CacheFileManger.getDataCachePath() + "/" + txtName + ".txt";
        return FileUtil.writeFileSdcard(txtPath, data);
    }


    /**
     * 退出需要清空对应用户的缓存数据
     */
    public static void clearCacheDataByLoginOut() {
        deleteCacheDataTxt(Utils.HttpRequest.HTTP_FAVORITE);
    }

    /**
     * 根据URL删除txt
     *
     * @param url
     */
    private static void deleteCacheDataTxt(String url) {
        String txtName = DataTools.getMD5(url);
        String txtPath = CacheFileManger.getDataCachePath() + "/" + txtName + ".txt";
        FolderUtils.deleteFolderFile(txtPath, false);
    }


    /**
     * 版本升级，清空缓存
     */
    public static void clearCacheDataByUpgrade(){
        FolderUtils.deleteFolderFile(CacheFileManger.getDataCachePath(), false);
    }


}
