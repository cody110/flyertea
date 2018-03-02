package com.ideal.flyerteacafes.ui.activity.writethread;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.ForumChooseAdapter;
import com.ideal.flyerteacafes.adapters.ForumChooseAdapter3;
import com.ideal.flyerteacafes.adapters.ForumChooseTypeAdapter;
import com.ideal.flyerteacafes.adapters.ForumOneLevelAdapter;
import com.ideal.flyerteacafes.caff.AmapLocation;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.DataCallback;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.CommunityBean;
import com.ideal.flyerteacafes.model.entity.CommunitySubBean;
import com.ideal.flyerteacafes.model.entity.CommunitySubTypeBean;
import com.ideal.flyerteacafes.model.entity.LocationListBean;
import com.ideal.flyerteacafes.model.entity.SendThreadResultBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.HomeActivity;
import com.ideal.flyerteacafes.ui.activity.web.SystemWebActivity;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.WriteThreadPresenter;
import com.ideal.flyerteacafes.ui.view.MyAlertDialog;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2017/7/31.
 */
@ContentView(R.layout.activity_forum_choose)
public class ForumChooseActivity extends BaseActivity {

    @ViewInject(R.id.forum_choose_1ist_1)
    ListView forum_choose_1ist_1;
    @ViewInject(R.id.forum_choose_1ist_2)
    ListView forum_choose_1ist_2;
    @ViewInject(R.id.forum_choose_1ist_3)
    ListView forum_choose_1ist_3;
    @ViewInject(R.id.list_line)
    View lineView;
    @ViewInject(R.id.select_zhibo_layout)
    View select_zhibo_layout;

    ForumOneLevelAdapter oneLevelAdapter;
    ForumChooseAdapter twoLevelAdapter;
    ForumChooseAdapter3 threeLevelAdapter;
    ForumChooseTypeAdapter typeAdapter;

    List<CommunitySubTypeBean> threeData = new ArrayList<>();
    List<CommunitySubBean> twoData = new ArrayList<>();
    private List<CommunityBean> communityBeanList = new ArrayList<>();
    private static final int UPDATE_MOBILE = 1;
    private LocationListBean.LocationBean locationBean;
    private String tags, utags, flight, flightid, collectionid, airportid;
    private float star;


    private String subject, message, attachId, location, fid1, fid2, fid3, typeid, from_type, vids;

    private String defTypeId;
    private boolean is_feed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        from_type = getIntent().getStringExtra(WriteThreadPresenter.BUNDLE_FROM_WENDA);
        subject = getIntent().getStringExtra("subject");
        message = getIntent().getStringExtra("message");
        attachId = getIntent().getStringExtra("attachId");
        location = getIntent().getStringExtra("location");
        vids = getIntent().getStringExtra("vids");
        fid1 = getIntent().getStringExtra(WriteThreadPresenter.BUNDLE_FID_1);
        fid2 = getIntent().getStringExtra(WriteThreadPresenter.BUNDLE_FID_2);
        fid3 = getIntent().getStringExtra(WriteThreadPresenter.BUNDLE_FID_3);
        typeid = getIntent().getStringExtra(WriteThreadPresenter.BUNDLE_TYPEID);
        locationBean = (LocationListBean.LocationBean) getIntent().getSerializableExtra("locationBean");
        tags = getIntent().getStringExtra("tags");
        star = getIntent().getFloatExtra("star", 0);
        utags = getIntent().getStringExtra("utags");
        flight = getIntent().getStringExtra("flight");
        flightid = getIntent().getStringExtra("flightid");
        airportid = getIntent().getStringExtra("airportid");
        collectionid = getIntent().getStringExtra("collectionid");
        LogFly.e("flightid:" + flightid + "airportid:" + airportid);
        defTypeId = typeid;

