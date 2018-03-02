package com.ideal.flyerteacafes.ui.fragment.page;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.dal.BaseHelper;
import com.ideal.flyerteacafes.model.loca.DraftInfo;
import com.ideal.flyerteacafes.ui.activity.writethread.WriteMajorThreadContentActivity;
import com.ideal.flyerteacafes.ui.activity.writethread.WriteThreadActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.WriteThreadPresenter;
import com.ideal.flyerteacafes.ui.fragment.page.Base.NewPullRefreshFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.DataUtils;

import org.xutils.ex.DbException;

import java.util.List;

/**
 * 我的草稿
 * Created by fly on 2016/11/28.
 */

public class MyDraftFragment extends NewPullRefreshFragment<DraftInfo> {

    @Override
    public void initViews() {
        super.initViews();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString(WriteThreadPresenter.BUNDLE_FROM_TYPE, WriteThreadPresenter.BUNDLE_FROM_DRAFT);
                bundle.putSerializable(WriteThreadPresenter.BUNDLE_DRAFT_DATA, datas.get(i));
                if (datas.get(i).isNormal()) {
                    jumpActivityForResult(WriteThreadActivity.class, bundle, 0);
                } else {
                    jumpActivityForResult(WriteMajorThreadContentActivity.class, bundle, 0);
                }
            }
        });
    }

    @Override
    protected CommonAdapter<DraftInfo> createAdapter(List<DraftInfo> datas) {
        return new CommonAdapter<DraftInfo>(getActivity(), datas, R.layout.item_mythread_reply) {
            @Override
            public void convert(ViewHolder holder, DraftInfo draftInfo) {
                TextView tv = holder.getView(R.id.item_my_reply_title);
                tv.setMaxEms(8);
                if (!TextUtils.isEmpty(draftInfo.getTitle())) {
                    holder.setText(R.id.item_my_reply_title, draftInfo.getTitle());
                } else if (!TextUtils.isEmpty(draftInfo.getContent())) {
                    holder.setText(R.id.item_my_reply_title, draftInfo.getContent());
                } else {
                    holder.setText(R.id.item_my_reply_title, "图片草稿");
                }


                String fName = draftInfo.getFname2();
                holder.setText(R.id.item_my_reply_forum, fName);
                holder.setText(R.id.item_my_reply_time, DataUtils.conversionTime(draftInfo.getTime()));
            }
        };
    }

    @Override
    protected PullRefreshPresenter<DraftInfo> createPresenter() {
        return new PullRefreshPresenter<DraftInfo>() {
            @Override
            public void requestDatas() {
                if (page == 1) datas.clear();
                List<DraftInfo> draftInfoList = null;
                try {
                    draftInfoList = BaseHelper.getInstance().getDbUtils().selector(DraftInfo.class).where("uid", "=", UserManger.getUserInfo().getMember_uid()).limit(10).offset(datas.size()).orderBy("time", true).findAll();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                if (draftInfoList != null)
                    datas.addAll(draftInfoList);
                getView().callbackData(datas);
                getView().pullToRefreshViewComplete();
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
