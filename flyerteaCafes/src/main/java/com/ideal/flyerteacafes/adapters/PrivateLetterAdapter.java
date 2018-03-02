package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.text.TextUtils;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.model.entity.PersonalLetterBean;
import com.ideal.flyerteacafes.utils.DataUtils;

import java.util.List;

public class PrivateLetterAdapter extends CommonAdapter<PersonalLetterBean> {


    public PrivateLetterAdapter(Context context, List<PersonalLetterBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(com.ideal.flyerteacafes.adapters.base.ViewHolder holder, PersonalLetterBean personalLetterBean) {

        if (holder.getPosition() == 0 && TextUtils.equals(personalLetterBean.getMsgfrom(), "系统消息")) {
            holder.setImageResource(R.id.listview_item_remind_fragment_icon, R.drawable.system_face);
        } else {
            holder.setImageUrl(R.id.listview_item_remind_fragment_icon, personalLetterBean.getFace(), R.drawable.def_face_2);
        }
        holder.setText(R.id.listview_item_remind_type, personalLetterBean.getMsgfrom());
        holder.setTextHtml(R.id.listview_item_remind_fragment_body, personalLetterBean.getSubject());
        holder.setText(R.id.listview_item_remind_fragment_time, DataUtils.conversionTime(personalLetterBean.getDateline()));
        holder.setVisible(R.id.listview_item_remind_fragment_isread, TextUtils.equals(personalLetterBean.getIsnew(), "1"));
    }


}
