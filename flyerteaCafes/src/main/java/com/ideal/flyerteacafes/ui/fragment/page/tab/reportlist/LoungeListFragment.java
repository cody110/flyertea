package com.ideal.flyerteacafes.ui.fragment.page.tab.reportlist;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.LoungeAdapter;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.model.entity.ArticleTabBean;

import java.util.List;

/**
 * Created by fly on 2016/6/2.
 */
public class LoungeListFragment extends ReportListFragment {

    @Override
    protected CommonAdapter<ArticleTabBean> createAdapter(List<ArticleTabBean> datas) {
        return new LoungeAdapter(mActivity, datas, R.layout.item_hotel_report);
    }
}
