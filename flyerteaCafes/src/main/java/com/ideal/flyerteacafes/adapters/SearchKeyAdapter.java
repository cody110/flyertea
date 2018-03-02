package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.SearchHistoryBean;
import com.ideal.flyerteacafes.model.loca.SegmentedStringMode;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.WidgetUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2017/9/20.
 */

public class SearchKeyAdapter extends CommonAdapter<SearchHistoryBean> {

    private String searchKey;

    public SearchKeyAdapter(Context context, List<SearchHistoryBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, SearchHistoryBean searchHistoryBean) {
        holder.setVisible(R.id.list_search_history_name, true);
        holder.setVisible(R.id.list_search_history_cancel, true);
        if (TextUtils.equals(searchHistoryBean.getSearchName(), mContext.getResources().getString(R.string.search_clear_history))) {
            holder.setText(R.id.list_search_history_cancel, searchHistoryBean.getSearchName());
            holder.setVisible(R.id.list_search_history_name, false);
        } else {
            WidgetUtils.setHighLightKey((TextView) holder.getView(R.id.list_search_history_name), searchHistoryBean.getSearchName(), searchKey);
            holder.setVisible(R.id.list_search_history_cancel, false);
        }
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
        notifyDataSetChanged();
    }


}
