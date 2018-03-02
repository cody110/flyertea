package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.AmapLocation;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.caff.UploadImgManger;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.DataCallback;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.dal.BaseHelper;
import com.ideal.flyerteacafes.model.entity.AidsBean;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.CommunityBean;
import com.ideal.flyerteacafes.model.entity.CommunitySubBean;
import com.ideal.flyerteacafes.model.entity.CommunitySubTypeBean;
import com.ideal.flyerteacafes.model.entity.HotelsInfo;
import com.ideal.flyerteacafes.model.entity.LocationListBean;
import com.ideal.flyerteacafes.model.entity.TagBean;
import com.ideal.flyerteacafes.model.entity.TopicBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.model.loca.DraftInfo;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.model.loca.UploadAttachInfo;
import com.ideal.flyerteacafes.model.loca.UploadImgInfo;
import com.ideal.flyerteacafes.ui.activity.AlbumActivity;
import com.ideal.flyerteacafes.ui.activity.HomeActivity;
import com.ideal.flyerteacafes.ui.activity.writethread.WriteThreadActivity;
import com.ideal.flyerteacafes.ui.activity.YulanTupianActivity;
import com.ideal.flyerteacafes.ui.activity.interfaces.IWriteThread;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.BitmapTools;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.DateUtil;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fly on 2016/11/14.
 */

public class WriteThreadPresenter extends BasePresenter<IWriteThread> {

    public static final int HANDLER_WHAT_FORUM = 1;

    public static final String BUNDLE_RESULT_HOTELSINFO = "hotels";
    /**
     * 草稿
     **/
    public static final String BUNDLE_DRAFT_DATA = "drafat";
    /**
     * 一二级版块
     */
    public static String BUNDLE_FID_1 = "fid1", BUNDLE_NAME_1 = "name1", BUNDLE_FID_2 = "fid2", BUNDLE_NAME_2 = "name2", BUNDLE_FID_3 = "fid3", BUNDLE_NAME_3 = "name3", BUNDLE_TYPEID = "typeid";
    /**
     * 不同发帖入口
     * BUNDLE_FROM_DEF 默认，没有选择任何版块
     * BUNDLE_FROM_DRAFT 草稿，可以修改版块
     * BUNDLE_FROM_SELECT_TWO_FID 选择了二级版块，不可修改，如有分类需要可修改分类
     */
    public static String BUNDLE_FROM_TYPE = "from_type", BUNDLE_FROM_DEF = "def", BUNDLE_FROM_DRAFT = "draft", BUNDLE_FROM_SELECT_TWO_FID = "select_two_fid", BUNDLE_FROM_WENDA = "wenda";
    public static String addImgMark = "addImgMark";
    private List<String> selectImgList = new ArrayList<>();
    private List<CommunityBean> communityBeanList = new ArrayList<>();

    public String fid1, fid2, fid3, name1, name2, name3, typeid, collectionid, topicName;


    public String from_type;

    private DraftInfo draftInfo;

    private TopicBean topicBean;

    public LocationListBean locationListBean;

    public LocationListBean.LocationBean locationBean;
    public List<TagBean> tagsList;
    public float star;
    public String flight, flightid,airportid;


    @Override
    public void init(Activity activity) {
        super.init(activity);
        Intent intent = activity.getIntent();


        from_type = intent.getStringExtra(BUNDLE_FROM_TYPE);
        if (TextUtils.equals(BUNDLE_FROM_DRAFT, from_type)) {
            draftInfo = (DraftInfo) intent.getSerializableExtra(BUNDLE_DRAFT_DATA);
            LogFly.e(draftInfo.toString());
            fid1 = draftInfo.getFid1();
            fid2 = draftInfo.getFid2();
            fid3 = draftInfo.getFid3();
            name1 = draftInfo.getFname1();
            name2 = draftInfo.getFname2();
            name3 = draftInfo.getFname3();

            locationBean = draftInfo.getLocationBean();
            tagsList = draftInfo.getTagList();
            star = draftInfo.getStar();
            flight = draftInfo.getFlight();
            flightid = draftInfo.getFlightid();

            topicName = draftInfo.getTopicName();
            collectionid = draftInfo.getCollectionid();

            getView().setTvTitleContent(draftInfo.getTitle(), draftInfo.getContent(), draftInfo.getLocation(), topicName);
            getView().bindRatingLayout(locationBean, star);

            if (draftInfo.getImgList() != null) {
                selectImgList.addAll(draftInfo.getImgList());
            }
        } else {

            fid1 = intent.getStringExtra(BUNDLE_FID_1);
            fid2 = intent.getStringExtra(BUNDLE_FID_2);
            name1 = intent.getStringExtra(BUNDLE_NAME_1);
            name2 = intent.getStringExtra(BUNDLE_NAME_2);
            typeid = intent.getStringExtra(BUNDLE_TYPEID);

            topicBean = (TopicBean) intent.getSerializableExtra("topicBean");
            if (topicBean != null) {
                topicName = "#" + topicBean.getName() + "#";
                collectionid = topicBean.getCtid();
                getView().setTvTitleContent(null, null, null, topicName);
            }
        }


        requestCommunity();
        requestLocation();
    }


