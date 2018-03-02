package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.flyer.tusdk.TuSdkManger;
import com.flyer.tusdk.model.VideoInfo;
import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.AmapLocation;
import com.ideal.flyerteacafes.caff.CacheFileManger;
import com.ideal.flyerteacafes.caff.UploadImgManger;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.DataCallback;
import com.ideal.flyerteacafes.dal.BaseHelper;
import com.ideal.flyerteacafes.model.entity.AidsBean;
import com.ideal.flyerteacafes.model.entity.LocationListBean;
import com.ideal.flyerteacafes.model.entity.TagBean;
import com.ideal.flyerteacafes.model.entity.TopicBean;
import com.ideal.flyerteacafes.model.entity.VidsBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.model.loca.DraftInfo;
import com.ideal.flyerteacafes.model.loca.TuwenInfo;
import com.ideal.flyerteacafes.model.loca.UploadAttachInfo;
import com.ideal.flyerteacafes.model.loca.UploadImgInfo;
import com.ideal.flyerteacafes.ui.activity.HomeActivity;
import com.ideal.flyerteacafes.ui.activity.interfaces.IWriteMajorThreadContent;
import com.ideal.flyerteacafes.ui.activity.writethread.EditMajorThreadImgTextActivity;
import com.ideal.flyerteacafes.ui.activity.writethread.ThreadPreviewActivity;
import com.ideal.flyerteacafes.ui.activity.writethread.WriteMajorThreadEditImgActivity;
import com.ideal.flyerteacafes.ui.activity.writethread.WriteMajorThreadImgTextActivity;
import com.ideal.flyerteacafes.ui.activity.writethread.WriteThreadActivity;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.TaskUtil;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.BitmapTools;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.DateUtil;
import com.ideal.flyerteacafes.utils.tools.StringTools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.lasque.tusdk.core.video.TuSDKVideoResult;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by fly on 2017/3/10.
 */

public class WriteMajorThreadContentPresenter extends BasePresenter<IWriteMajorThreadContent> implements UploadImgInfo.IUploadStatus {

    private String title;
    public String from_type;
    private DraftInfo draftInfo;
    private List<TuwenInfo> tuwenInfoList = new ArrayList<>();

    public boolean isEditModle = false;//编辑模式

    public int allCount = 0, successCount = 0, errorCount = 0;

    public String fid1, fid2, fid3, name1, name2, name3, typeid;

    public LocationListBean locationListBean;
    public LocationListBean.LocationBean locationBean;
    public List<TagBean> tagsList;
    public String utags, flight, flightid, collectionid, topicName, airportid;
    public float star;

    private String content, attachId;
    List<UploadImgInfo> needUploadImageInfo = new ArrayList<>();


    public List<TuwenInfo> getTuwenInfoList() {
        return tuwenInfoList;
    }

    public float getStarMark() {
        return star;
    }


    /**
     * 刷新图文列表
     */
    public void refreshTuwenView() {
        getView().bindAdapter(tuwenInfoList);
    }

    @Override
    public void init(Activity activity) {
        super.init(activity);
        Intent intent = activity.getIntent();
        bindInit(intent);
        refreshTuwenView();
        requestLocation();
        uploadImage();
    }

    @Override
    public void detachView() {
        super.detachView();
        UploadImgManger.getInstance().stopUploadTask();

    }

