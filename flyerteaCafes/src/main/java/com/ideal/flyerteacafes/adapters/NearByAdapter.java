package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.NearBean.NearInfo;
import com.ideal.flyerteacafes.ui.controls.RoundImageView;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

public class NearByAdapter extends BaseAdapter {

    private Context context;
    private List<NearInfo> infoList;

    public NearByAdapter(Context context, List<NearInfo> infoList) {
        this.context = context;
        this.infoList = infoList;
    }

    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int position) {
        return infoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_nearby_friends, null);
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NearInfo info = (NearInfo) getItem(position);

        DataUtils.downloadPicture(holder.userHead, info.getFace(), R.drawable.def_face);
        holder.userName.setText(info.getUsername());
        holder.city.setText(info.getResidecity());
        if (!DataUtils.isEmpty(info.getRecentnote())) {
            holder.sign.setText(info.getRecentnote());
        } else {
            holder.sign.setText(context.getString(R.string.not_sightml));
        }

        long dataTime = Long.parseLong(info.getLocationtime());
        holder.time.setText(DataUtils.conversionTime(dataTime));

        switch (DataTools.getInteger(info.getGender())) {
            case 0:
                holder.sex.setVisibility(View.INVISIBLE);
                break;
            case 1:
                holder.sex.setImageResource(R.drawable.boy_flag);
                holder.sex.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder.sex.setImageResource(R.drawable.girl_flag);
                holder.sex.setVisibility(View.VISIBLE);
                break;

        }

        double dis = DataTools.getDouble(info.getDistance());
        if (dis < 1) {
            int distance = (int) (dis * 1000);
            holder.distance.setText(distance + "m");
        } else {
            holder.distance.setText(DataTools.getTwoDecimal(dis) + "km");
        }

        return convertView;
    }

    ViewHolder holder = null;

    private class ViewHolder {
        @ViewInject(R.id.nearby_head)
        RoundImageView userHead;
        @ViewInject(R.id.nearby_name)
        TextView userName;
        @ViewInject(R.id.nearby_friends_sex)
        ImageView sex;
        @ViewInject(R.id.nearby_friends_city)
        TextView city;
        @ViewInject(R.id.nearby_signature)
        TextView sign;
        @ViewInject(R.id.nearby_distance)
        TextView distance;
        @ViewInject(R.id.nearby_time)
        TextView time;
    }

}
