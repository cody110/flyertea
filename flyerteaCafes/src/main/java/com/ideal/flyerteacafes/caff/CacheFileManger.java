package com.ideal.flyerteacafes.caff;

import android.text.TextUtils;

import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.ApplicationTools;
import com.ideal.flyerteacafes.utils.tools.FolderUtils;

import org.xutils.cache.DiskCacheEntity;
import org.xutils.cache.LruDiskCache;
import org.xutils.common.util.FileUtil;
import org.xutils.x;

import java.io.File;

/**
 * Created by fly on 2016/10/28.
 * 缓存文件管理类
 */

public class CacheFileManger {


    private static final String CACHE_DIR_NAME = "xUtils_img";

    /**
     * 获取应用文件夹路径
     *
     * @return
     */
    public static final String getFlyPath() {
        return SharedPreferencesString.getInstances().getStringToKey("sdPath");
    }

    /**
     * 获取保存图片文件夹路径
     *
     * @return
     */
    public static final String getSavePath() {
        return getFlyPath() + "/飞客茶馆";
    }

    /**
     * 获取缓存视频文件夹信息
     *
     * @return
     */
    public static final String getCacheVideoPath() {
        return getFlyPath() + "/cacheVideo";
    }


    /**
     * 获取缓存图片文件夹信息
     *
     * @return
     */
    public static final String getCacheImagePath() {
        return getFlyPath() + "/cacheImage";
    }


    /**
     * 数据缓存文件夹
     *
     * @return
     */
    public static final String getDataCachePath() {
        return getFlyPath() + "/cacheData";
    }

    /**
     * 缓存文件信息初始化
     */
    public static final void init() {

        if (TextUtils.isEmpty(getFlyPath())) {
            setFlyPath();
        }

        File file = new File(getFlyPath());
        if (!file.exists()) {
            setFlyPath();
            file = new File(getFlyPath());//path有可能变更，必须重新生成文件夹
            if (!file.exists()) {
                file.mkdirs();
            }
        }

        File cacheFile = new File(getCacheImagePath());
        if (!cacheFile.exists()) {
            FolderUtils.deleteFolderFile(getFlyPath(), false);/**缓存文件夹位置变更，先清除掉其他缓存*/
            cacheFile.mkdirs();
        }

        File saveFile = new File(getSavePath());
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        
        File dataCacheFile = new File(getDataCachePath());
        if (!dataCacheFile.exists()) {
            dataCacheFile.mkdirs();
        }


        File videoCacheFile = new File(getCacheVideoPath());
        if (!videoCacheFile.exists()) {
            videoCacheFile.mkdirs();
        }

    }


    /**
     * 设置缓存根目录
     * sd卡存在就建在sd
     * 不存在，就是apk缓存目录
     */
    private static void setFlyPath() {
        String sdPath = "";
        if (ApplicationTools.sdCardExist()) {
            sdPath = ApplicationTools.getSDPath() + "/"
                    + Utils.ROOT_FILE_NAME;
        } else {
            sdPath = FlyerApplication.getContext().getFilesDir().getPath();
        }
        SharedPreferencesString.getInstances().savaToString("sdPath", sdPath);
        SharedPreferencesString.getInstances().commit();
    }

    /**
     * 获取图片缓存大小
     *
     * @return
     */
    public static final String getCacheImageSize() {
        long my = FolderUtils.getFolderSize(new File(getCacheImagePath()));

        long xutils = FolderUtils.getFolderSize(FileUtil.getCacheDir(CACHE_DIR_NAME));
        my += xutils;

        return (int) my / 1048576 + "M";
    }

    /**
     * 清空图片缓存
     */
    public static final boolean clearCacheImage() {
        x.image().clearCacheFiles();
        return FolderUtils.deleteFolderFile(getCacheImagePath(), false);
    }


}
