package com.ideal.flyerteacafes.ui.wheelview.adapters.base;

import android.content.Context;

import java.util.List;

/**
 * Created by fly on 2016/4/1.
 */
public class ListWheelAdapter<T> extends AbstractWheelTextAdapter {

    protected List<T> items;

    /**
     * Constructor
     * @param context the current context
     * @param items the items
     */
    public ListWheelAdapter(Context context, List<T> items) {
        super(context);

        //setEmptyItemResource(TEXT_VIEW_ITEM_RESOURCE);
        this.items = items;
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < items.size()) {
            T item = items.get(index);
            if (item instanceof CharSequence) {
                return (CharSequence) item;
            }
            return item.toString();
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return items==null? 0:items.size();
    }

}
