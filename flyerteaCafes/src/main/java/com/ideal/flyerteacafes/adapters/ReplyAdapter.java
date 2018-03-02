package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.text.TextUtils;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.NotificationBean;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;

import java.util.List;

public class ReplyAdapter extends CommonAdapter<NotificationBean> {

    public ReplyAdapter(Context context, List<NotificationBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, NotificationBean notificationBean) {
        holder.setTextHtml(R.id.item_my_reply_title, notificationBean.getNote());
        String dbdateline = notificationBean.getDateline();
        if (!TextUtils.isEmpty(dbdateline)) {
            holder.setText(R.id.item_my_reply_time, DataUtils.conversionTime(dbdateline));
        } else {
            holder.setText(R.id.item_my_reply_time, "");
        }
        holder.setVisible(R.id.icon_remind, TextUtils.equals(notificationBean.getIsnew(), "1"));
    }
}
