package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.ArticleTabBean;
import com.ideal.flyerteacafes.model.loca.SegmentedStringMode;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2017/8/28.
 */

public class DiscountAdapter extends CommonAdapter<ArticleTabBean> {
    public DiscountAdapter(Context context, List<ArticleTabBean> datasAll, int layoutId) {
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
        holder.setTextHtml(R.id.item_post_subject, articleTabBean.getTitle());
        holder.setTextHtml(R.id.discount_time, articleTabBean.getDateline());

        holder.setText(R.id.discount_hotel_group, "");
        if (!DataUtils.isEmpty(articleTabBean.getSortoptions())) {
            for (ArticleTabBean.SortoptionsBean bean : articleTabBean.getSortoptions()) {
                if (TextUtils.equals(bean.getIdentifier(), "youhui_type")) {
                    holder.setText(R.id.discount_hotel_group, bean.getSorttitle());
                }
            }
        }


        long nowTime = DateUtil.getDateline() / 1000;
        long endTime = DataTools.getInteger(articleTabBean.getEnddateline());
        long time = endTime - nowTime;


        long days = time / (60 * 60 * 24);
        if (time % (60 * 60 * 24) > 0) {
            days += 1;
        }

        holder.setVisible(R.id.daojishi, time > 0);
        List<SegmentedStringMode> modeList = new ArrayList<>();
        SegmentedStringMode mode1 = new SegmentedStringMode("倒计时：", R.dimen.zb_list_comment_size, R.color.content, null);
        modeList.add(mode1);
        SegmentedStringMode mode2 = new SegmentedStringMode(String.valueOf(days), R.dimen.zb_list_comment_size, R.color.daojishi, null);
        modeList.add(mode2);
        SegmentedStringMode mode3 = new SegmentedStringMode("天", R.dimen.zb_list_comment_size, R.color.content, null);
        modeList.add(mode3);
        holder.setText(R.id.daojishi, DataUtils.getSegmentedDisplaySs(modeList));


    }
}
