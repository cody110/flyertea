package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.BankBean;

import java.util.List;

/**
 * Created by fly on 2017/4/14.
 */

public class BankAdapter extends SelectItemAdapter<BankBean> {
    public BankAdapter(Context context, List<BankBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, BankBean bankBean) {
        holder.setVisible(R.id.bank_index, isFirst(holder.getPosition()));

        holder.setText(R.id.bank_index, bankBean.getIndex());
        holder.setText(R.id.bank_name, bankBean.getBankName());

        if (selectIndex == holder.getPosition()) {
            holder.setTextColor(R.id.bank_name, R.color.app_bg_title);
        } else {
            holder.setTextColor(R.id.bank_name, R.color.app_body_black);
        }

        holder.setVisible(R.id.line, !islast(holder.getPosition()));

    }


    /**
     * 下标index 是否出现在第一个
     *
     * @param section
     * @return
     */
    private boolean isFirst(int section) {
        if (section == 0) return true;
        return !TextUtils.equals(mDatas.get(section - 1).getIndex(), mDatas.get(section).getIndex());
    }

    /**
     * 下标index 是否出现在最后一个
     *
     * @param section
     * @return
     */
    private boolean islast(int section) {
        if (section == mDatas.size() - 1) return true;
        return !TextUtils.equals(mDatas.get(section + 1).getIndex(), mDatas.get(section).getIndex());
    }

}