    /**
     * 初始化数据
     *
     * @param intent
     */
    public void bindInit(Intent intent) {
        from_type = intent.getStringExtra(WriteThreadPresenter.BUNDLE_FROM_TYPE);

        if (TextUtils.equals(from_type, WriteThreadPresenter.BUNDLE_FROM_DRAFT)) {
            draftInfo = (DraftInfo) intent.getSerializableExtra(WriteThreadPresenter.BUNDLE_DRAFT_DATA);
            initDataByDraftInfo(draftInfo);
        } else {
            fid1 = intent.getStringExtra(WriteThreadPresenter.BUNDLE_FID_1);
            fid2 = intent.getStringExtra(WriteThreadPresenter.BUNDLE_FID_2);
            typeid = intent.getStringExtra(WriteThreadPresenter.BUNDLE_TYPEID);
            String title = intent.getStringExtra(WriteThreadActivity.BUNDLE_TITLE);
            String content = intent.getStringExtra(WriteThreadActivity.BUNDLE_CONTENT);
            String location = intent.getStringExtra(WriteThreadActivity.BUNDLE_LOCATION);


            locationBean = (LocationListBean.LocationBean) intent.getSerializableExtra("locationBean");
            tagsList = (List<TagBean>) intent.getSerializableExtra("tagsList");
            star = intent.getFloatExtra("star", 0);
            flight = intent.getStringExtra("flight");
            flightid = intent.getStringExtra("flightid");

            TopicBean topicBean = (TopicBean) intent.getSerializableExtra("topicBean");
            if (topicBean != null) {
                topicName = "#" + topicBean.getName() + "#";
                collectionid = topicBean.getCtid();
            }


            getView().threadTitleSubjectLocation(title, content, location, topicName);


            List<String> mSelectImage = (List<String>) intent.getSerializableExtra("mSelectedImage");
            if (mSelectImage != null)
                for (String imgPath : mSelectImage) {
                    TuwenInfo info = new TuwenInfo();
                    info.setType(TuwenInfo.TYPE_TU);
                    info.setImgPath(imgPath);
                    tuwenInfoList.add(info);
                }
        }
    }


    /**
     * 把图片数据转成图文数据
     *
     * @param data
     */
    public void setTuwenList(Intent data) {
        List<String> mSelectImage = (List<String>) data.getSerializableExtra("mSelectedImage");
        for (String imgPath : mSelectImage) {
            TuwenInfo info = new TuwenInfo();
            info.setType(TuwenInfo.TYPE_TU);
            info.setImgPath(imgPath);
            for (UploadImgInfo imgInfo : needUploadImageInfo) {
                if (TextUtils.equals(info.getImgPath(), imgInfo.getLocaPath())) {
                    info.setImgStatus(imgInfo.getStatus());
                }
            }

            tuwenInfoList.add(info);
        }
        refreshTuwenView();
        uploadImage();
    }


    /**
     * 草稿数据初始化
     *
     * @param draftInfo
     */
    private void initDataByDraftInfo(DraftInfo draftInfo) {

        locationBean = draftInfo.getLocationBean();
        tagsList = draftInfo.getTagList();
        star = draftInfo.getStar();
        flight = draftInfo.getFlight();
        flightid = draftInfo.getFlightid();

        topicName = draftInfo.getTopicName();
        collectionid = draftInfo.getCollectionid();


        title = draftInfo.getTitle();
        String subject = draftInfo.getSubject();

        getView().threadTitleSubjectLocation(title, subject, draftInfo.getLocation(), topicName);


        List<DraftInfo.UploadLocaInfo> uploadImgInfos = draftInfo.getUploadLocaInfoList();
        if (!DataTools.isEmpty(uploadImgInfos)) {
            for (DraftInfo.UploadLocaInfo info : uploadImgInfos) {
                UploadImgInfo uploadImgInfo = new UploadImgInfo(info.getLocaPath(), info.getWebPath());
                if (info.getStatus() == UploadImgInfo.STATUS_SUCCESS) {
                    uploadImgInfo.setStatus(info.getStatus());
                } else {
                    uploadImgInfo.setStatus(UploadImgInfo.STATUS_NOT_BEGIN);
                }
                uploadImgInfo.uploadThread();
                uploadImgInfo.setNeedCompress(true);
                uploadImgInfo.setiUploadStatus(this);
                needUploadImageInfo.add(uploadImgInfo);
            }

        }

        List<TuwenInfo> tuwenInfos = draftInfo.getTuwenList();
        if (tuwenInfos != null && !tuwenInfos.isEmpty()) {
            tuwenInfoList.addAll(tuwenInfos);
        }


        for (TuwenInfo info : tuwenInfoList) {
            for (UploadImgInfo imgInfo : needUploadImageInfo) {
                if (TextUtils.equals(info.getImgPath(), imgInfo.getLocaPath())) {
                    info.setImgStatus(imgInfo.getStatus());
                }
            }
        }

    }

