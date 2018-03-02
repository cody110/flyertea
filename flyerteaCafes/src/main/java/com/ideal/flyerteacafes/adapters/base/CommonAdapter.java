package com.ideal.flyerteacafes.adapters.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected int layoutId;

    public CommonAdapter(Context context, List<T> datasAll, int layoutId) {
        init(context, datasAll, layoutId);
    }

    private void init(Context context, List<T> datasAll, int layoutId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        if (isNeedMyList()) {
            this.mDatas = new ArrayList<>();
            this.mDatas.addAll(datasAll);
        } else {
            this.mDatas = datasAll;
        }
        this.layoutId = layoutId;
    }

    /**
     * 是否需要自己的list
     *
     * @return
     */
    protected boolean isNeedMyList() {
        return false;
    }

    /**
     * 刷新数据
     *
     * @param datas
     */
    public void refresh(List<T> datas) {
        this.mDatas.clear();
        if (datas != null && datas.size() != 0) {
            mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent,
                layoutId, position);
        convert(holder, getItem(position));
        return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder, T t);

}
