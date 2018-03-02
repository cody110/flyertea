package com.ideal.flyerteacafes.adapters;

import android.content.Context;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.ArticleTabBean;

import java.util.List;

/**
 * Created by fly on 2016/6/3.
 */
public class StrategyAdapter extends CommonAdapter<ArticleTabBean> {
    public StrategyAdapter(Context context, List<ArticleTabBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, ArticleTabBean bean) {

        if(bean.getPic().indexOf("http")==-1){
            bean.setPic("http://ptf.flyert.com/"+bean.getPic());
        }
        holder.setImageUrl(R.id.listview_information_article_logo,bean.getPic(), R.drawable.icon_def);
        holder.setText(R.id.listview_information_article_title, bean.getTitle());
        holder.setText(R.id.listview_information_article_type,bean.getCat_name());
        holder.setText(R.id.listview_information_article_dateline,bean.getViews());
    }
}
