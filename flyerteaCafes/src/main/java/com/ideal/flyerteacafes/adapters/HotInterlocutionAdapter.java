package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.text.TextUtils;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.InterlocutionBean;

import java.util.List;

/**
 * Created by fly on 2016/6/6.
 */
public class HotInterlocutionAdapter extends CommonAdapter<InterlocutionBean> {
    public HotInterlocutionAdapter(Context context, List<InterlocutionBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, final InterlocutionBean interlocutionBean) {
        if (TextUtils.isEmpty(interlocutionBean.getAttach()) || TextUtils.equals(interlocutionBean.getAttach(), "0")) {
            holder.setVisible(R.id.item_hot_interlocution_img, false);
        } else {
            holder.setVisible(R.id.item_hot_interlocution_img, true);
            holder.setImageUrl(R.id.item_hot_interlocution_img, interlocutionBean.getAttach(), R.drawable.icon_def);
        }
        holder.setText(R.id.item_hot_interlocution_content, interlocutionBean.getSubject());
        holder.setText(R.id.item_hot_interlocution_read, interlocutionBean.getViews()+"人查看");
    }


    @Override
    public int getCount() {
        return mDatas.size() > 3 ? 3 : mDatas.size();
    }
}
