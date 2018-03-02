package com.ideal.flyerteacafes.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.RaidersBean;
import com.ideal.flyerteacafes.utils.DataUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

public class RaidersAdapter extends BaseAdapter {

    private Context context;
    private List<RaidersBean> raidersBeans;

    public RaidersAdapter(Context context, List<RaidersBean> raidersBeans) {
        this.context = context;
        this.raidersBeans = raidersBeans;
    }

    @Override
    public int getCount() {
        return raidersBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return raidersBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gride_raider, null);
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DataUtils.downloadPicture( holder.raider_logo, "http://ptf.flyert.com/" + raidersBeans.get(position).getPic(),R.drawable.post_def);
        holder.des.setText(raidersBeans.get(position).getTitle());
        holder.author.setText(raidersBeans.get(position).getAuthor());
//		holder.author.setText(DataUtils.getTimeFormat(raidersBeans.get(position).getDateline(), "yyyy-MM-dd HH:mm"));
        return convertView;
    }

    ViewHolder holder = null;

    private class ViewHolder {
        @ViewInject(R.id.item_gride_raider)
        ImageView raider_logo;
        @ViewInject(R.id.item_raider_des)
        TextView des;
        @ViewInject(R.id.item_raider_author)
        TextView author;
    }

}
