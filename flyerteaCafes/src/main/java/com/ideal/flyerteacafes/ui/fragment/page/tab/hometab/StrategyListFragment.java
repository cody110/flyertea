package com.ideal.flyerteacafes.ui.fragment.page.tab.hometab;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.StrategyAdapter;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.model.entity.ArticleTabBean;
import com.ideal.flyerteacafes.ui.fragment.page.tab.reportlist.ReportListFragment;

import java.util.List;

/**
 * Created by fly on 2016/6/3.
 */
public class StrategyListFragment extends ReportListFragment {

    @Override
    protected CommonAdapter<ArticleTabBean> createAdapter(List<ArticleTabBean> datas) {
        return new StrategyAdapter(mActivity, datas, R.layout.listview_flydaily_item_layout);
    }

}