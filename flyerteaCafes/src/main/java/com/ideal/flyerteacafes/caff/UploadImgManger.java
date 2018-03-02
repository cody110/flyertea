package com.ideal.flyerteacafes.caff;

import android.text.TextUtils;

import com.ideal.flyerteacafes.model.loca.UploadImgInfo;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.upyun.block.api.listener.CompleteListener;
import com.upyun.block.api.listener.ProgressListener;
import com.upyun.block.api.main.UploaderManager;
import com.upyun.block.api.utils.UpYunUtils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by fly on 2016/12/5.
 * 上传图片管理类
 */

public class UploadImgManger {


    private static UploadImgManger instance;

    private UploadImgManger() {
    }

    public static UploadImgManger getInstance() {
        if (instance == null) {
            synchronized (UploadImgManger.class) {
                instance = new UploadImgManger();
            }
        }
        return instance;
    }


    private boolean isStop = false;

    public void stopUploadTask() {
        isStop = true;
    }

    /**
     * 上传图片线程池
     */
    private ExecutorService pool = Executors.newFixedThreadPool(3);

    /**
     * 执行下载任务
     *
     * @param array
     */
    public void execute(UploadImgInfo... array) {
        isStop = false;
        for (UploadImgInfo info : array) {
            if (info.getStatus() == UploadImgInfo.STATUS_FAIL || info.getStatus() == UploadImgInfo.STATUS_NOT_BEGIN)
                pool.execute(new UploadImgThread(info));
        }
    }

    public void execute(final List<UploadImgInfo> array) {
        isStop = false;
        for (UploadImgInfo info : array) {
            if (info.getStatus() == UploadImgInfo.STATUS_FAIL || info.getStatus() == UploadImgInfo.STATUS_NOT_BEGIN) {
                pool.execute(new UploadImgThread(info));
            }
        }


    }


    /**
     * 上传图片的线程
     */
    class UploadImgThread extends Thread {


        UploadImgInfo info;

        public UploadImgThread(UploadImgInfo info) {
            this.info = info;
            info.setStatus(UploadImgInfo.STATUS_UPLOAD_ING);
        }

        @Override
        public void run() {
            super.run();

            if (info == null || TextUtils.isEmpty(info.getBucket()) || TextUtils.isEmpty(info.getFormApiSecret())
                    || TextUtils.isEmpty(info.getLocaPath()) || TextUtils.isEmpty(info.getWebPath())) {
                throw new RuntimeException("上传图片参数非空异常");
            }

            String path = info.getLocaPath();
            if (info.isNeedCompress()) {//需要压缩图片，失败则上传原图
                String compressPath = DataUtils.compressPictures(info.getLocaPath());
                if (!TextUtils.isEmpty(compressPath)) {
                    path = compressPath;
                }
            }
            uploadImage(info.getBucket(), info.getFormApiSecret(), path, info.getWebPath(), info);
        }
    }


    /**
     * 上传图片到u盘云的方法
     *
     * @param bucket
     * @param formApiSecret
     * @param locaPath
     * @param webPath
     */
    private void uploadImage(String bucket, String formApiSecret, String locaPath, String webPath, final UploadImgInfo info) {

        File localFile = new File(locaPath);
        UploaderManager uploaderManager = UploaderManager
                .getInstance(bucket);
        uploaderManager.setConnectTimeout(60);
        uploaderManager.setResponseTimeout(60);
        try {
            Map<String, Object> paramsMap = uploaderManager
                    .fetchFileInfoDictionaryWith(localFile, webPath);
            // 还可以加上其他的额外处理参数...
            paramsMap.put("return_url", "http://httpbin.org/get");
            // signature & policy 建议从服务端获取
            String policyForInitial = UpYunUtils.getPolicy(paramsMap);
            String signatureForInitial = UpYunUtils.getSignature(paramsMap,
                    formApiSecret);

            uploaderManager.upload(policyForInitial, signatureForInitial,
                    localFile, new ProgressListener() {
                        @Override
                        public void transferred(long l, long l1) {

                        }
                    }, new CompleteListener() {
                        @Override
                        public void result(boolean b, String s, String s1) {
                            if (!isStop) {
                                if (b) {
                                    info.setStatus(UploadImgInfo.STATUS_SUCCESS);
                                } else {
                                    info.setStatus(UploadImgInfo.STATUS_FAIL);
                                }
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            info.setStatus(UploadImgInfo.STATUS_FAIL);
        }
    }


}
