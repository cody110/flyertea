package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.text.TextUtils;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.ArticleTabBean;
import com.ideal.flyerteacafes.utils.DataUtils;

import java.util.List;

/**
 * Created by fly on 2017/8/28.
 */

public class FlyerAdapter extends CommonAdapter<ArticleTabBean> {
    public FlyerAdapter(Context context, List<ArticleTabBean> datasAll, int layoutId) {
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
        holder.setText(R.id.flyer_hangsi, "");
        holder.setText(R.id.flyer_changwei, "");
        holder.setText(R.id.flyer_jixing, "");
        if (!DataUtils.isEmpty(articleTabBean.getSortoptions())) {
            for (ArticleTabBean.SortoptionsBean bean : articleTabBean.getSortoptions()) {
                if (TextUtils.equals(bean.getIdentifier(), "flight_airline")) {
                    holder.setText(R.id.flyer_hangsi, bean.getSorttitle());
                }
                if (TextUtils.equals(bean.getIdentifier(), "flight_class")) {
                    holder.setText(R.id.flyer_changwei, bean.getSorttitle());
                }
                if (TextUtils.equals(bean.getIdentifier(), "flight_model")) {
                    holder.setText(R.id.flyer_jixing, bean.getSorttitle());
                }

            }
        }

    }
}
