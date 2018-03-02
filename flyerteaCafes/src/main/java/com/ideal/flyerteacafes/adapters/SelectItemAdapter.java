package com.ideal.flyerteacafes.adapters;

import android.content.Context;

import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;

import java.util.List;

/**
 * Created by fly on 2016/11/30.
 * 单项选择item的适配器
 */

public abstract class SelectItemAdapter<T> extends CommonAdapter<T> {
    public SelectItemAdapter(Context context, List<T> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    protected int selectIndex = -1;

    public void setSelectIndex(int index) {
        selectIndex = index;
        notifyDataSetChanged();
    }

    public int getSelectIndex() {
        return selectIndex;
    }

    public T getSelectBean() {
        return DataTools.getBeanByListPos(mDatas, selectIndex);
    }
}
