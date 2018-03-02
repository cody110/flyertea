package com.ideal.flyerteacafes.adapters;

import android.content.Context;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.CreditsBean;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;

import java.util.List;

/**
 * Created by fly on 2017/5/26.
 */

public class WeiWangAdapter extends CommonAdapter<CreditsBean> {

    public WeiWangAdapter(Context context, List<CreditsBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, CreditsBean creditsBean) {
        holder.setText(R.id.title, creditsBean.getOpname());
        holder.setVisible(R.id.thread_id, DataTools.getInteger(creditsBean.getRelatedid()) != 0);
        holder.setText(R.id.thread_id, "(帖子id:" + creditsBean.getRelatedid() + ")");
        holder.setText(R.id.time, DataUtils.getTimeFormatToBiaozhun(creditsBean.getDateline()));
        holder.setText(R.id.number, "+" + creditsBean.getCredit());
    }
}
