package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.adapters.interfaces.IGridViewItemClick;
import com.ideal.flyerteacafes.adapters.videoitem.ThreadVideoItem;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;

import java.util.List;

/**
 * Created by fly on 2016/12/22.
 */

public class ThreadSubjectVideoListAdapter extends CommonAdapter<ThreadVideoItem> {

    int imgLayoutWidth;


    public ThreadSubjectVideoListAdapter(Context context, List<ThreadVideoItem> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
        int w_screen = SharedPreferencesString.getInstances().getIntToKey("w_screen");
        imgLayoutWidth = w_screen - context.getResources().getDimensionPixelOffset(R.dimen.app_commen_margin) * 2;
    }


    //显示板块分类=true
    private boolean isShowType = true;

    public void actionShowAdapterType(boolean bol) {
        isShowType = bol;
    }

    //显示二级板块名称=true
    private boolean isShowFroumNmae = false;

    public ThreadSubjectVideoListAdapter setIsShowFroumName(boolean is) {
        this.isShowFroumNmae = is;
        return this;
    }

    //显示火帖标识=true 只在我的帖子列表，跟他人的帖子列表展示火帖标识
    private boolean isShowHeat = false;

    public ThreadSubjectVideoListAdapter setIsShowHeat(boolean is) {
        this.isShowHeat = is;
        return this;
    }

    private IGridViewItemClick iGridViewItemClick;

    public ThreadSubjectVideoListAdapter setiGridViewItemClick(IGridViewItemClick iGridViewItemClick) {
        this.iGridViewItemClick = iGridViewItemClick;
        return this;
    }

    private boolean is_feed;

    public ThreadSubjectVideoListAdapter setIs_feed(boolean is_feed) {
        this.is_feed = is_feed;
        return this;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);

        ThreadVideoItem threadVideoItem = mDatas.get(position);
        threadVideoItem.bindListenerItem(holder, iGridViewItemClick,getItem(position));

        convert(holder, getItem(position));
        return holder.getConvertView();
    }


    @Override
    public void convert(final ViewHolder holder, final ThreadVideoItem threadSubjectBean) {
        ThreadVideoItem threadVideoItem = mDatas.get(holder.getPosition());
        threadVideoItem.bindItem(holder, isShowType, isShowFroumNmae, is_feed, isShowHeat);
    }


}
