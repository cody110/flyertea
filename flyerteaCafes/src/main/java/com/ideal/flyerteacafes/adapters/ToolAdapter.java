package com.ideal.flyerteacafes.adapters;

import android.content.Context;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.loca.ToolBean;

import java.util.List;

/**
 * Created by fly on 2016/6/6.
 */
public class ToolAdapter extends CommonAdapter<ToolBean> {
    public ToolAdapter(Context context, List<ToolBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, ToolBean toolBean) {
        holder.setBackgroundRes(R.id.item_tool_root,toolBean.getBackground());
        holder.setImageResource(R.id.item_tool_icon,toolBean.getIcon());
        holder.setImageResource(R.id.item_tool_arrow,toolBean.getArrow());
        holder.setText(R.id.item_tool_title,toolBean.getTitle());
        holder.setText(R.id.item_tool_title_name,toolBean.getTitle());
        holder.setText(R.id.item_tool_subject,toolBean.getSubject());
        holder.setText(R.id.item_tool_description,toolBean.getDescription());
    }
}
