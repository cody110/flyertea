package com.ideal.flyerteacafes.ui.wheelview.adapters;

import android.content.Context;

import com.ideal.flyerteacafes.model.entity.GroupmembershipsBean;
import com.ideal.flyerteacafes.ui.wheelview.adapters.base.ListWheelAdapter;

import java.util.List;

/**
 * Created by fly on 2017/5/24.
 */

public class GroupTypeAdapter extends ListWheelAdapter<GroupmembershipsBean.Type> {
    /**
     * Constructor
     *
     * @param context the current context
     * @param items   the items
     */
    public GroupTypeAdapter(Context context, List<GroupmembershipsBean.Type> items) {
        super(context, items);
    }

    @Override
    public CharSequence getItemText(int index) {
        return items.get(index).getName();
    }
}
