package com.ideal.flyerteacafes.ui.fragment.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.ui.activity.SettingActivity;
import com.ideal.flyerteacafes.ui.activity.UserDatumActvity;
import com.ideal.flyerteacafes.ui.activity.UserInfoActivity;
import com.ideal.flyerteacafes.adapters.NearByAdapter;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.caff.AmapLocation;
import com.ideal.flyerteacafes.model.entity.NearBean;
import com.ideal.flyerteacafes.model.entity.NearBean.NearInfo;
import com.ideal.flyerteacafes.model.entity.TakeNotesBean;
import com.ideal.flyerteacafes.model.entity.UserBean;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.utils.SmartUtil;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.json.JSONException;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NearbyFragment extends BaseFragment {

    private View view = null;

    @ViewInject(R.id.near_fragment_listview)
    private ListView lvNearby;
    @ViewInject(R.id.near_not_setting_kejian)
    private View toSettingView;

    private List<NearInfo> infoList = new ArrayList<NearBean.NearInfo>();
    private NearByAdapter nearByAdapter;
    private NearBean nearBean;
    private UserBean userBean;
    private int page = FinalUtils.START_PAGE;
    private SharedPreferencesString preferences;
    private static final int REQUEST_SETTING_CODE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_near, null);
        x.view().inject(this, view);
        userBean = (UserBean) getActivity().getIntent().getSerializableExtra("userBean");
        initView();
        return view;
    }

    private void initView() {
        preferences = SharedPreferencesString.getInstances(getActivity());
        if (preferences.getIntToKey("privacyMode") == 0) {
            nearByAdapter = new NearByAdapter(getActivity(), infoList);
            lvNearby.setAdapter(nearByAdapter);
            getNear();
            toSettingView.setVisibility(view.GONE);
        } else {
            toSettingView.setVisibility(view.VISIBLE);
        }
    }

    @Event(value = R.id.near_fragment_listview,type = AdapterView.OnItemClickListener.class)
    private void itemClick(AdapterView<?> parent, View view,
                          int position, long id) {
        NearInfo info = (NearInfo) nearByAdapter
                .getItem(position);

        String uid = SharedPreferencesString.getInstances(getActivity()).getStringToKey("uid");
        if (uid.equals(info.getUid())) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("userBean", userBean);
            bundle.putString("type", "search");
            jumpActivity(UserInfoActivity.class, bundle);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("uid", info.getUid());
            if (info.getIsfriend().equals("0"))
                bundle.putString("activity", "SearchMemberActivity");
            else
                bundle.putString("activity", "FriendsFragment");
            jumpActivity(UserDatumActvity.class, bundle);

        }
    }

    @Event(R.id.near_to_setting)
    public void onActionClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isNearTo", true);
        jumpActivityForResult(SettingActivity.class, bundle, REQUEST_SETTING_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == REQUEST_SETTING_CODE) {
                initView();
            }
        }
    }

    private void getNear() {
        FlyRequestParams params=new FlyRequestParams(Utils.HttpRequest.HTTP_NEAR);
        params.addQueryStringParameter("mapx", AmapLocation.mLongitude + "");
        params.addQueryStringParameter("mapy", AmapLocation.mLatitude + "");
//		params.addQueryStringParameter("page", page+"");
//		params.addQueryStringParameter("pagesize", pagesize+"");
        XutilsHttp.Get( params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                int position = 0;
                nearBean = JsonAnalysis.getDataBean(result, NearBean.class);
                if (page == FinalUtils.START_PAGE) {
                    infoList.clear();
                } else {
                    position = infoList.size();
                }
                if (nearBean != null) {
                    if (nearBean.getList() != null) {
                        infoList.addAll(nearBean.getList());
                    }
                    if (!!DataUtils.isEmpty(nearBean.getList())) {
                        SmartUtil.BToast(getActivity(), getString(R.string.near_not_people));
                    }
                }
                nearByAdapter.notifyDataSetChanged();
                if (page != FinalUtils.START_PAGE)
                    lvNearby.setSelectionFromTop(position, (int) (FinalUtils.FROM_TOP * SharedPreferencesString.getInstances().getScale()));
            }
        });
    }


}
