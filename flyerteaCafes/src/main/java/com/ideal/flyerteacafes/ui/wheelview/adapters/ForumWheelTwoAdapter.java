package com.ideal.flyerteacafes.ui.wheelview.adapters;

import android.content.Context;

import com.ideal.flyerteacafes.model.entity.CommunityBean;
import com.ideal.flyerteacafes.model.entity.CommunitySubBean;
import com.ideal.flyerteacafes.ui.wheelview.adapters.base.ListWheelAdapter;

import java.util.List;

/**
 * Created by fly on 2016/11/18.
 */

public class ForumWheelTwoAdapter extends ListWheelAdapter<CommunitySubBean> {
    /**
     * Constructor
     *
     * @param context the current context
     * @param items   the items
     */
    public ForumWheelTwoAdapter(Context context, List<CommunitySubBean> items) {
        super(context, items);
    }
    @Override
    public CharSequence getItemText(int index) {
        return items.get(index).getName();
    }
}
