package com.ideal.flyerteacafes.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.NewFriendsAdapter;
import com.ideal.flyerteacafes.adapters.NewFriendsAdapter.INewFriends;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.dal.BaseHelper;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.NewFriendsBean;
import com.ideal.flyerteacafes.model.entity.NewFriendsBean.NewFriendsInfo;
import com.ideal.flyerteacafes.model.entity.Userinfo;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.controls.PullToRefreshView;
import com.ideal.flyerteacafes.ui.controls.PullToRefreshView.OnFooterRefreshListener;
import com.ideal.flyerteacafes.ui.controls.PullToRefreshView.OnHeaderRefreshListener;
import com.ideal.flyerteacafes.ui.dialog.CustomDialog;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

@ContentView(R.layout.activity_new_friends)
public class NewFriendsActivity extends BaseActivity implements
        INewFriends, OnHeaderRefreshListener, OnFooterRefreshListener {

    @ViewInject(R.id.include_left_title_text)
    TextView titleView;
    @ViewInject(R.id.fragment_refreshview_listview)
    ListView friendListView;
    @ViewInject(R.id.fragment_refreshview)
    private PullToRefreshView mPullToRefreshView;

    private NewFriendsAdapter adapter;
    private List<NewFriendsInfo> friensList = new ArrayList<NewFriendsBean.NewFriendsInfo>();

    private int page = FinalUtils.START_PAGE;
    private SharedPreferencesString preferences;

    private NewFriendsBean newFriends;
    BaseHelper helper;
    private CustomDialog custom = new CustomDialog();
    private Dialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        dialog = custom.loadingDialog(this);
        preferences = SharedPreferencesString.getInstances(this);
        titleView.setText("新朋友");
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        adapter = new NewFriendsAdapter(this, friensList,
                R.layout.listview_item_friend, this);
        friendListView.setAdapter(adapter);
        helper = BaseHelper.getInstance();
        requestNewFriends();
    }

    @Event(R.id.include_left_title_back_layout)
    private void onActionClick(View v) {
        finish();
    }

    /**
     * 当前所有请求加我为好友的人， 每页20个， 可以翻页
     */
    private void requestNewFriends() {
        dialog.show();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_NEWFRIENDS);
        params.addQueryStringParameter("page", page + "");
        XutilsHttp.Get(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                int position = 0;
                newFriends = JsonAnalysis.getDataBean(result, NewFriendsBean.class);
                if (page == FinalUtils.START_PAGE) {
                    friensList.clear();
                    if (newFriends == null || newFriends.getList() == null
                            || newFriends.getList().size() == 0)
                        BToast("没有新的朋友");
                } else {
                    position = friensList.size();
                }
                if (newFriends != null) {
                    List<NewFriendsInfo> list = newFriends.getList();
                    if (list != null && list.size() > 0) {
                        friensList.addAll(list);
                    }
                }

                adapter.notifyDataSetChanged();
                if (page != 1)
                    friendListView.setSelectionFromTop(position,
                            (int) (FinalUtils.FROM_TOP * SharedPreferencesString.getInstances().getScale()));
            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                dialog.dismiss();
                mPullToRefreshView.onHeaderRefreshComplete();
                mPullToRefreshView.onFooterRefreshComplete();
            }
        });
    }

    /**
     * 同意添加好友
     *
     * @param uid
     */
    private void agreeAddFriends(final String uid) {
        dialog.show();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_AGREEAddFRIENDS);
        params.addQueryStringParameter("uid", uid);
        params.addBodyParameter("add2submit", "true");
        params.addBodyParameter("formhash", preferences.getStringToKey("formhash"));
        params.addBodyParameter("gid", "6");
        params.addBodyParameter("handlekey", "afr_" + uid);

        XutilsHttp.Post(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                BaseBean msg = JsonAnalysis.getMessageBean(result);
                if (msg != null) {

                    if (msg.getMessage() != null
                            && msg.getMessage().equals("您已和{username}成为好友")) {
                        BToast("同意添加好友成功");
                        preferences.savaToString("addId");
                        preferences.commit();
                        if (id < friensList.size()) {
                            NewFriendsInfo newFriendsInfo = friensList
                                    .get(id);
                            Userinfo info = new Userinfo();
                            info.setUid(newFriendsInfo.getFuid());
                            info.setUsername(newFriendsInfo.getFusername());
                            info.setFace(newFriendsInfo.getFace());
                            helper.saveBean(info);
                            friensList.remove(id);
                            adapter.notifyDataSetChanged();
                            EventBus.getDefault().post(new TagEvent(TagEvent.TAG_ADD_FRIEND_SUCCESS));
                        }
                    } else {
                        BToast(msg.getMessage());
                    }
                }
            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                dialog.dismiss();
                mPullToRefreshView.onHeaderRefreshComplete();
                mPullToRefreshView.onFooterRefreshComplete();
            }

        });
    }

    private void ignoreAddFriends(String uid) {
        FlyRequestParams params=new FlyRequestParams(Utils.HttpRequest.HTPP_IGNOREADDFRIENDS);
        params.addQueryStringParameter("uid", uid);
        params.addBodyParameter("formhash", preferences.getStringToKey("formhash"));
        params.addBodyParameter("friendsubmit", "true");
        params.addBodyParameter("handlekey", "afi_" + uid);
        XutilsHttp.Post(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                BaseBean msg = JsonAnalysis.getMessageBean(result);
                BToast(msg.getMessage());
            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                dialog.dismiss();
                mPullToRefreshView.onHeaderRefreshComplete();
                mPullToRefreshView.onFooterRefreshComplete();
            }
        });
    }

    int id;

    @Override
    public <T> void agreeAddFriend(T t) {
        NewFriendsInfo info = (NewFriendsInfo) t;
        id = friensList.indexOf(info);
        if (id == 0)
            agreeAddFriends(info.getFuid());
        else
            ignoreAddFriends(info.getFuid());
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        // TODO Auto-generated method stub
        if (newFriends != null) {
            if (newFriends.getPage() < newFriends.getCount()) {
                page++;
                requestNewFriends();
                return;
            }
        }
        mPullToRefreshView.onFooterRefreshComplete();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        // TODO Auto-generated method stub
        page = FinalUtils.START_PAGE;
        requestNewFriends();
    }

}
