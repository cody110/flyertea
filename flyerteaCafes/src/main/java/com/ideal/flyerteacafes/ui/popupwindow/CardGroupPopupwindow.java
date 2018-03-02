package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.CardGroupBean;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by fly on 2017/4/13.
 */

public class CardGroupPopupwindow extends TopPopupwindow {

    public interface IItemClick {

        void itemClick(CardGroupBean bean);
    }

    IItemClick iItemClick;

    public void setiItemClick(IItemClick iItemClick) {
        this.iItemClick = iItemClick;
    }


    List<CardGroupBean> mDatas;

    @ViewInject(R.id.card_group_listview)
    ListView listView;

    public CardGroupPopupwindow(Context context) {
        super(context);
    }

    @Override
    protected int setTopViewLayoutId() {
        return R.layout.pop_card_group;
    }

    public void bindData(List<CardGroupBean> datas) {
        mDatas = datas;
        CommonAdapter<CardGroupBean> adapter = new CommonAdapter<CardGroupBean>(mContext, datas, R.layout.item_taskname) {
            @Override
            public void convert(ViewHolder holder, CardGroupBean cardGroupBean) {
                holder.setText(R.id.taskname_tv, cardGroupBean.getCardChannelName());
            }
        };
        listView.setAdapter(adapter);
    }

    @Event(value = R.id.card_group_listview, type = AdapterView.OnItemClickListener.class)
    private void itemClick(AdapterView<?> parent, View view, int position, long id) {
        if (iItemClick != null) {
            iItemClick.itemClick(mDatas.get(position));
        }
        showEndAnimation();
    }


}