        select_zhibo_layout.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(location) && !TextUtils.isEmpty(attachId)) {
            if (attachId.split("_").length >= 3) {
                select_zhibo_layout.setVisibility(View.VISIBLE);
            }
        }


        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_THREAD_FORUM_LIST), new ListDataCallback(ListDataCallback.LIST_KEY_DATA, CommunityBean.class) {
            @Override
            public void flySuccess(ListDataBean result) {

                if (result.getDataList() != null && result.getDataList().size() > 0) {
                    communityBeanList.clear();
                    if (TextUtils.equals(from_type, WriteThreadPresenter.BUNDLE_FROM_WENDA)) {
                        if (result.getDataList().size() > 2)
                            for (int i = 0; i < 3; i++) {
                                CommunityBean communityBean = (CommunityBean) result.getDataList().get(i);
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
                        communityBeanList.addAll(result.getDataList());
                    }


                    if (oneLevelAdapter == null) {
                        oneLevelAdapter = new ForumOneLevelAdapter(ForumChooseActivity.this, communityBeanList, R.layout.listview_forum_one_level_layout);
                        forum_choose_1ist_1.setAdapter(oneLevelAdapter);
                        if (TextUtils.isEmpty(fid1)) {
                            oneLevelAdapter.setSelectIndex(0);
                        } else {
                            oneLevelAdapter.setDefFid(fid1);
                        }
                        int selectIndex = 0;
                        for (int i = 0; i < communityBeanList.size(); i++) {
                            if (TextUtils.equals(((CommunityBean) communityBeanList.get(i)).getFid(), fid1)) {
                                selectIndex = i;
                            }
                        }

                        setTwoLevelAdapter(oneLevelAdapter.getItem(selectIndex).getSubforums());

                        setThreeLevelAdapter(twoLevelAdapter.getSelectIndex());

                    } else {
                        oneLevelAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }

    @Event(value = R.id.forum_choose_1ist_1, type = AdapterView.OnItemClickListener.class)
    private void oneLevelClickItem(AdapterView<?> parent, View view, int position, long id) {
        if (isSingleClick(view)) {
            oneLevelAdapter.setSelectIndex(position);
            setTwoLevelAdapter(oneLevelAdapter.getItem(position).getSubforums());
            if (twoLevelAdapter != null) {
                setThreeLevelAdapter(twoLevelAdapter.getSelectIndex());
            }

        }
    }

    @Event(value = R.id.forum_choose_1ist_2, type = AdapterView.OnItemClickListener.class)
    private void twoLevelClickItem(AdapterView<?> parent, View view, int position, long id) {
        if (twoLevelAdapter.getSelectIndex() == position) return;
        twoLevelAdapter.setSelectIndex(position);
        setThreeLevelAdapter(position);
    }

    @Event(value = R.id.forum_choose_1ist_3, type = AdapterView.OnItemClickListener.class)
    private void threeLevelClickItem(AdapterView<?> parent, View view, int position, long id) {
        if (threeLevelAdapter != null) {
            if (threeLevelAdapter.getSelectIndex() == position) return;
            threeLevelAdapter.setSelectIndex(position);
        }
    }

    private void setTwoLevelAdapter(List<CommunitySubBean> datas) {
        twoData.clear();
        if (!DataUtils.isEmpty(datas))
            twoData.addAll(datas);
        if (twoLevelAdapter == null) {
            twoLevelAdapter = new ForumChooseAdapter(this, twoData, R.layout.item_forum_choose);
            twoLevelAdapter.setDefFid(fid2);
            forum_choose_1ist_2.setAdapter(twoLevelAdapter);
        } else {
            twoLevelAdapter.setSelectIndex(-1);
        }
    }


    private void setThreeLevelAdapter(int position) {

        threeData.clear();
        List<CommunitySubTypeBean> typeDatas = new ArrayList<>();
        if (position > -1 && twoLevelAdapter != null) {
            List<CommunitySubTypeBean> datas = twoLevelAdapter.getItem(position).getSubtypesList();
            if (!DataUtils.isEmpty(twoLevelAdapter.getItem(position).getRelatedgroupList())) {
                threeData.addAll(twoLevelAdapter.getItem(position).getRelatedgroupList());
            }
            if (!DataUtils.isEmpty(datas))
                threeData.addAll(datas);

            if (!DataUtils.isEmpty(twoLevelAdapter.getItem(position).getSubforumList())) {
                typeDatas.addAll(twoLevelAdapter.getItem(position).getSubforumList());
            }
        }

        if (DataUtils.isEmpty(threeData) || threeData.size() == 1) {
            if (!DataUtils.isEmpty(threeData)) {
                fid3 = threeData.get(0).getFid();
            }
            if (typeDatas.size() <= 1) {
                typeAdapter = null;
                if (typeDatas.size() == 1) {
                    typeid = typeDatas.get(0).getFid();
                }
                forum_choose_1ist_3.setAdapter(null);
                setLineView(false);
            } else {
                typeAdapter = new ForumChooseTypeAdapter(this, typeDatas, R.layout.item_forum_type_choose);
                typeAdapter.setTypeId(defTypeId);
                forum_choose_1ist_3.setAdapter(typeAdapter);
                setLineView(true);
            }
            threeLevelAdapter = null;
        } else {
            threeLevelAdapter = new ForumChooseAdapter3(this, threeData, R.layout.item_forum_choose, typeDatas);
            threeLevelAdapter.setDefFid(fid3, defTypeId);
            forum_choose_1ist_3.setAdapter(threeLevelAdapter);
            typeAdapter = null;
            setLineView(true);
        }


    }

    private void setLineView(boolean isShow) {
        if (twoLevelAdapter != null) {
            twoLevelAdapter.isShowLine(isShow);
        }
        WidgetUtils.setVisible(lineView, isShow);

    }


    @Event({R.id.toolbar_left, R.id.toolbar_right, R.id.select_zhobo_img})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left:
                onBackPressed();
                break;
            case R.id.toolbar_right:
                if (!TextUtils.equals(UserManger.getUserInfo().getAuthed(), "1")) {//未实名认证
                    showAuthodDialog();
                    return;
                }
                if (twoLevelAdapter == null)
                    return;
                fid2 = twoLevelAdapter.getFid();
                if (typeAdapter != null)
                    typeid = typeAdapter.getTypeid();
                if (threeLevelAdapter != null) {
                    fid3 = threeLevelAdapter.getFid();
                    typeid = threeLevelAdapter.getTypeid();
                }
                if (TextUtils.isEmpty(fid2)) {
                    showRemindDialog();
                    return;
                }

                boolean isRelatedgroupid = false;
                if (!DataUtils.isEmpty(twoLevelAdapter.getSelectBean().getRelatedgroupList())) {
                    for (CommunitySubTypeBean bean : twoLevelAdapter.getSelectBean().getRelatedgroupList()) {
                        if (TextUtils.equals(fid3, bean.getFid())) {
                            isRelatedgroupid = true;
                        }
                    }
                }
                requestSendThread(fid2, fid3, typeid, isRelatedgroupid);
                break;

            case R.id.select_zhobo_img:
                ImageView iv = (ImageView) v;
                if (is_feed) {
                    iv.setImageResource(R.drawable.choose_zhibo_nomal);
                } else {
                    iv.setImageResource(R.drawable.choose_zhibo_select);
                }
                is_feed = !is_feed;
                break;
        }
    }


    @Override
    public void onBackPressed() {
        closeRemindDialog();
    }

    /**
     * 发帖
     */
    private void requestSendThread(String fid2, String fid3, String typeid, boolean isRelatedgroupid) {
        showDialog();
        String formhash = SharedPreferencesString.getInstances().getStringToKey("formhash");
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_PUBLISHPOST).setCharsetGBK();
        params.addQueryStringParameter("fid", fid2);
        params.addBodyParameter("fid", fid2 + "");
        params.addBodyParameter("typeid", typeid);
        if (!TextUtils.isEmpty(fid3)) {
            if (isRelatedgroupid) {
                params.addBodyParameter("relatedgroupid", fid3);
            } else {
                params.addBodyParameter("subtypeid", fid3);
            }
        }
        params.addBodyParameter("subject", subject);
        params.addBodyParameter("message", message);
        params.addBodyParameter("formhash", formhash);
        params.addBodyParameter("attachnew", attachId);
        params.addBodyParameter("vids", vids);
        if (!TextUtils.isEmpty(location)) {
            params.addBodyParameter("location", AmapLocation.mLongitude + "," + AmapLocation.mLatitude + "," + location);
            params.addBodyParameter("mapx", AmapLocation.mLongitude + "");
            params.addBodyParameter("mapy", AmapLocation.mLatitude + "");
        }

        if (is_feed) {
            params.addBodyParameter("is_feed", "1");
        }

        params.addQueryStringParameter("transcode", "yes");


        params.addBodyParameter("airportid", airportid);
        params.addBodyParameter("flightid", flightid);
        params.addBodyParameter("flight", flight);
        params.addBodyParameter("collectionid", collectionid);


        XutilsHttp.Post(params, new DataCallback<SendThreadResultBean>() {


            @Override
            public void flySuccess(DataBean<SendThreadResultBean> result) {
                if (TextUtils.equals(result.getCode(), "post_newthread_succeed") || TextUtils.equals(result.getCode(), "post_newthread_mod_succeed")) {
                    if (locationBean != null) {
                        if (result.getDataBean() != null) {
                            sendComment(result.getDataBean().getTid());
                        }
                    }
                    Bundle bundle = new Bundle();
                    bundle.putInt("code", FinalUtils.HOME_TO_MYTHREAD);
                    jumpActivity(HomeActivity.class, bundle);
                }
                ToastUtils.showToast(result.getMessage());
            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                dialogDismiss();
            }
        });

    }


    private void closeRemindDialog() {
        MyAlertDialog.Builder builder = new MyAlertDialog.Builder(this);
        builder.setTitle(null);
        builder.setMessage("是否继续发表帖子?");
        builder.setNegativeButton("返回修改", new MyAlertDialog.OnAlertViewClickListener() {
            @Override
            public void OnAlertViewClick() {
                finish();
            }
        });
        builder.setPositiveButton("继续发表");
        builder.create().show();
    }

    private void showRemindDialog() {
        MyAlertDialog.Builder builder = new MyAlertDialog.Builder(this);
        builder.setTitle(null);
        builder.setIsOneButton(true);
        builder.setMessage("请选择发表到的版块");
        builder.setPositiveButton("确定");
        builder.create().show();
    }

    /**
     * 实名认证提示
     */
    private void showAuthodDialog() {
        MyAlertDialog.Builder builder = new MyAlertDialog.Builder(ForumChooseActivity.this);
        builder.setTitle("手机号绑定提示");
        builder.setMessage("依《网络安全法》相关要求发帖之前需先完成手机绑定");
        builder.setNegativeButton("放弃");
        builder.setPositiveButton("去绑定", new MyAlertDialog.OnAlertViewClickListener() {
            @Override
            public void OnAlertViewClick() {
                Bundle bundle = new Bundle();
                bundle.putString("url", Utils.HtmlUrl.HTML_BIND_PHONE + "?access_token=" + UserManger.getUserInfo().getPs_token().getAccess_token());
                jumpActivityForResult(SystemWebActivity.class, bundle, UPDATE_MOBILE);
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_MOBILE) {
            if (resultCode == RESULT_OK) {
            }
        }
    }


    /**
     * 发送点评
     */
    private void sendComment(String tid) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_COMMENT_LOCATION);
        params.addBodyParameter("tid", tid);
        if (TextUtils.equals(locationBean.getType(), "hotel")) {
            params.addBodyParameter("cotype", "0");
        } else if (TextUtils.equals(locationBean.getType(), "airport")) {
            params.addBodyParameter("cotype", "1");
            params.addBodyParameter("flight", flight);
            params.addBodyParameter("flightid", flightid);
        } else if (TextUtils.equals(locationBean.getType(), "lounge")) {
            params.addBodyParameter("cotype", "3");
        } else {
            params.addBodyParameter("cotype", "4");
        }

        params.addBodyParameter("itemid", locationBean.getId());
        params.addBodyParameter("itemname", locationBean.getName());
        params.addBodyParameter("star", String.valueOf(star));
        params.addQueryStringParameter("transcode", "yes");


        params.addBodyParameter("tags", tags);
        params.addBodyParameter("utags", utags);

        showDialog();
        XutilsHttp.Post(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                if (JsonAnalysis.isSuccessEquals1(result)) {
                }
                ToastUtils.showToast(JsonAnalysis.getMessage(result));
            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                dialogDismiss();
            }
        });
    }
}
