package com.ideal.flyerteacafes.ui.fragment.page;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.MyPostBean;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.NewPullRefreshFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.List;

public class MyReplyFragment extends NewPullRefreshFragment<MyPostBean> {


    @Override
    public void initViews() {
        super.initViews();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Bundle bundle = new Bundle();
                bundle.putString(ThreadPresenter.BUNDLE_TID, String.valueOf(datas.get(pos).getTid()));
//                if (datas.get(pos).isNormal()) {
                    jumpActivity(ThreadActivity.class, bundle);
//                }else{
//                    jumpActivity(MajorCommentActivity.class, bundle);
//                }
            }
        });
    }

    @Override
    protected CommonAdapter<MyPostBean> createAdapter(List<MyPostBean> datas) {
        if(datas.size()==0)
            ToastUtils.showToast("暂无数据");
        return new CommonAdapter<MyPostBean>(getActivity(),datas,R.layout.item_mythread_reply) {
            @Override
            public void convert(ViewHolder holder, MyPostBean myPostBean) {
                holder.setText(R.id.item_my_reply_title,myPostBean.getSubject());
                holder.setText(R.id.item_my_reply_forum,myPostBean.getForumname());
                String dbdateline= myPostBean.getDbdateline();
                if(!TextUtils.isEmpty(dbdateline)){
                    holder.setText(R.id.item_my_reply_time, DataUtils.conversionTime(dbdateline));
                }else{
                    holder.setText(R.id.item_my_reply_time,"");
                }
            }
        };
    }


    @Override
    protected PullRefreshPresenter<MyPostBean> createPresenter() {
        return new PullRefreshPresenter<MyPostBean>() {
            @Override
            public void requestDatas() {
                FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_MYREPLY);
                params.addQueryStringParameter("page", String.valueOf(page));
                params.addQueryStringParameter("perpage",String.valueOf(perpage));
                XutilsHttp.Get(params, new ListDataHandlerCallback(ListDataCallback.LIST_KEY_DATA_THREADS, MyPostBean.class));
            }
        };
    }


}
