package com.ideal.flyerteacafes.adapters;

import android.content.Context;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.RaidersRootBean;

import java.util.List;

/**
 * Created by fly on 2017/8/30.
 */

public class RaidersGroupAdapter2 extends CommonAdapter<RaidersRootBean.CatsBean> {


    public RaidersGroupAdapter2(Context context, List<RaidersRootBean.CatsBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, RaidersRootBean.CatsBean typeInfo) {

        holder.setText(R.id.name, typeInfo.getName());
    }
}
