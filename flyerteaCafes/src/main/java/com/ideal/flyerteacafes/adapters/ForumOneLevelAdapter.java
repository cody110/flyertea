package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.text.TextUtils;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.model.entity.CommunityBean;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;

import java.util.List;

public class ForumOneLevelAdapter extends SelectItemAdapter<CommunityBean> {


    public ForumOneLevelAdapter(Context context, List<CommunityBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    private String fid;

    @Override
    public void convert(com.ideal.flyerteacafes.adapters.base.ViewHolder holder, CommunityBean communityBean) {
        if (TextUtils.equals(communityBean.getFid(), fid)) {
            selectIndex = holder.getPosition();
            holder.setBackgroundColor(R.id.forum_one_level_root, mContext.getResources().getColor(R.color.white));
            holder.setBackgroundColor(R.id.forum_one_level_select_mark, mContext.getResources().getColor(R.color.app_bg_title));
            holder.setTextColorRes(R.id.forum_one_level_text, R.color.app_bg_title);
        } else {
            holder.setBackgroundColor(R.id.forum_one_level_root, mContext.getResources().getColor(R.color.app_bg_grey));
            holder.setBackgroundColor(R.id.forum_one_level_select_mark, mContext.getResources().getColor(R.color.app_bg_grey));
            holder.setTextColorRes(R.id.forum_one_level_text, R.color.app_body_black);
        }

        holder.setText(R.id.forum_one_level_text, communityBean.getName());

    }

    @Override
    public void setSelectIndex(int index) {
        CommunityBean bean = DataTools.getBeanByListPos(mDatas, index);
        if (bean != null) {
            fid = bean.getFid();
        }
        super.setSelectIndex(index);
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


}
