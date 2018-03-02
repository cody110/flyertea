package com.ideal.flyerteacafes.ui.fragment.page.messagecenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.SystemMessageBean;
import com.ideal.flyerteacafes.ui.activity.ChatActivity;
import com.ideal.flyerteacafes.ui.activity.RemindDetailsActivity;
import com.ideal.flyerteacafes.ui.controls.PullToRefreshView;
import com.ideal.flyerteacafes.ui.fragment.page.Base.NewPullRefreshFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.ui.fragment.presenter.SystemMessagePresenter;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;

import java.util.List;

/**
 * Created by fly on 2017/12/7.
 */

public class SystemMessageFragment extends NewPullRefreshFragment<SystemMessageBean> {


    SystemMessagePresenter myPresenter;

    @Override
    public void initViews() {
        super.initViews();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("msg", datas.get(i).getNote());
                jumpActivityForResult(RemindDetailsActivity.class, bundle, FinalUtils.REQUEST_CODE_PRIVATE_LETTER);
                //TODO 设为已读，不用再掉接口
                mPresenter.getDatas().get(i).setIsnew("0");
                adapter.notifyDataSetChanged();
                myPresenter.setMessageRead(i);
            }
        });
    }

    @Override
    protected CommonAdapter<SystemMessageBean> createAdapter(List<SystemMessageBean> datas) {
        return new CommonAdapter<SystemMessageBean>(mActivity, datas, R.layout.listview_item_remind_fragment_layout) {
            @Override
            public void convert(ViewHolder holder, SystemMessageBean systemMessageBean) {
                holder.setImageUrl(R.id.listview_item_remind_fragment_icon, systemMessageBean.getFace(), R.drawable.def_face_2);
                holder.setText(R.id.listview_item_remind_type, TextUtils.isEmpty(systemMessageBean.getAuthor()) ? "系统" : systemMessageBean.getAuthor());
                holder.setTextHtml(R.id.listview_item_remind_fragment_body, systemMessageBean.getMessage());
                holder.setText(R.id.listview_item_remind_fragment_time, DataUtils.conversionTime(systemMessageBean.getDateline()));
                holder.setVisible(R.id.listview_item_remind_fragment_isread, false);
            }
        };
    }

    @Override
    protected PullRefreshPresenter<SystemMessageBean> createPresenter() {
        return myPresenter = new SystemMessagePresenter();
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        pullToRefreshViewComplete();
    }
}
