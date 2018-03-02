package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.RemindBean;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.xutils.XutilsHelp;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

public class RemindAdapter extends BaseAdapter {

    private Context context;
    private List<RemindBean> remindList;

    public RemindAdapter(Context context, List<RemindBean> remindList) {
        this.context = context;
        this.remindList = remindList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return remindList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return remindList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item_remind_fragment_layout, null);
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        RemindBean bean = (RemindBean) getItem(position);

        if (bean.getNote() != null) {
            String note = DataUtils.Html2Text(bean.getNote());
            holder.bodyText.setText(note);
        }
        holder.timeText.setText(DataUtils.getTimeFormat(bean.getDateline()));

        if (!DataUtils.isEmpty(bean.getType())) {
            if (bean.getType().equals("system")) {
                holder.typeText.setText("系统");
                holder.iconImg.setImageResource(R.drawable.login_logo);
            } else {
                holder.typeText.setText(bean.getAuthor());
                DataUtils.downloadPicture(holder.iconImg, bean.getFace(), R.drawable.def_face);
            }
        }

        if (bean.getIsread().equals("0"))//1为未读，0为已读
            holder.isReadImg.setVisibility(View.GONE);
        else
            holder.isReadImg.setVisibility(View.VISIBLE);

        return convertView;
    }

    ViewHolder holder = null;

    private class ViewHolder {
        @ViewInject(R.id.listview_item_remind_fragment_isread)
        ImageView isReadImg;
        @ViewInject(R.id.listview_item_remind_fragment_icon)
        ImageView iconImg;
        @ViewInject(R.id.listview_item_remind_type)
        TextView typeText;
        @ViewInject(R.id.listview_item_remind_fragment_body)
        TextView bodyText;
        @ViewInject(R.id.listview_item_remind_fragment_time)
        TextView timeText;
    }

}
