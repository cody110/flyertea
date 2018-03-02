package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.RaidersRootBean;
import com.ideal.flyerteacafes.model.entity.TypeBaseBean;
import com.ideal.flyerteacafes.utils.tools.ViewTools;

import java.util.List;

/**
 * Created by fly on 2017/8/30.
 */

public class RaidersGroupAdapter extends CommonAdapter<RaidersRootBean.CatsBean> {


    public RaidersGroupAdapter(Context context, List<RaidersRootBean.CatsBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, RaidersRootBean.CatsBean typeInfo) {
        holder.setImageUrl(R.id.icon, typeInfo.getIcon(), R.drawable.icon_def);
        holder.setText(R.id.name, typeInfo.getName());
    }
}
