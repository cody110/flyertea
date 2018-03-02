package com.ideal.flyerteacafes.ui.fragment.page;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.FriendAdapter;
import com.ideal.flyerteacafes.caff.AmapLocation;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.dal.FriendsInfoHelper;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.FriendsInfo;
import com.ideal.flyerteacafes.model.entity.MyFrirends;
import com.ideal.flyerteacafes.model.entity.UserBean;
import com.ideal.flyerteacafes.ui.activity.NearbyActivity;
import com.ideal.flyerteacafes.ui.activity.search.SearchMemberActivity;
import com.ideal.flyerteacafes.ui.activity.UserDatumActvity;
import com.ideal.flyerteacafes.ui.activity.search.ThreadSearchActivity2;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.ui.sortlistview.CharacterParser;
import com.ideal.flyerteacafes.ui.sortlistview.PinyinComparator;
import com.ideal.flyerteacafes.ui.sortlistview.SideBar;
import com.ideal.flyerteacafes.ui.sortlistview.SideBar.OnTouchingLetterChangedListener;
import com.ideal.flyerteacafes.utils.TvDrawbleUtils;
import com.ideal.flyerteacafes.utils.SmartUtil;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.V;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;

public class FriendsFragment extends BaseFragment {

    private View view = null;
    @ViewInject(R.id.lv_friends)
    private ListView friendLv;

    @ViewInject(R.id.sidrbar)
    private SideBar sideBar;

    @ViewInject(R.id.dialog)
    private TextView dialog;

    /**
     * 我附近的人
     **/
    @ViewInject(R.id.mrl_nearby)
    private RelativeLayout mrl_nearby;
    /**
     * 添加朋友
     **/
    @ViewInject(R.id.mrl_add_friends)
    private RelativeLayout mrl_add_friends;

    private TextView chatNewnum;

    private FriendAdapter adapter;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<FriendsInfo> sourceDataList = new ArrayList<FriendsInfo>();

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    FriendsInfoHelper friendsHelper;

    private static final int REQUEST_FRIEND = 1;

    private int onclickIndex = -1;
    private UserBean userBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_friends, null);
        x.view().inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initViews();
        initLisenner();
        requestFriends();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == REQUEST_FRIEND)//删除好友返回
            {
                if (onclickIndex < sourceDataList.size()) {
                    FriendsInfo info = sourceDataList.get(onclickIndex);
                    friendsHelper.deleteFriendsInfoById(info.getFuid());
                    sourceDataList.remove(onclickIndex);
                    adapter.notifyDataSetChanged();
                    onclickIndex = -1;
                }
            }
        }

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initData() {
        getDbFriendslist();
        userBean = (UserBean) getActivity().getIntent().getSerializableExtra("userBean");
    }


    private void getDbFriendslist() {
        friendsHelper = FriendsInfoHelper.getInstance();
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        List<FriendsInfo> friends = friendsHelper.getListAll(FriendsInfo.class);
        sortList(friends);
    }

    public void initViews() {


        View headerView = LayoutInflater.from(mActivity).inflate(R.layout.item_header_friends, null);
        x.view().inject(this, headerView);

        friendLv.addHeaderView(headerView);

        TextView labelNearby = V.f(mrl_nearby, R.id.comment_person_name);
        labelNearby.setText(R.string.friends_ac_people_nearby);
        TvDrawbleUtils.chageDrawble(labelNearby, R.mipmap.friends_ac_nearby);

        TextView labelAddFriends = V.f(mrl_add_friends, R.id.comment_person_name);
        labelAddFriends.setText(R.string.friends_ac_add_friends);
        TvDrawbleUtils.chageDrawble(labelAddFriends, R.mipmap.friends_ac_add_friends);

        V.f(mrl_add_friends, R.id.item_person_divider).setVisibility(View.INVISIBLE);

        adapter = new FriendAdapter(getActivity(), sourceDataList);
        friendLv.setAdapter(adapter);
        sideBar.setTextView(dialog);
    }


    public void onEventMainThread(TagEvent event) {
        if (TagEvent.TAG_ADD_FRIEND_SUCCESS == event.getTag()) {
            requestFriends();
        }
    }


    private void initLisenner() {
        EventBus.getDefault().register(this);
        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    friendLv.setSelection(position);
                }

            }
        });

        friendLv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position > 0) {
                    FriendsInfo friendsInfo = (FriendsInfo) adapter
                            .getItem(position - 1);
                    Bundle bundle = new Bundle();
                    bundle.putString("uid", friendsInfo.getUid());
                    bundle.putString("activity", "FriendsFragment");
                    onclickIndex = position - 1;
                    jumpActivityForResult(UserDatumActvity.class, bundle, REQUEST_FRIEND);
                }
            }
        });

    }

    @Event({R.id.mrl_nearby, R.id.mrl_add_friends})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.mrl_nearby://附近的人
                if (AmapLocation.mLatitude == 0) {
                    SmartUtil.BToast(getActivity(), getString(R.string.getlocation_is_null));
                    return;
                }
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("userBean", userBean);
                jumpActivity(NearbyActivity.class, bundle2);
                break;
            case R.id.mrl_add_friends://添加朋友
                jumpActivity(ThreadSearchActivity2.class);
                break;
        }
    }

    /**
     * 好友列表请求
     **/
    private void requestFriends() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_FRIENDS);
        params.addQueryStringParameter("page", String.valueOf(1));
        params.addQueryStringParameter("pagesize", String.valueOf(2500));
        XutilsHttp.Get(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                MyFrirends myFrirends = JsonAnalysis.getFriends(result);
                if (myFrirends == null)
                    return;
                if (myFrirends.getVariables() == null)
                    return;
                if (myFrirends.getVariables().getList() == null)
                    return;
                SharedPreferencesString.getInstances(getActivity()).savaToString("formhash", myFrirends.getVariables().getFormhash());
                SharedPreferencesString.getInstances(getActivity()).commit();
                // 插入所有数据进去
                friendsHelper.saveListAll(myFrirends.getVariables().getList(),
                        FriendsInfo.class);
                sortList(myFrirends.getVariables().getList());
                adapter.notifyDataSetChanged();
            }
        });
    }


    /**
     * 排序
     */
    private void sortList(List<FriendsInfo> list) {
        if (list == null)
            return;
        if (sourceDataList != null)
            sourceDataList.clear();
        for (FriendsInfo friendsInfo : list) {
            if (friendsInfo == null)
                continue;
            if (TextUtils.isEmpty(friendsInfo.getUsername()))
                continue;
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(friendsInfo
                    .getUsername());
            if (TextUtils.isEmpty(pinyin)) {
                return;
            }
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                friendsInfo.setSortLetters(sortString.toUpperCase());
            } else {
                friendsInfo.setSortLetters("#");
            }
            sourceDataList.add(friendsInfo);
        }
        // 根据a-z进行排序源数据
        Collections.sort(sourceDataList, pinyinComparator);
    }
}
