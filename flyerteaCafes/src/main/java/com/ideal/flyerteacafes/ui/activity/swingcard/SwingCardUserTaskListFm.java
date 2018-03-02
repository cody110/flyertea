package com.ideal.flyerteacafes.ui.activity.swingcard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.HotTaskAdapter;
import com.ideal.flyerteacafes.adapters.UserTaskAdapter;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.TaskNameBean;
import com.ideal.flyerteacafes.model.entity.UserTaskBean;
import com.ideal.flyerteacafes.ui.fragment.page.Base.NewPullRefreshFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.List;

/**
 * Created by fly on 2017/4/12.
 * 刷卡任务 -待完成 -已完成
 */

public class SwingCardUserTaskListFm extends NewPullRefreshFragment<UserTaskBean> {


    UserTaskAdapter userTaskAdapter;

    @Override
    public void initViews() {
        super.initViews();
        setListviewDivider(null);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putInt(SwingCardTaskDetails.BUNDLE_MISSIONID, mPresenter.getDatas().get(i).getMyCardMissionId());
                jumpActivityForResult(SwingCardTaskDetails.class, bundle, 0);
            }
        });
    }

    @Override
    protected CommonAdapter<UserTaskBean> createAdapter(List<UserTaskBean> datas) {

        if (datas.size() == 0) {
            if (getArguments() != null) {
                WidgetUtils.setText(normalRemindTv, "您还没有已结束的任务哦");
            } else {
                WidgetUtils.setText(normalRemindTv, "您还没有待完成的任务哦");
            }

        } else {
            WidgetUtils.setText(normalRemindTv, "");
        }
        userTaskAdapter = new UserTaskAdapter(mActivity, datas, R.layout.item_user_task);
        if (getArguments() != null) {
            userTaskAdapter.setIsComplete(true);
        }
        return userTaskAdapter;
    }

    @Override
    protected PullRefreshPresenter<UserTaskBean> createPresenter() {
        return new PullRefreshPresenter<UserTaskBean>() {
            @Override
            public void requestDatas() {
                getDialog().proDialogShow();
                FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.KEY_TASK_USER);
                params.addQueryStringParameter("page", String.valueOf(page));
                if (getArguments() != null)
                    params.addQueryStringParameter("overtime", getArguments().getString("overtime"));
                XutilsHttp.Get(params, new ListDataHandlerCallback(ListDataCallback.LIST_KEY_TASKNAME, UserTaskBean.class));
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == mActivity.RESULT_OK) {
            headerRefreshing();
        }
    }
}
