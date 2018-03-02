package com.ideal.flyerteacafes.adapters;

import android.content.Context;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.TypeAll;
import com.ideal.flyerteacafes.model.entity.TypeBaseBean;

import java.util.List;

public class FlyHuiTypeAapter extends CommonAdapter<TypeBaseBean.TypeInfo> {

    public static final int LEVEL_ONE = 1, LEVEL_TWO = 2;
    private int level;

    public FlyHuiTypeAapter(Context context, List<TypeBaseBean.TypeInfo> datas, int layoutId, int level) {
        super(context, datas, layoutId);
        this.level = level;
        if (level == LEVEL_ONE) {
            index = 0;
        } else {
            index = -1;
        }
    }

    @Override
    protected boolean isNeedMyList() {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, TypeBaseBean.TypeInfo t) {
        holder.setText(R.id.typeall_one_level_text, t.getName());
        if (mDatas.indexOf(t) == index) {
            holder.setBackgroundColor(R.id.typeall_one_level_root, mContext.getResources().getColor(R.color.white));
            if (level == LEVEL_ONE) {
                holder.setBackgroundColor(R.id.typeall_one_level_select_mark, mContext.getResources().getColor(R.color.app_bg_title));
            }
            holder.setTextColor(R.id.typeall_one_level_text, mContext.getResources().getColor(R.color.app_bg_title));
        } else {
            holder.setBackgroundColor(R.id.typeall_one_level_root, mContext.getResources().getColor(R.color.app_bg_grey));
            if (level == LEVEL_ONE) {
                holder.setBackgroundColor(R.id.typeall_one_level_select_mark, mContext.getResources().getColor(R.color.app_bg_grey));
            }
            holder.setTextColor(R.id.typeall_one_level_text, mContext.getResources().getColor(R.color.app_body_black));
        }
        if (level == LEVEL_TWO) {
            holder.setBackgroundColor(R.id.typeall_one_level_root, mContext.getResources().getColor(R.color.white));
        }
    }

    private int index = -1;

    public void selectItem(int index) {
        this.index = index;
        notifyDataSetChanged();
    }

}
