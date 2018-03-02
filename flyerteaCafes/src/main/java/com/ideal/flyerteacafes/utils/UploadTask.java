package com.ideal.flyerteacafes.utils;

import android.os.AsyncTask;

import com.ideal.flyerteacafes.utils.tools.DateUtil;
import com.upyun.block.api.listener.CompleteListener;
import com.upyun.block.api.listener.ProgressListener;
import com.upyun.block.api.main.UploaderManager;
import com.upyun.block.api.utils.UpYunUtils;

import java.io.File;
import java.util.Date;
import java.util.Map;

/**
 * 上传图片的u盘云的封装
 */
public class UploadTask extends AsyncTask<String, Void, String> {


    private IUploadStatus noticeSucces;

    public UploadTask setIUploadStatus(IUploadStatus noticeSuccess) {
        noticeSucces = noticeSuccess;
        return this;
    }

    public interface IUploadStatus {
        void uploadStatus(boolean result, Object data);
    }

    /**
     * 传入的标识，通过callback返回
     */
    private Object status;
    private String bucket;
    private String formApiSecret;

    // 保存到又拍云的路径

    String savePath = null;

    /**
     * 头像
     *
     * @param userId
     * @param num    标识哪种size的头像
     */
    public UploadTask uploadFace(String userId, int num) {
        savePath = splitChar(userId, num);
        bucket = Utils.bucket;
        formApiSecret = Utils.formApiSecret;
        return this;
    }

    /**
     * 直播上传图片
     *
     * @param status   图片在list里的下标
     * @param savePath 路径
     */
    public UploadTask uploadFeed(Object status, String savePath) {
        this.savePath = savePath;
        this.status = status;
        bucket = "flyertea-app";
        formApiSecret = "9GmD4rotirmOPrxhO6b0NOx4NRk=";
        return this;
    }

    public UploadTask uploadThread(String savePath) {
        this.savePath = savePath;
        bucket = "flyerteaphoto";
        formApiSecret = "t2BcNsAtV1+vRKJDTAtiQgD/hv0=";
        return this;
    }

    /**
     * 帖子上传图片
     */
    public UploadTask uploadThread() {
        bucket = "flyerteaphoto";
        formApiSecret = "t2BcNsAtV1+vRKJDTAtiQgD/hv0=";
        return this;
    }


    /**
     * 上传到u盘云
     *
     * @param params 参数 第一个参数为本地路径，第二个为上传路径
     * @return
     */
    @Override
    protected String doInBackground(String... params) {
        File localFile = new File(params[0]);
        if (params.length == 2) {
            savePath = params[1];
        }
        try {
            /*
             * 设置进度条回掉函数
			 * 
			 * 注意：由于在计算发送的字节数中包含了图片以外的其他信息，最终上传的大小总是大于图片实际大小，
			 * 为了解决这个问题，代码会判断如果实际传送的大小大于图片 ，就将实际传送的大小设置成'fileSize-1000'（最小为0）
			 */
            ProgressListener progressListener = new ProgressListener() {
                @Override
                public void transferred(long transferedBytes, long totalBytes) {
                    // do something...
                    System.out.println("trans:" + transferedBytes + "; total:"
                            + totalBytes);
                }
            };

            CompleteListener completeListener = new CompleteListener() {
                @Override
                public void result(boolean isComplete, String result,
                                   String error) {
                    // do something...
//                    results=result;
                }
            };

            UploaderManager uploaderManager = UploaderManager
                    .getInstance(bucket);
            uploaderManager.setConnectTimeout(60);
            uploaderManager.setResponseTimeout(60);
            Map<String, Object> paramsMap = uploaderManager
                    .fetchFileInfoDictionaryWith(localFile, savePath);
            // 还可以加上其他的额外处理参数...
            paramsMap.put("return_url", "http://httpbin.org/get");
            // signature & policy 建议从服务端获取
            String policyForInitial = UpYunUtils.getPolicy(paramsMap);
            String signatureForInitial = UpYunUtils.getSignature(paramsMap,
                    formApiSecret);
            uploaderManager.upload(policyForInitial, signatureForInitial,
                    localFile, progressListener, completeListener);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "result";
    }

    /**
     * 上传完毕
     *
     * @param result 返回result 为成功 返回null为失败
     */
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (noticeSucces != null) {
            if (result != null) {
                noticeSucces.uploadStatus(true, status);
            } else {
                noticeSucces.uploadStatus(false, status);
            }
        }
    }


