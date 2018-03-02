package com.ideal.flyerteacafes.adapters;

import android.content.Context;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.ExerciseBean;
import com.ideal.flyerteacafes.utils.DataUtils;

import java.util.List;

/**
 * Created by fly on 2016/6/6.
 */
public class ExerciseAdapter extends CommonAdapter<ExerciseBean> {
    public ExerciseAdapter(Context context, List<ExerciseBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, ExerciseBean exerciseBean) {
        if(exerciseBean.getCover().indexOf("http")==-1){
            exerciseBean.setCover("http://ptf.flyert.com/"+exerciseBean.getCover());
        }
        holder.setImageUrl(R.id.listview_information_article_da_logo, exerciseBean.getCover(),  R.drawable.post_def);
        holder.setText(R.id.listview_information_article_da_title,exerciseBean.getSubject());
        holder.setText(R.id.listview_information_article_da_type, DataUtils.getTimeFormat(exerciseBean.getStarttime(),"yyyy/MM/dd")+" - "+ DataUtils.getTimeFormat(exerciseBean.getEndtime(),"yyyy/MM/dd"));
        holder.setText(R.id.listview_information_article_da_dateline,exerciseBean.getViews());
    }
}