    /**
     * item 上移
     *
     * @param pos
     */
    public void itemUp(int pos) {
        if (pos != 0) {
            tuwenInfoList.add(pos - 1, tuwenInfoList.get(pos));
            tuwenInfoList.remove(pos + 1);
            refreshTuwenView();
        }
    }

    /**
     * item 下移
     *
     * @param pos
     */
    public void itemDown(int pos) {
        if (pos < tuwenInfoList.size() - 1) {
            tuwenInfoList.add(pos + 2, tuwenInfoList.get(pos));
            tuwenInfoList.remove(pos);
            refreshTuwenView();
        }
    }


    /**
     * 重新上传
     *
     * @param i
     */
    public void reStartUpload(int i) {
        for (int j = 0; j < needUploadImageInfo.size(); j++) {
            if (TextUtils.equals(tuwenInfoList.get(i).getImgPath(), needUploadImageInfo.get(j).getLocaPath())) {
                UploadImgManger.getInstance().execute(needUploadImageInfo.get(j));
                uploadProgress();
                break;
            }
        }
    }


    /**
     * 删除item
     *
     * @param pos
     */
    public void deleteItem(int pos) {
        tuwenInfoList.remove(pos);
        refreshTuwenView();
    }

    //添加文字返回
    public void activityResultAddImgText(Intent data) {
        String imgText = data.getStringExtra(WriteMajorThreadImgTextActivity.BUNDLE_IMG_TEXT);
        TuwenInfo info = new TuwenInfo();
        info.setType(TuwenInfo.TYPE_WEN);
        info.setText(imgText);
        tuwenInfoList.add(info);
        refreshTuwenView();
    }

    //编辑文字返回
    public void activityResultEditText(Intent data) {
        int pos = data.getIntExtra(EditMajorThreadImgTextActivity.BUNDLE_LIST_POS, 0);
        String imgText = data.getStringExtra(WriteMajorThreadImgTextActivity.BUNDLE_IMG_TEXT);
        tuwenInfoList.get(pos).setText(imgText);
        refreshTuwenView();
    }

    //添加图片返回
    public void activityResultAddImg(Intent data) {
        List<String> mSelectImage = (List<String>) data.getSerializableExtra("mSelectedImage");
        for (String imgPath : mSelectImage) {
            TuwenInfo info = new TuwenInfo();
            info.setType(TuwenInfo.TYPE_TU);
            info.setImgPath(imgPath);
            for (UploadImgInfo imgInfo : needUploadImageInfo) {
                if (TextUtils.equals(info.getImgPath(), imgInfo.getLocaPath())) {
                    info.setImgStatus(imgInfo.getStatus());
                }
            }

            tuwenInfoList.add(info);
        }
        refreshTuwenView();
        uploadImage();
    }

    //删除视频
    public void activityResultDeleteVideo(Intent data) {
        int index = data.getIntExtra("pos", -1);
        if (index > -1) {
            tuwenInfoList.remove(index);
            refreshTuwenView();
        }
    }

    //编辑图片
    public void activityResultEditImg(Intent data) {
        int index = data.getIntExtra(WriteMajorThreadEditImgActivity.BUNDLE_LIST_POS, -1);
        String imgPath = data.getStringExtra(WriteMajorThreadEditImgActivity.BUNDLE_IMG_PATH);
        if (TextUtils.isEmpty(imgPath)) {
            removeUploadInfo(tuwenInfoList.get(index).getImgPath());
            tuwenInfoList.remove(index);
        } else {
            removeUploadInfo(tuwenInfoList.get(index).getImgPath());
            tuwenInfoList.get(index).setImgPath(imgPath);
            tuwenInfoList.get(index).setImgStatus(getImgUploadStatus(imgPath));
        }
        refreshTuwenView();
        uploadImage();
    }

