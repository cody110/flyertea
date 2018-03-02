package com.ideal.flyerteacafes.ui.fragment.page.tab.reportlist;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.DiscountAdapter;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.model.entity.ArticleTabBean;
import com.ideal.flyerteacafes.ui.activity.presenter.ArticlePresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.threads.DiscountArticleContentActivity;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fly on 2017/9/28.
 * 优惠列表
 */

public class DiscountListFragment extends ReportListFragment {

    @Override
    protected CommonAdapter<ArticleTabBean> createAdapter(List<ArticleTabBean> datas) {
        return new DiscountAdapter(mActivity, datas, R.layout.discount_item);
    }

    @Override
    public AdapterView.OnItemClickListener createItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArticleTabBean bean = adapter.getItem(i);
                Bundle bundle = new Bundle();
                bundle.putString(ArticlePresenter.BUNDLE_AID, String.valueOf(bean.getAid()));
                bundle.putString(ThreadPresenter.BUNDLE_TITLE, getDetailsTitle());
                jumpActivity(DiscountArticleContentActivity.class, bundle);

                Map<String, String> map = new HashMap<>();
                map.put("aid", String.valueOf(bean.getAid()));
                MobclickAgent.onEvent(getActivity(), FinalUtils.EventId.coupon_list_click, map);
            }
        };
    }

}
