package com.ideal.flyerteacafes.adapters.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by fly on 2015/11/18.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    protected View itemView;
    public ViewHolder holder;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        holder = new ViewHolder(itemView, itemView.getContext());
    }
}
