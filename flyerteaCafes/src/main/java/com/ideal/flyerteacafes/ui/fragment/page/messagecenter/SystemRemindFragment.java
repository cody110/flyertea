package com.ideal.flyerteacafes.ui.fragment.page.messagecenter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.OtherMessageAdapter;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.NotificationBean;
import com.ideal.flyerteacafes.ui.activity.RemindDetailsActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.NewPullRefreshFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.ui.fragment.presenter.SystemRemindPresenter;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.Marks;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cindy on 2017/3/20.
 */
public class SystemRemindFragment extends NewPullRefreshFragment<NotificationBean> {

    SystemRemindPresenter myPresenter;

    @Override
    public void initViews() {
        super.initViews();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                NotificationBean bean = mPresenter.getDatas().get(position);
                if (TextUtils.equals(bean.getType(), "post")) {
                    Intent intent = new Intent(getActivity(), ThreadActivity.class);
                    intent.putExtra("tid", bean.getFrom_id());
                    startActivity(intent);
                } else {
                    String msg = bean.getNote();
                    Intent intent = new Intent(getActivity(), RemindDetailsActivity.class);
                    intent.putExtra("msg", msg);
                    startActivity(intent);
                }

                if (TextUtils.equals(bean.getIsnew(), Marks.MESSAGE_UNREAD)) {
                    myPresenter.requestMarkPositionReads(position);
                }
            }
        });
        addNormalLayout(R.layout.include_no_message);
    }

    @Override
    protected CommonAdapter<NotificationBean> createAdapter(List<NotificationBean> datas) {
        WidgetUtils.setVisible(normalLayout, DataUtils.isEmpty(datas));
        return new OtherMessageAdapter(getActivity(), datas, R.layout.item_message_center_other_message);
    }

    @Override
    protected PullRefreshPresenter<NotificationBean> createPresenter() {
        return myPresenter = new SystemRemindPresenter();
    }
}