    //选择位置
    public void activityResultFlyLocation(Intent data) {
        locationBean = (LocationListBean.LocationBean) data.getSerializableExtra("data");
        tagsList = (List<TagBean>) data.getSerializableExtra("tags");
        star = data.getFloatExtra("star", 0);
        flight = data.getStringExtra("flight");
        flightid = data.getStringExtra("flightid");
        if (locationBean != null) {
            airportid = locationBean.getId();
        }
    }

    /**
     * 添加视频
     */
    public void addVideoData(VideoInfo videoInfo) {
        TuwenInfo info = new TuwenInfo();
        info.setType(TuwenInfo.TYPE_VIDEO);
        info.setImgPath(videoInfo.getThumbnailLocalPath());
        info.setVideoPath(videoInfo.getVideoLocalPath());
        info.setVideoInfo(videoInfo);

        tuwenInfoList.add(info);
        refreshTuwenView();

        recordVideoInfo(videoInfo);
    }


    /**
     * 同步视频信息
     * <p>
     * <p>
     * $attachment['isvideo'];   //取值1
     * $attachment['videourl'];  //视频在又拍云上的url，存储bucket同图片,
     * $attachment['videowidth'];    //视频宽度（如果能获取）
     * $attachment['videoheight'];//视频高度（如果能获取）
     * $attachment['videosize'];//视频大小 KB(如果能获取）
     * $attachment['videomapx']; //视频经度（如果能获取）
     * $attachment['videomapy']; //视频维度（如果能获取）
     * $attachment['videodevice']; //视频设备   ios/android
     *
     * @param videoInfo
     */
    private void recordVideoInfo(final VideoInfo videoInfo) {
        File file = new File(videoInfo.getThumbnailLocalPath());

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> info = new HashMap<>();
        info.put("filename", file.getName());
        info.put("filesize", file.length());
        info.put("attachment", videoInfo.getThumbnailUrl().replace("/forum/", ""));
        info.put("width", BitmapTools.getImageWidth(file.getPath()));

        info.put("isvideo", "1");
        info.put("videourl", videoInfo.getVideoUrl().replace("/forum/", ""));
        info.put("videowidth", videoInfo.getVideoWidth());
        info.put("videoheight", videoInfo.getVideoHeight());
        info.put("videosize", videoInfo.getVideosize());
        info.put("videomapx", AmapLocation.mLongitude);
        info.put("videomapy", AmapLocation.mLatitude);
        info.put("timelength", videoInfo.getTimelength());

        list.add(info);

        Map<String, Object> map = new HashMap<>();
        map.put("attachments", list);
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_UPLOADIMAGE);
        params.setBodyJson(map);
        XutilsHttp.Post(params, new PDataCallback<VidsBean>() {
            @Override
            public void flySuccess(DataBean<VidsBean> result) {
                if (result.getDataBean() != null) {
                    videoInfo.setVids(result.getDataBean().getVids());
                }
            }
        });
    }


    /**
     * 预览
     */
    public void toThreadPreviewActivity(String title, String subject, String location) {
        List<TuwenInfo> datas = new ArrayList<>();

        for (TuwenInfo info : tuwenInfoList) {
            if ((info.getType() == TuwenInfo.TYPE_TU && info.getImgStatus() == UploadImgInfo.STATUS_SUCCESS) || info.getType() == TuwenInfo.TYPE_WEN || info.getType() == TuwenInfo.TYPE_VIDEO) {
                datas.add(info);
            }
        }

        Bundle bundle = new Bundle();
        bundle.putString(ThreadPreviewActivity.BUNDLE_FID_1, fid1);
        bundle.putString(ThreadPreviewActivity.BUNDLE_FID_2, fid2);
        bundle.putString(ThreadPreviewActivity.BUNDLE_FID_3, fid3);
        bundle.putString(ThreadPreviewActivity.BUNDLE_TITLE, title);
        bundle.putString(ThreadPreviewActivity.BUNDLE_SUBJECT, subject);
        bundle.putString(ThreadPreviewActivity.BUNDLE_FORUMNAME, name1 + "-" + name2);
        bundle.putString(ThreadPreviewActivity.BUNDLE_LOCATION, location);
        bundle.putSerializable(ThreadPreviewActivity.BUNDLE_CONTENT, (Serializable) datas);
        getBaseView().jumpActivity(ThreadPreviewActivity.class, bundle);

    }


    /**
     * 删除草稿
     */
    public void deleteDraft() {
        if (TextUtils.equals(WriteThreadPresenter.BUNDLE_FROM_DRAFT, from_type)) {
            BaseHelper.getInstance().delete(DraftInfo.class, "id", "=", draftInfo.getId());
        }
    }

    /**
     * 保存草稿
     */
    public void saveDraft(String title, String subject, String location) {
        DraftInfo info = new DraftInfo();
        info.setLocation(location);
        info.setType(DraftInfo.TYPE_MAJOR);
        info.setUid(UserManger.getUserInfo().getMember_uid());
        info.setTitle(title);
        info.setSubject(subject);
        if (tuwenInfoList != null && !tuwenInfoList.isEmpty()) {
            StringBuffer contentSb = new StringBuffer();
            for (TuwenInfo tuwen : tuwenInfoList) {
                if (!TextUtils.isEmpty(tuwen.getText())) {
                    contentSb.append(tuwen.getText());
                }
            }
            info.setContent(contentSb.toString());
            info.setTuwenList(tuwenInfoList);
        }
        info.setTime(DateUtil.getDateline());
        info.setUploadImgInfoList(needUploadImageInfo);


        info.setLocationBean(locationBean);
        info.setTagList(tagsList);
        info.setStar(star);
        info.setTopicName(topicName);
        info.setCollectionid(collectionid);
        info.setFlight(flight);
        info.setFlightid(flightid);

        deleteDraft();
        BaseHelper.getInstance().saveBean(info);
        Bundle bundle = new Bundle();
        bundle.putInt("code", FinalUtils.HOME_TO_DRAFT);
        getBaseView().jumpActivity(HomeActivity.class, bundle);
    }


    public void getThreadCountText(String countTitle) {
        getDialog().proDialogShow();
        List<UploadAttachInfo> uploadAttachInfoList = new ArrayList<>();

        StringBuffer sb = new StringBuffer();
        sb.append(countTitle);

        StringBuffer vidSb = new StringBuffer();

        for (int i = 0; i < tuwenInfoList.size(); i++) {

            if (tuwenInfoList.get(i).getType() == TuwenInfo.TYPE_TU) {


                for (UploadImgInfo img : needUploadImageInfo) {

                    if (TextUtils.equals(img.getLocaPath(), tuwenInfoList.get(i).getImgPath())) {
                        if (img.getStatus() != UploadImgInfo.STATUS_SUCCESS) {
                            continue;
                        }
                        //TODO 获取attachid的数据
                        UploadAttachInfo info = imageToUploadAttachInfo(img.getLocaPath(), img.getWebPath().replace("/forum/", ""));
                        uploadAttachInfoList.add(info);

                        sb.append(tuwenInfoList.get(i).getImgPath());

                        if (!TextUtils.isEmpty(tuwenInfoList.get(i).getText())) {
                            sb.append(tuwenInfoList.get(i).getText());
                        }
                    }


                }


            } else if (tuwenInfoList.get(i).getType() == TuwenInfo.TYPE_VIDEO) {

                VideoInfo videoInfo = tuwenInfoList.get(i).getVideoInfo();

//                [media=mp4,宽度,高度,Cover图片,时间，vid]mp4播放地址[/media]
//                [media=mp4,544,960,/forum/2018/02/07/100202GMZZSILBSYILEXXT.png,10,2023666]/forum/2018/02/07/100202YIIXTVZICKFFHCED.mp4[/media]
                sb.append("[media=mp4,");
                sb.append(videoInfo.getVideoWidth());
                sb.append(",");
                sb.append(videoInfo.getVideoHeight());
                sb.append(",");
                sb.append(videoInfo.getThumbnailUrl().substring(1));
                sb.append(",");
                sb.append(videoInfo.getTimelength());
                sb.append(",");
                sb.append(videoInfo.getVids());
                sb.append("]");
                sb.append(videoInfo.getVideoUrl().substring(1));
                sb.append("[/media]");


                if (!TextUtils.isEmpty(tuwenInfoList.get(i).getText())) {
                    sb.append(tuwenInfoList.get(i).getText());
                }

                vidSb.append(videoInfo.getVids());
                vidSb.append(",");
            } else {
                sb.append(tuwenInfoList.get(i).getText());
            }

            sb.append("\n");

        }

        content = sb.toString();

        String vids = null;
        if (vidSb.length() > 0) {
            vids = vidSb.deleteCharAt(vidSb.length() - 1).toString();
        }

        if (uploadAttachInfoList.isEmpty()) {
            getView().threadCountText(content, "", vids);
        } else {
            requestGetAttachId(uploadAttachInfoList, vids);
        }


    }

    /**
     * 根据图片路径 返回需要上传的数据
     *
     * @param path
     * @return
     */
    private UploadAttachInfo imageToUploadAttachInfo(String path, String webPath) {
        File file = new File(path);
        return new UploadAttachInfo(file.getName(), String.valueOf(file.length()), webPath, BitmapTools.getImageWidth(file.getPath()));
    }


    /**
     * 批量获取attachid
     *
     * @param list
     */
    private void requestGetAttachId(List<UploadAttachInfo> list, final String vids) {
        Map<String, Object> map = new HashMap<>();
        map.put("attachments", list);
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_UPLOADIMAGE);
        params.setBodyJson(map);
        XutilsHttp.Post(params, new PDataCallback<AidsBean>() {
            @Override
            public void flySuccess(DataBean<AidsBean> result) {
                if (!isViewAttached()) return;
                if (result.getDataBean() == null) {
                    ToastUtils.showToast(context.getString(R.string.data_error));
                    return;
                }
                String[] strArray = result.getDataBean().getAids();
                if (strArray != null) {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < strArray.length; i++) {
                        content = StringTools.replaceAll(content, needUploadImageInfo.get(i).getLocaPath(), "[attachimg]" + strArray[i] + "[/attachimg]");

                        sb.append(strArray[i]);
                        if (i != strArray.length - 1)
                            sb.append("_");
                    }
                    attachId = sb.toString();
                    getView().threadCountText(content, attachId, vids);
                }
            }

            @Override
            public void flyFinished() {
                if (isViewAttached())
                    getDialog().proDialogDissmiss();
            }
        });
    }


    /**
     * 删除图片
     *
     * @param path
     */
    private void removeUploadInfo(String path) {
        for (UploadImgInfo info : needUploadImageInfo) {
            if (TextUtils.equals(info.getLocaPath(), path)) {
                if (info.getTime() > 1) {
                    info.setTime(info.getTime() - 1);
                } else {
                    needUploadImageInfo.remove(info);
                }
                break;
            }
        }
    }

    /**
     * 上传图片到U盘云
     */
    public void uploadImage() {

        for (UploadImgInfo info : needUploadImageInfo) {
            info.setTime(0);
        }

        for (int i = 0; i < tuwenInfoList.size(); i++) {


            if (tuwenInfoList.get(i).getType() == TuwenInfo.TYPE_TU) {

                boolean isHas = false;
                for (int j = 0; j < needUploadImageInfo.size(); j++) {
                    if (TextUtils.equals(tuwenInfoList.get(i).getImgPath(), needUploadImageInfo.get(j).getLocaPath())) {
                        isHas = true;
                        needUploadImageInfo.get(j).setTime(needUploadImageInfo.get(j).getTime() + 1);
                    }
                }
                if (!isHas) {
                    //TODO 上传到又拍云需要的数据
                    UploadImgInfo uploadImgInfo = new UploadImgInfo(tuwenInfoList.get(i).getImgPath(), "/forum/" + getAttachment());
                    uploadImgInfo.uploadThread();
                    uploadImgInfo.setNeedCompress(true);
                    uploadImgInfo.setiUploadStatus(this);
                    needUploadImageInfo.add(uploadImgInfo);
                }
            }

        }

        if (needUploadImageInfo.size() > 0) {
            UploadImgManger.getInstance().execute(needUploadImageInfo);
        }
        uploadProgress();

    }

    /**
     * 得到图片状态
     *
     * @param path
     * @return
     */
    private int getImgUploadStatus(String path) {
        int status = UploadImgInfo.STATUS_NOT_BEGIN;
        for (UploadImgInfo info : needUploadImageInfo) {
            if (TextUtils.equals(info.getLocaPath(), path)) {
                status = info.getStatus();
            }
        }
        return status;
    }


    boolean isRunDelayed = false;

    Map<String, UploadImgInfo> uploadMap = new HashMap<>();

    @Override
    public void uploadStatusChange(final UploadImgInfo uploadImgInfo) {
        uploadMap.put(uploadImgInfo.getLocaPath(), uploadImgInfo);
        if (!isRunDelayed) {
            isRunDelayed = true;
            Random rand = new Random();
            int randNum = rand.nextInt(2000);
            randNum += 1000;
            TaskUtil.postOnUiThreadDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isViewAttached()) {
                        for (TuwenInfo tuwen : tuwenInfoList) {
                            if (!TextUtils.isEmpty(tuwen.getImgPath())) {
                                UploadImgInfo info = uploadMap.get(tuwen.getImgPath());
                                if (info != null) {
                                    tuwen.setImgStatus(info.getStatus());
                                    uploadMap.remove(info.getLocaPath());
                                }
                            }
                        }
                        refreshTuwenView();
                        uploadProgress();
                        isRunDelayed = false;
                    }
                }
            }, randNum);
        }

    }

    /**
     * 上传图片进度
     */
    private void uploadProgress() {
        allCount = 0;
        successCount = 0;
        errorCount = 0;
        for (UploadImgInfo i : needUploadImageInfo) {
            allCount += i.getTime();
            if (i.getStatus() == UploadImgInfo.STATUS_SUCCESS) {
                successCount += i.getTime();
            }
            if (i.getStatus() == UploadImgInfo.STATUS_FAIL) {
                errorCount += i.getTime();
            }
        }
        if (isViewAttached()) {
            getView().uploadProgress(allCount, successCount, errorCount);
        }
    }

    private String getAttachment() {
        Date date = DateUtil.getSysDate();
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
        sb.append(DataTools.getRandomString(16));
        sb.append(".jpg");
        return sb.toString();

    }

    public void requestLocation() {
        if (AmapLocation.mLongitude != 0) {
            FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_LOCATION_NAME);
            //&longtitude=116.6254445393&latitude=40.0523666397
            params.addQueryStringParameter("longtitude", String.valueOf(AmapLocation.mLongitude));
            params.addQueryStringParameter("latitude", String.valueOf(AmapLocation.mLatitude));
            XutilsHttp.Get(params, new DataCallback<LocationListBean>() {
                @Override
                public void flySuccess(DataBean<LocationListBean> result) {
                    locationListBean = result.getDataBean();
                }
            });
        }
    }
}
