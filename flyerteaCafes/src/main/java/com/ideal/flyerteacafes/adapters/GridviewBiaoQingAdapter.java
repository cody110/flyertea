package com.ideal.flyerteacafes.adapters;

import java.util.List;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.SmileyBean;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GridviewBiaoQingAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<SmileyBean> smileyList;

    public GridviewBiaoQingAdapter(Context context, List<SmileyBean> smileyList) {
        inflater = LayoutInflater.from(context);
        this.smileyList = smileyList;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return smileyList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return smileyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        SmileyBean bean = (SmileyBean) getItem(position);
        if (convertView == null) {
            ImageView img = new ImageView(context);
            if (bean.getIid() == 0)
                img.setImageResource(R.drawable._0);
            else
                img.setImageResource(bean.getIid());
            img.setVisibility(TextUtils.isEmpty(bean.getImage()) ? View.INVISIBLE : View.VISIBLE);
            convertView = img;
        }
        return convertView;
    }

}
