package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.ForumTypeBean;

import java.util.List;

public class ForumTypeAdapter extends BaseAdapter {

    @SuppressWarnings("unused")
    private LayoutInflater inflater;
    private List<ForumTypeBean> listBean;
    private int pos = 0;
    private Context context;

    public ForumTypeAdapter(Context context, List<ForumTypeBean> listBean, int pos) {
        this.listBean = listBean;
        this.context = context;
        this.pos = pos;
        inflater = LayoutInflater.from(context);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null)
            convertView = inflater.inflate(R.layout.listview_creditcard_choose_item, null);
        View layoutView = convertView.findViewById(R.id.creditcard_choose_item_layout);
        TextView name = (TextView) convertView.findViewById(R.id.creditcard_choose_item_name);
        ImageView img = (ImageView) convertView.findViewById(R.id.creditcard_choose_item_img);

        ForumTypeBean bean = (ForumTypeBean) getItem(position);

        name.setText(bean.getTypeName());

        if (pos == position) {
            name.setTextColor(context.getResources().getColor(R.color.app_bg_title));
            img.setVisibility(View.VISIBLE);
        } else {
            name.setTextColor(context.getResources().getColor(R.color.app_body_black));
            img.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

}
