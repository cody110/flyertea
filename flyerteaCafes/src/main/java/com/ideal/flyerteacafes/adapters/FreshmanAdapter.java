package com.ideal.flyerteacafes.adapters;

import android.content.Context;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.ArticleBean;

import java.util.List;

/**
 * Created by fly on 2016/6/8.
 */
public class FreshmanAdapter extends CommonAdapter<ArticleBean> {
    public FreshmanAdapter(Context context, List<ArticleBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, ArticleBean bean) {
        if(bean.getLogo().indexOf("http")==-1){
            bean.setLogo("http://ptf.flyert.com/" + bean.getLogo());
        }
        holder.setImageUrl(R.id.listview_information_article_logo,bean.getLogo(), R.drawable.icon_def);
        holder.setText(R.id.listview_information_article_title, bean.getTitle());
        holder.setText(R.id.listview_information_article_type,bean.getCat_name());
        holder.setText(R.id.listview_information_article_dateline,bean.getViews());
    }
}
