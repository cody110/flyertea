package com.ideal.flyerteacafes.adapters;

import android.content.Context;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;

import java.util.List;

/**
 * Created by fly on 2015/11/26.
 */
public class JubaoAdapter extends SelectItemAdapter<String> {

    public JubaoAdapter(Context context, List<String> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, String s) {
        holder.setText(R.id.jubao_item_tv, s);
        if (selectIndex == holder.getPosition()) {
            holder.setImageResource(R.id.jubao_item_img, R.drawable.jubao_selected);
        } else {
            holder.setImageResource(R.id.jubao_item_img, R.drawable.jubao_unselected);
        }
    }


}
