package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.text.TextUtils;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.ArticleTabBean;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;

import java.util.List;

/**
 * Created by fly on 2017/8/28.
 */

public class HotelAdapter extends CommonAdapter<ArticleTabBean> {
    public HotelAdapter(Context context, List<ArticleTabBean> datasAll, int layoutId) {
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
        holder.setText(R.id.commment_views, articleTabBean.getComment_qty());
        holder.setText(R.id.hotel_group, "");
        holder.setText(R.id.hotel_city, "");
        if (!DataUtils.isEmpty(articleTabBean.getSortoptions())) {
            for (ArticleTabBean.SortoptionsBean bean : articleTabBean.getSortoptions()) {
                if (TextUtils.equals(bean.getIdentifier(), "hotel_brand")) {
                    holder.setText(R.id.hotel_group, bean.getSorttitle());
                }
                if (TextUtils.equals(bean.getIdentifier(), "hotel_city")) {
                    holder.setText(R.id.hotel_city, bean.getSorttitle());
                }

            }
        }

        /**
         * 底部数据都为空
         */
        if (TextUtils.isEmpty(holder.getText(R.id.commment_views)) && TextUtils.isEmpty(holder.getText(R.id.hotel_group)) && TextUtils.isEmpty(holder.getText(R.id.hotel_city))) {
            holder.setVisible(R.id.item_post_bottom, false);
        } else {
            holder.setVisible(R.id.item_post_bottom, true);
        }

    }
}
