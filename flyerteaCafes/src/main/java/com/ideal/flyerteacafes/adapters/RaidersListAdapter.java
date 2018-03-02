package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.text.TextUtils;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.ArticleTabBean;
import com.ideal.flyerteacafes.utils.DataUtils;

import java.util.List;

/**
 * Created by fly on 2017/9/5.
 */

public class RaidersListAdapter extends DiscountAdapter {

    public RaidersListAdapter(Context context, List<ArticleTabBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, ArticleTabBean articleTabBean) {
        if (!TextUtils.isEmpty(articleTabBean.getPic())) {
            if (!articleTabBean.getPic().contains("http")) {
                articleTabBean.setPic("http://ptf.flyert.com/" + articleTabBean.getPic());
            }
        }
        holder.setImageUrl(R.id.item_post_pic, articleTabBean.getPic(), R.drawable.post_def);
        holder.setText(R.id.item_post_subject, articleTabBean.getTitle());
        holder.setTextHtml(R.id.discount_time, articleTabBean.getDateline());
        holder.setText(R.id.discount_hotel_group, articleTabBean.getComment_qty()+"评论");


    }


}
