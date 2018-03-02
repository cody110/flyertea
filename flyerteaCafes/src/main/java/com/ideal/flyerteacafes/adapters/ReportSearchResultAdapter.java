package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.SearchReportBean;
import com.ideal.flyerteacafes.model.entity.SearchThreadBean;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;

import java.util.List;

/**
 * Created by fly on 2018/1/21.
 */

public class ReportSearchResultAdapter extends CommonAdapter<SearchReportBean> {
    private String searchKey;

    public ReportSearchResultAdapter(Context context, List<SearchReportBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, SearchReportBean searchThreadBean) {
        WidgetUtils.setHighLightKey((TextView) holder.getView(R.id.tv_subject), searchThreadBean.getSubject(), searchKey);
        holder.setText(R.id.tv_forum_name, searchThreadBean.getForumname());
        holder.setText(R.id.tv_time, DataUtils.conversionTime(searchThreadBean.getDbdateline()));
        holder.setText(R.id.tv_comment_num, searchThreadBean.getReplies());
//        holder.setText(R.id.tv_flower_num, searchThreadBean.getFlowers());
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
        notifyDataSetChanged();
    }

}
