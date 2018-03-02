package com.ideal.flyerteacafes.ui.fragment.page;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.caff.ReadsManger;
import com.ideal.flyerteacafes.model.entity.ReadsBean;
import com.ideal.flyerteacafes.ui.activity.presenter.ArticlePresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.threads.ArticleContentActivity;
import com.ideal.flyerteacafes.ui.activity.threads.MajorCommentActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.NewPullRefreshFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.DataUtils;

import java.util.List;

/**
 * Created by fly on 2016/11/29.
 * 本地浏览记录
 */

public class MyReadsFragment extends NewPullRefreshFragment<ReadsBean> {


    @Override
    public void initViews() {
        super.initViews();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (TextUtils.equals(datas.get(i).getIdtype(), ReadsBean.IDTYPE_TID)) {
                    if (DataUtils.isNormal(datas.get(i).getProfessional())) {
                        Bundle bundle = new Bundle();
                        bundle.putString(ThreadPresenter.BUNDLE_TID, datas.get(i).getTid());
                        jumpActivity(ThreadActivity.class, bundle);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString(ThreadPresenter.BUNDLE_TID, datas.get(i).getTid());
                        jumpActivity(MajorCommentActivity.class, bundle);
                    }
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(ArticlePresenter.BUNDLE_AID, datas.get(i).getTid());
                    jumpActivity(ArticleContentActivity.class, bundle);
                }
            }
        });
    }

    @Override
    protected CommonAdapter<ReadsBean> createAdapter(List<ReadsBean> datas) {
        return new CommonAdapter<ReadsBean>(getActivity(), datas, R.layout.item_mythread_reply) {
            @Override
            public void convert(ViewHolder holder, ReadsBean readsBean) {
                holder.setText(R.id.item_my_reply_title, readsBean.getSubject());
                holder.setText(R.id.item_my_reply_forum, readsBean.getFname());
                holder.setText(R.id.item_my_reply_time, DataUtils.conversionTime(readsBean.getDbdateline()));
            }
        };
    }

    @Override
    protected PullRefreshPresenter<ReadsBean> createPresenter() {
        return new PullRefreshPresenter<ReadsBean>() {
            @Override
            public void requestDatas() {
                datas.clear();
                List<ReadsBean> readsBeanList = ReadsManger.getInstance().getLocaListAll();
                if (readsBeanList != null && !readsBeanList.isEmpty()) {
                    datas.addAll(readsBeanList);
                }
                getView().callbackData(datas);
                getView().pullToRefreshViewComplete();
            }
        };
    }
}
