package com.ideal.flyerteacafes.ui.wheelview.adapters;

import android.content.Context;

import com.ideal.flyerteacafes.model.entity.CitysBean;
import com.ideal.flyerteacafes.ui.wheelview.adapters.base.ListWheelAdapter;

import java.util.List;

/**
 * Created by fly on 2016/4/1.
 */
public class CityListAdapter extends ListWheelAdapter<CitysBean>{
    /**
     * Constructor
     *
     * @param context the current context
     * @param items   the items
     */
    public CityListAdapter(Context context, List<CitysBean> items) {
        super(context, items);
    }

    @Override
    public CharSequence getItemText(int index) {
        return items.get(index).getCity();
    }
}
