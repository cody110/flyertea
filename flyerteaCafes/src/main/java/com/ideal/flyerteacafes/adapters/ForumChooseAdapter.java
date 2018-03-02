package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.text.TextUtils;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.CommunitySubBean;
import com.ideal.flyerteacafes.utils.tools.DataTools;

import java.util.List;

/**
 * Created by fly on 2017/8/1.
 */

public class ForumChooseAdapter extends SelectItemAdapter<CommunitySubBean> {


    public ForumChooseAdapter(Context context, List<CommunitySubBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    private String fid;

    @Override
    public void convert(final ViewHolder holder, final CommunitySubBean communitySubBean) {
        holder.setText(R.id.forum_name, communitySubBean.getName());
        holder.setVisible(R.id.forum_type_layout, false);
        holder.setVisible(R.id.jiao_layout, isShow);
        if (TextUtils.equals(communitySubBean.getFid(), fid)) {
            selectIndex = holder.getPosition();
            holder.setTextColorRes(R.id.forum_name, R.color.app_black);
            holder.setVisible(R.id.jiao_img, true);
        } else {
            holder.setVisible(R.id.jiao_img, false);
            holder.setTextColorRes(R.id.forum_name, R.color.live_com_info_item);
        }


    }


    @Override
    public void setSelectIndex(int index) {
        CommunitySubBean bean = DataTools.getBeanByListPos(mDatas, index);
        if (bean != null) {
            fid = bean.getFid();
        } else {
            fid = null;
        }
        super.setSelectIndex(index);
    }

    @Override
    public int getSelectIndex() {

        for (int i = 0; i < mDatas.size(); i++) {
            if (TextUtils.equals(mDatas.get(i).getFid(), fid)) {
                selectIndex = i;
            }
        }

        return super.getSelectIndex();
    }

    /**
     * 默认选中项
     *
     * @param fid
     */
    public void setDefFid(String fid) {
        this.fid = fid;
        notifyDataSetChanged();
    }


    public String getFid() {
        CommunitySubBean bean = DataTools.getBeanByListPos(mDatas, selectIndex);
        if (bean != null) {
            fid = bean.getFid();
        }
        return fid;
    }


    private boolean isShow = true;

    public void isShowLine(boolean bol) {
        this.isShow = bol;
        notifyDataSetChanged();
    }

}