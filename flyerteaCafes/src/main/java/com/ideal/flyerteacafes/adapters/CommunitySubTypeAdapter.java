package com.ideal.flyerteacafes.adapters;

import android.content.Context;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.CommunitySubTypeBean;

import java.util.List;

/**
 * Created by fly on 2016/11/29.
 */

public class CommunitySubTypeAdapter extends SelectItemAdapter<CommunitySubTypeBean> {
    public CommunitySubTypeAdapter(Context context, List<CommunitySubTypeBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, CommunitySubTypeBean communitySubTypeBean) {
        holder.setText(R.id.item_gridview_community_type_name, communitySubTypeBean.getName());
        if (selectIndex == holder.getPosition()) {
            holder.setBackgroundRes(R.id.item_gridview_community_type_name, R.drawable.text_bottom_line_bg);
        } else {
            holder.setBackground(R.id.item_gridview_community_type_name, null);
        }
    }


}
