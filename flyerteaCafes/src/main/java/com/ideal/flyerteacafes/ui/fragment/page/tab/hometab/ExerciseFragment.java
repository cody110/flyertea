package com.ideal.flyerteacafes.ui.fragment.page.tab.hometab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.ExerciseAdapter;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.ExerciseBean;
import com.ideal.flyerteacafes.ui.activity.web.TbsWebActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.NewPullRefreshFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.List;

/**
 * Created by fly on 2016/6/6.
 */
public class ExerciseFragment extends NewPullRefreshFragment<ExerciseBean> {
    @Override
    protected CommonAdapter<ExerciseBean> createAdapter(List<ExerciseBean> datas) {
        return new ExerciseAdapter(mActivity, datas, R.layout.listview_flydaily_item_da_layout);
    }

    @Override
    protected PullRefreshPresenter<ExerciseBean> createPresenter() {
        return new PullRefreshPresenter<ExerciseBean>() {
            @Override
            public void requestDatas() {
                XutilsHttp.Get(new FlyRequestParams(splitPage(Utils.HttpRequest.HTTP_EXERCISE)), new ListDataHandlerCallback(ListDataCallback.LIST_KEY_LIST_EVENTLIST, ExerciseBean.class));
            }
        };
    }

    @Override
    public void initViews() {
        super.initViews();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String url = datas.get(position).getUrl();
                String def_url1 = "http://www.flyertea.com/thread";

                if (url.indexOf(def_url1) != -1) {
                    String[] array = url.split("-");
                    try {
                        int tid = Integer.parseInt(array[1]);
                        Intent intent = new Intent(mActivity,
                                ThreadActivity.class);
                        intent.putExtra("tid", tid);
                        intent.putExtra("webviewClickUrl", "webviewClickUrl");
                        startActivity(intent);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", datas.get(position).getUrl());
                    jumpActivity(TbsWebActivity.class, bundle);
                }
            }
        });
    }
}
