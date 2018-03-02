package com.ideal.flyerteacafes.ui.fragment.page;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.ReadsBean;
import com.ideal.flyerteacafes.model.entity.ReadsWebBean;
import com.ideal.flyerteacafes.ui.activity.presenter.ArticlePresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.threads.ArticleContentActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.NewPullRefreshFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.List;

/**
 * Created by fly on 2016/12/1.
 * 用户登录了的浏览记录
 */

public class MyReadsFragment2 extends NewPullRefreshFragment<ReadsWebBean> {


    @Override
    public void initViews() {
        super.initViews();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(TextUtils.equals(datas.get(i).getIdtype(),ReadsBean.IDTYPE_TID)){
                    if(DataUtils.isNormal(datas.get(i).getProfessional())){
                        Bundle bundle=new Bundle();
                        bundle.putString(ThreadPresenter.BUNDLE_TID,datas.get(i).getId());
                        jumpActivity(ThreadActivity.class,bundle);
                    }else{
                        Bundle bundle=new Bundle();
                        bundle.putString(ThreadPresenter.BUNDLE_TID,datas.get(i).getId());
                        jumpActivity(ThreadActivity.class,bundle);
                    }
                }else{
                    Bundle bundle=new Bundle();
                    bundle.putString(ArticlePresenter.BUNDLE_AID,datas.get(i).getId());
                    jumpActivity(ArticleContentActivity.class,bundle);
                }
            }
        });
    }

    @Override
    protected CommonAdapter<ReadsWebBean> createAdapter(List<ReadsWebBean> datas) {
        return new CommonAdapter<ReadsWebBean>(getActivity(), datas, R.layout.item_mythread_reply) {
            @Override
            public void convert(ViewHolder holder, ReadsWebBean readsBean) {
                holder.setText(R.id.item_my_reply_title, readsBean.getSubject());
                holder.setText(R.id.item_my_reply_forum, readsBean.getForumname());
                holder.setText(R.id.item_my_reply_time, DataUtils.conversionTime(readsBean.getDbdateline()));
            }
        };
    }

    @Override
    protected PullRefreshPresenter<ReadsWebBean> createPresenter() {
        return new PullRefreshPresenter<ReadsWebBean>() {
            @Override
            public void requestDatas() {
                getDialog().proDialogShow();
                FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_READS);
                params.addQueryStringParameter("page", String.valueOf(page));
                XutilsHttp.Get(params, new ListDataHandlerCallback(ListDataCallback.DATA, ReadsWebBean.class));
            }
        };
    }
}
