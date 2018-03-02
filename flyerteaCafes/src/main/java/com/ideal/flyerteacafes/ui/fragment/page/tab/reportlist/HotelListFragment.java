package com.ideal.flyerteacafes.ui.fragment.page.tab.reportlist;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.HotelAdapter;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.model.entity.ArticleTabBean;

import java.util.List;

/**
 * Created by fly on 2017/9/28.
 */

public class HotelListFragment extends ReportListFragment {

    @Override
    protected CommonAdapter<ArticleTabBean> createAdapter(List<ArticleTabBean> datas) {
        return new HotelAdapter(mActivity, datas, R.layout.item_hotel_report);
    }

}
