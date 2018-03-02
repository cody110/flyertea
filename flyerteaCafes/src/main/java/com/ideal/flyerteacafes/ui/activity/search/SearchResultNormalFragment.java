package com.ideal.flyerteacafes.ui.activity.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.HomeActivity;
import com.ideal.flyerteacafes.ui.activity.writethread.WriteMajorThreadContentActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;

/**
 * Created by fly on 2018/1/21.
 */

public class SearchResultNormalFragment extends BaseFragment {


    private String searchKey;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.normal_search_result, container, false);
        x.view().inject(this, view);
        return view;
    }

    @Event({R.id.btn_strategy, R.id.btn_questions})
    private void click(View view) {
        switch (view.getId()) {
            case R.id.btn_strategy:
                Bundle bundle = new Bundle();
                bundle.putInt("code", FinalUtils.HOME_GONGLUE);
                jumpActivity(HomeActivity.class, bundle);

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("name", searchKey);
                MobclickAgent.onEvent(getContext(), FinalUtils.EventId.search_noResult_articleBtn_click, map);

                break;
            case R.id.btn_questions:
                jumpActivity(WriteMajorThreadContentActivity.class);

                map = new HashMap<String, String>();
                map.put("name", searchKey);
                MobclickAgent.onEvent(getContext(), FinalUtils.EventId.search_noResult_questionBtn_click, map);
                break;
        }
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

}
