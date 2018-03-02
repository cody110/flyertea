package com.ideal.flyerteacafes.ui.fragment.page;

import android.os.Bundle;
import android.text.TextUtils;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.ThreadSubjectVideoListAdapter;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.interfaces.IGridViewItemClick;
import com.ideal.flyerteacafes.adapters.videoitem.ThreadVideoItem;
import com.ideal.flyerteacafes.model.entity.ThreadSubjectBean;
import com.ideal.flyerteacafes.model.entity.TopicBean;
import com.ideal.flyerteacafes.ui.activity.video.AutoPlayListFragment;
import com.ideal.flyerteacafes.ui.activity.web.TbsWebActivity;
import com.ideal.flyerteacafes.ui.activity.TopicDetailsActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.ui.fragment.presenter.ThreadTagListPresenter;
import com.ideal.flyerteacafes.utils.Marks;
import com.ideal.flyerteacafes.utils.Utils;

import java.util.List;

/**
 * Created by fly on 2017/11/30.
 */

public class ThreadTagListFragment extends AutoPlayListFragment {

    @Override
    public void initViews() {
        super.initViews();
        listView.setDivider(getResources().getDrawable(R.drawable.line_margin_left_right));
    }

    @Override
    protected CommonAdapter<ThreadVideoItem> createAdapter(final List<ThreadVideoItem> datas) {
        return new ThreadSubjectVideoListAdapter(mActivity, datas, R.layout.item_community_follow_list).setIsShowFroumName(true).setiGridViewItemClick(new IGridViewItemClick() {
            @Override
            public void gridViewItemClick(int i) {
                ThreadSubjectBean bean = adapter.getItem(i).getData();
                if (TextUtils.equals(bean.getType(), Marks.THREAD_LIST_TYPE_ADVER)) {

                    if (TextUtils.equals(bean.getApptemplatetype(), Marks.THREAD_LIST_TYPE_TOPIC)) {
                        Bundle bundle = new Bundle();
                        TopicBean topicBean = new TopicBean();
                        topicBean.setCtid(bean.getCtid());
                        topicBean.setName(bean.getName());
                        topicBean.setDesc(bean.getDesc());
                        topicBean.setViews(bean.getViews());
                        topicBean.setUsers(bean.getUsers());
                        bundle.putSerializable("data", topicBean);
                        jumpActivity(TopicDetailsActivity.class, bundle);
                    } else {
                        Bundle bundle = new Bundle();
                        if (TextUtils.equals(bean.getAdtype(), "code")) {
                            bundle.putString("url", Utils.HtmlUrl.HTML_ADV + bean.getTid());
                        } else {
                            bundle.putString("url", bean.getUrl());
                        }
                        jumpActivity(TbsWebActivity.class, bundle);
                    }
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(ThreadPresenter.BUNDLE_TID, bean.getTid());
                    if (bean.isNormal()) {
                        jumpActivity(ThreadActivity.class, bundle);
                    } else {
                        bundle.putInt(ThreadPresenter.BUNDLE_TYPE_KEY, ThreadPresenter.BUNDLE_TYPE_MAJOR);
                        jumpActivity(ThreadActivity.class, bundle);
                    }
                }
            }
        });
    }

    @Override
    protected PullRefreshPresenter<ThreadVideoItem> createPresenter() {
        return new ThreadTagListPresenter();
    }
}