    public static void uploadImage(String bucket, String formApiSecret, String locaPath, String webPath) {

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
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 将用户ID扩充至9位
     **/
    public static String expandLength(String userId) {
        int length = 9;
        StringBuilder sb = new StringBuilder();
        if (userId.length() < length) {
            for (int i = 0; i < length - userId.length(); i++) {
                sb.append("0");
            }
            sb.append(userId);

        }
        String temp = sb.toString();
        return temp;
    }

    /**
     * 将 9位数ID，按照要求拼接参数
     **/
    public static String splitChar(String userId, int num) {
        String tep = expandLength(userId);
        char[] ab = tep.toCharArray();
        StringBuilder sp = new StringBuilder();
        sp.append("avatar/");
        for (int i = 0; i < ab.length; i++) {
            if (i == 3) {
                sp.append("/");
                sp.append(ab[i]);
            } else if (i == 5) {
                sp.append("/");
                sp.append(ab[i]);
            } else if (i == 7) {
                sp.append("/");
                sp.append(ab[i]);
            } else if (i == 8) {
                sp.append(ab[i]);
                if (num == 1)
                    sp.append("_avatar_big.jpg");
                else if (num == 2)
                    sp.append("_avatar_middle.jpg");
                else
                    sp.append("_avatar_small.jpg");
            } else {
                sp.append(ab[i]);
            }
        }
        return sp.toString();
    }

    /**
     * 将用户ID扩充至9位
     **/
    public static String exLength(String userId) {
        if (userId == null) userId = "";
        int length = 9;
        StringBuilder sb = new StringBuilder();
        if (userId.length() < length) {
            for (int i = 0; i < length - userId.length(); i++) {
                sb.append("0");
            }
            sb.append(userId);

        }
        String temp = sb.toString();
//        LogFly.e(temp);
        return temp;
    }

    /**
     * 将 9位数ID，按照要求拼接参数
     **/
    public static String split(String userId) {
        String tep = exLength(userId);
        char[] ab = tep.toCharArray();
        StringBuilder sp = new StringBuilder();
        sp.append("avatar/");
        for (int i = 0; i < ab.length; i++) {
            if (i == 3) {
                sp.append("/");
                sp.append(ab[i]);
            } else if (i == 5) {
                sp.append("/");
                sp.append(ab[i]);
            } else if (i == 7) {
                sp.append("/");
                sp.append(ab[i]);
            } else if (i == 8) {
                sp.append(ab[i]);
                sp.append("_avatar_small.jpg");
            } else {
                sp.append(ab[i]);
            }
        }
        return sp.toString();
    }

    /**
     * 将 9位数ID，按照要求拼接参数
     **/
    public static String getSplit(String userId) {
        String tep = exLength(userId);
        char[] ab = tep.toCharArray();
        StringBuilder sp = new StringBuilder();
        sp.append("avatar/");
        for (int i = 0; i < ab.length; i++) {
            if (i == 3) {
                sp.append("/");
                sp.append(ab[i]);
            } else if (i == 5) {
                sp.append("/");
                sp.append(ab[i]);
            } else if (i == 7) {
                sp.append("/");
                sp.append(ab[i]);
            } else if (i == 8) {
                sp.append(ab[i]);
                sp.append("_avatar_middle.jpg");
            } else {
                sp.append(ab[i]);
            }
        }
        return "http://ptf.flyert.com/" + sp.toString();
    }

}
