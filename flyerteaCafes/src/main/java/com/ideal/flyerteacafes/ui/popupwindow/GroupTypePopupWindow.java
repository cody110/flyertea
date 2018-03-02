package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;

import com.ideal.flyerteacafes.model.entity.GroupmembershipsBean;
import com.ideal.flyerteacafes.ui.wheelview.adapters.GroupTypeAdapter;

import java.util.List;

/**
 * Created by fly on 2017/5/24.
 */

public class GroupTypePopupWindow extends CommonBottomPopupwindow<GroupmembershipsBean.Type> {


    public GroupTypePopupWindow(Context context, List<GroupmembershipsBean.Type> datas, ISureOK<GroupmembershipsBean.Type> iSureOK) {
        super(context, datas, iSureOK);
    }

    protected void bindDatas(List<GroupmembershipsBean.Type> datas) {
        GroupTypeAdapter adapter = new GroupTypeAdapter(mContext, datas);
        wheelView.setViewAdapter(adapter);
    }


}
