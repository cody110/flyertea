package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.text.TextUtils;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.ThreadSubjectBean;
import com.ideal.flyerteacafes.utils.DataUtils;

import java.util.List;

/**
 * Created by fly on 2016/6/3.
 */
public class InterlocutionAdapter extends CommonAdapter<ThreadSubjectBean> {
    public InterlocutionAdapter(Context context, List<ThreadSubjectBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, ThreadSubjectBean bean) {
        holder.setText(R.id.item_interlocution_answer, bean.getReplies() + "回答");
        holder.setText(R.id.item_interlocution_content, bean.getSubject());
        holder.setText(R.id.item_interlocution_groupname, bean.getForumname());
        holder.setTextHtml(R.id.item_interlocution_time, DataUtils.conversionTime(bean.getDbdateline()));
        if (TextUtils.equals(bean.getReplies(), "0")) {
            holder.setTextColorRes(R.id.item_interlocution_answer, R.color.app_bg_title);
        } else {
            holder.setTextColorRes(R.id.item_interlocution_answer, R.color.app_body_grey);
        }

        if(TextUtils.isEmpty(bean.getPrice())||TextUtils.equals(bean.getPrice(),"0")){
            holder.setVisible(R.id.item_interlocution_weiwang,false);
        }else{
            holder.setVisible(R.id.item_interlocution_weiwang,true);
            holder.setText(R.id.item_interlocution_weiwang,bean.getPrice()+"威望·");
        }

    }
}
