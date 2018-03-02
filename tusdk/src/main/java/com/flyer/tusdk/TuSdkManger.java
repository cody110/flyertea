package com.flyer.tusdk;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.flyer.tusdk.utils.Utils;

import org.lasque.tusdk.core.TuSdk;
import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.struct.TuSdkSize;
import org.lasque.tusdk.video.editor.TuSDKVideoImageExtractor;
import org.lasque.tusdk.video.mixer.TuSDKMediaDataSource;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 又拍云短视频管理类
 * Created by fly on 2018/1/16.
 */

public class TuSdkManger {

    //低清 标清 高清 超清
    public static final int LOW = 1, SD = 2, HIGH = 3, SUPER = 4;


    private static TuSdkManger instance;

    private TuSdkManger() {
    }

    public static TuSdkManger getInstance() {
        if (instance == null) {
            synchronized (TuSdkManger.class) {
                instance = new TuSdkManger();
            }
        }
        return instance;
    }

    /**
     * 配置信息
     */
    private Config config;

    public Config getConfig() {
        return config;
    }


    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context, Config config) {
        TuSdk.enableDebugLog(true);
        // 初始化SDK (请将目标项目所对应的密钥放在这里)
        TuSdk.init(context, "a249d35f5a338c1c-03-cognr1");
        this.config = config;

    }


    /**
     * 根据时间搓生成文件名字
     *
     * @param currentTimeMillis
     * @return
     */
    public String getAttachmentName(long currentTimeMillis) {
        Date date = new Date(currentTimeMillis);
        StringBuffer sb = new StringBuffer();
        sb.append(date.getYear() + 1900);
        sb.append("/");
        sb.append(String.format("%0" + 2 + "d", date.getMonth()));
        sb.append("/");
        sb.append(String.format("%0" + 2 + "d", date.getDay()));
        sb.append("/");
        sb.append(date.getHours());
        sb.append(date.getMinutes());
        sb.append(date.getSeconds());
        sb.append(Utils.getRandomString(16));
        return sb.toString();

    }


    /**
     * 视频质量,默认高清
     *
     * @param status
     */
    public void setConfigQuality(int status) {
        switch (status) {
            case LOW:
                Config.EDITORWIDTH = 640;
                Config.EDITORHEIGHT = 360;
                Config.EDITORBITRATE = 384;
                Config.EDITORFPS = 15;
                break;
            case SD:
                Config.EDITORWIDTH = 854;
                Config.EDITORHEIGHT = 480;
                Config.EDITORBITRATE = 512;
                Config.EDITORFPS = 20;
                break;
            case HIGH:
                Config.EDITORWIDTH = 1280;
                Config.EDITORHEIGHT = 720;
                Config.EDITORBITRATE = 1152;
                Config.EDITORFPS = 25;
                break;
            case SUPER:
                Config.EDITORWIDTH = 1920;
                Config.EDITORHEIGHT = 1080;
                Config.EDITORBITRATE = 2560;
                Config.EDITORFPS = 30;
                break;
        }
    }


    /**
     * 根据视频路径生成视频名称
     *
     * @param videoPath
     */
    public String getThumbnailName(String videoPath) {
        if (!TextUtils.isEmpty(videoPath)) ;
        {
            if (videoPath.contains("/") && videoPath.contains(".")) {
                String[] array = videoPath.split("/");
                String videoName = array[array.length - 1];
                return videoName.substring(0, videoName.lastIndexOf(".")) + ".jpg";
            }

        }
        return "thumbnail.jpg";
    }


    /**
     * 获取缩略图路劲
     *
     * @param mVideoPath
     * @return
     */
    public String getThumbnailPath(String mVideoPath) {
        return config.getLocalCachePath() + "/" + getThumbnailName(mVideoPath);
    }


    /**
     * 保存缩略图
     *
     * @param mVideoPath
     * @return
     */
    public String saveThumbnailImage(Activity activity,final String mVideoPath) {
        return saveThumbnailImage(activity,mVideoPath, null);
    }


    /**
     * 保存缩略图
     *
     * @param mVideoPath 视频文件路径
     */
    public String saveThumbnailImage(Activity activity, final String mVideoPath, final iSaveThumbnailListener listener) {
        final String path = getThumbnailPath(mVideoPath);
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int w_screen = dm.widthPixels;
        TuSdkSize tuSdkSize = TuSdkSize.create(w_screen, w_screen);

        TuSDKVideoImageExtractor extractor = TuSDKVideoImageExtractor.createExtractor();

        extractor.setOutputImageSize(tuSdkSize)
                .setVideoDataSource(TuSDKMediaDataSource.create(mVideoPath))
                .setExtractFrameCount(1);

        extractor.asyncExtractImageList(new TuSDKVideoImageExtractor.TuSDKVideoImageExtractorDelegate() {
            @Override
            public void onVideoImageListDidLoaded(List<Bitmap> images) {
                if (images != null && images.size() > 0) {
                    File file = new File(path);
                    boolean bol = Utils.saveMyBitmap(file, images.get(0));
                    if (listener != null) {
                        listener.saveThumbnailStatus(bol);
                    }
                }
            }

            @Override
            public void onVideoNewImageLoaded(Bitmap bitmap) {

            }
        });
        return path;
    }


    /**
     * 保存缩略图结果通知
     */
    public interface iSaveThumbnailListener {
        void saveThumbnailStatus(boolean bol);
    }


}
