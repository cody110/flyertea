package com.ideal.flyerteacafes.ui.fragment.page.tab.community;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.CommunityListAdapter;
import com.ideal.flyerteacafes.model.entity.CommunityBean;
import com.ideal.flyerteacafes.ui.activity.HomeActivity;
import com.ideal.flyerteacafes.ui.activity.interfaces.IChooseCommunity;
import com.ideal.flyerteacafes.ui.activity.presenter.ChooseCommunityPresenter;
import com.ideal.flyerteacafes.ui.fragment.page.Base.MVPBaseFragment;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * @version V6.6.0
 * @description: 社区
 * @author: Cindy
 * @date: 2016/11/7 11:17
 */
public class ChooseCommunityFm extends MVPBaseFragment<IChooseCommunity, ChooseCommunityPresenter> implements IChooseCommunity {

    /**
     * 社区Listview
     **/
    @ViewInject(R.id.mlv_choose_communit)
    ListView mlv_choose_communit;

    private CommunityListAdapter communityListAdapter;


    private HomeActivity homeActivity;
    private boolean isFirst = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        homeActivity = (HomeActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_choose_community, container, false);
        x.view().inject(this, view);
        mPresenter.init(mActivity);
        return view;
    }

    @Event({R.id.toolbar_left, R.id.toolbar_right})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left:
                if (isFirst) {
                    mPresenter.actionFirstDefFollow();
                } else {
                    homeActivity.setCommunityFragment();
                }
                break;
            case R.id.toolbar_right://完成
                mPresenter.actionOk();
                break;
        }
    }


    @Override
    protected ChooseCommunityPresenter createPresenter() {
        return new ChooseCommunityPresenter();
    }

    @Override
    public void callbackCommunity(List<CommunityBean> communityBeanList) {
        if (communityListAdapter == null) {
            communityListAdapter = new CommunityListAdapter(mActivity, communityBeanList, R.layout.activity_choose_communit_list_item);
            mlv_choose_communit.setAdapter(communityListAdapter);
        } else {
            communityListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void actionToCommunityFm() {
        homeActivity.setCommunityFragment();
    }

    public void setIsFirst(boolean bol) {
        isFirst = bol;
    }


}
