package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.HotelBean;
import com.ideal.flyerteacafes.utils.DataUtils;

import java.util.List;

public class HotelReservationAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<HotelBean> listBean;

    public HotelReservationAdapter(Context context, List<HotelBean> listBean) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.listBean = listBean;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listBean.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null)
            convertView = inflater.inflate(R.layout.gridview_item_hotel_reservation, null);
        ImageView iconImg = (ImageView) convertView.findViewById(R.id.gridview_hotel_reservation_icon);
        HotelBean bean = (HotelBean) getItem(position);
        DataUtils.downloadPicture(iconImg, bean.getHotel_icon_url(), R.drawable.ic_launcher);
        TextView nameText = (TextView) convertView.findViewById(R.id.gridview_hotel_reservation_name);
        nameText.setText(bean.getHotel_name());
        return convertView;
    }

}
