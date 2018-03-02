package com.ideal.flyerteacafes.adapters;


import android.content.Context;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.ArticleTabBean;

import java.util.List;

/**
 * Created by fly on 2016/1/26.
 */
public class ReportAdapter extends CommonAdapter<ArticleTabBean> {
    public ReportAdapter(Context context, List<ArticleTabBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, ArticleTabBean articleTabBean) {
        if(articleTabBean.getPic().indexOf("http")==-1){
            articleTabBean.setPic("http://ptf.flyert.com/"+articleTabBean.getPic());
        }
        holder.setImageUrl(R.id.listview_information_article_da_logo,articleTabBean.getPic(),R.drawable.post_def);
        holder.setText(R.id.listview_information_article_da_title, articleTabBean.getTitle());
        holder.setText(R.id.listview_information_article_da_type, articleTabBean.getCat_name());
        holder.setText(R.id.listview_information_article_da_dateline,articleTabBean.getViews());
    }
}