    /**
     * @description: 获取社区版块数据
     * @author: Cindy
     * @date: 2016/11/15 17:20
     * @version V6.6.0
     */
    private void requestCommunity() {
        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_COMMUNITY_INDEX), new PListDataCallback(ListDataCallback.LIST_KEY_DATA, CommunityBean.class) {
            @Override
            public void flySuccess(ListDataBean response) {
                if (!isViewAttached()) return;
                if (response.getDataList() != null && response.getDataList().size() > 0) {
                    communityBeanList.clear();
                    if (TextUtils.equals(from_type, BUNDLE_FROM_WENDA)) {
                        if (response.getDataList().size() > 2)
                            for (int i = 0; i < 3; i++) {
                                CommunityBean communityBean = (CommunityBean) response.getDataList().get(i);
                                for (CommunitySubBean subbean : communityBean.getSubforums()) {
                                    if (subbean.getSubforumList() != null) {
                                        for (CommunitySubTypeBean typeBean : subbean.getSubforumList()) {
                                            if (TextUtils.equals(typeBean.getName(), "求助问答")) {
                                                if (!communityBeanList.contains(communityBean))
                                                    communityBeanList.add(communityBean);
                                            }
                                        }
                                    }
                                }
                            }
                    } else {
                        communityBeanList.addAll(response.getDataList());
                    }
                }
            }
        });
    }


    /**
     * 拼接标识
     *
     * @param strArray
     * @return
     */
    public static String splicMark(String... strArray) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strArray.length; i++) {
            sb.append(strArray[i]);
            if (i != strArray.length - 1)
                sb.append("-");
        }
        return sb.toString();
    }


    /**
     * 保存草稿
     */
    public void saveDraft(String title, String content, String location) {
        DraftInfo info = new DraftInfo();
        info.setUid(UserManger.getUserInfo().getMember_uid());
        info.setFid1(fid1);
        info.setFid2(fid2);
        info.setFid3(fid3);
        info.setFname1(name1);
        info.setFname2(name2);
        info.setFname3(name3);
        info.setTitle(title);
        info.setContent(content);
        info.setLocation(location);
        info.setTime(DateUtil.getDateline());


        info.setLocationBean(locationBean);
        info.setTagList(tagsList);
        info.setStar(star);
        info.setTopicName(topicName);
        info.setCollectionid(collectionid);
        info.setFlight(flight);
        info.setFlightid(flightid);


        if (TextUtils.equals(BUNDLE_FROM_DRAFT, from_type)) {
            BaseHelper.getInstance().delete(DraftInfo.class, "id", "=", draftInfo.getId());
        }

        LogFly.e(info.toString());

        BaseHelper.getInstance().saveBean(info);

        Bundle bundle = new Bundle();
        bundle.putInt("code", FinalUtils.HOME_TO_DRAFT);
        getBaseView().jumpActivity(HomeActivity.class, bundle);
    }

    /**
     * 删除草稿
     */
    public void deleteDraft() {
        if (TextUtils.equals(WriteThreadPresenter.BUNDLE_FROM_DRAFT, from_type)) {
            BaseHelper.getInstance().delete(DraftInfo.class, "id", "=", draftInfo.getId());
        }
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
