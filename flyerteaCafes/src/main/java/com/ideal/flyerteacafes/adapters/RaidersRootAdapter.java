package com.ideal.flyerteacafes.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.RaidersBean;
import com.ideal.flyerteacafes.model.entity.RaidersRootBean;
import com.ideal.flyerteacafes.utils.DataUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

public class RaidersRootAdapter extends CommonAdapter<RaidersRootBean.ArticlesBean> {


    public RaidersRootAdapter(Context context, List<RaidersRootBean.ArticlesBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, RaidersRootBean.ArticlesBean articlesBean) {
        holder.setImageUrl(R.id.item_gride_raider, articlesBean.getAttachments()[0], R.drawable.post_def);
        holder.setTextHtml(R.id.item_raider_des, articlesBean.getSubject());
    }
}
