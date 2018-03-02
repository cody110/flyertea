package com.ideal.flyerteacafes.adapters;

import android.content.Context;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.ArticleTabBean;
import com.ideal.flyerteacafes.model.entity.FlyHuiModel.DisList;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.DataUtils;

import java.util.List;

public class FlyHuiNopicAdapter extends FlyHuiSutraAdapter {

    public FlyHuiNopicAdapter(Context context, List<ArticleTabBean> datas, int layoutId) {
        super(context, datas, layoutId);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void convert(ViewHolder holder, ArticleTabBean t) {
        // TODO Auto-generated method stub
//        setRightTuibiao(holder, t, R.id.flyhui_nopic_top);
//        holder.setText(R.id.flyhui_nopic_title, t.getTitle());
//        holder.setText(R.id.flyhui_nopic_body, t.getInfo());
//        holder.setText(R.id.listview_flyhui_type, t.getCate_2_name());
//        holder.setText(R.id.listview_flyhui_preferential_type, t.getShangjianame());
//        String time = DataUtils.conversionTime(DataTools.getLong(t.getDateline()));
//        holder.setText(R.id.listview_flyhui_time, time);
//        holder.setText(R.id.listview_flyhui_read,t.getView());
    }

}
