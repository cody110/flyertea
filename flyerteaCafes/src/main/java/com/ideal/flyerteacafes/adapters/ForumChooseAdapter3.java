package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.CommunitySubBean;
import com.ideal.flyerteacafes.model.entity.CommunitySubTypeBean;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;

import java.util.List;

/**
 * Created by fly on 2017/8/1.
 */

public class ForumChooseAdapter3 extends SelectItemAdapter<CommunitySubTypeBean> {


    List<CommunitySubTypeBean> typeDatas;

    public ForumChooseAdapter3(Context context, List<CommunitySubTypeBean> datasAll, int layoutId, List<CommunitySubTypeBean> typeDatas) {
        super(context, datasAll, layoutId);
        this.typeDatas = typeDatas;
        selectIndex = 0;
    }

    private String fid, typeid;

    @Override
    public void convert(ViewHolder holder, CommunitySubTypeBean communitySubTypeBean) {
        holder.setText(R.id.forum_name, communitySubTypeBean.getName());


        if (selectIndex == holder.getPosition()) {
            holder.setTextColorRes(R.id.forum_name, R.color.app_black);
            if (DataUtils.isEmpty(typeDatas) || typeDatas.size() == 1) {
                holder.setVisible(R.id.forum_type_layout, false);
                if (!DataUtils.isEmpty(typeDatas)) {
                    typeid = typeDatas.get(0).getFid();
                }
            } else {
                holder.setVisible(R.id.forum_type_layout, true);
                final LinearLayout typeLayout = holder.getView(R.id.forum_type_layout);
                typeLayout.removeAllViews();
                for (int i = 0; i < typeDatas.size(); i++) {
                    TextView tv = new TextView(mContext);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(DensityUtil.dip2px(20), DensityUtil.dip2px(8), DensityUtil.dip2px(20), DensityUtil.dip2px(8));
                    tv.setPadding(0, DensityUtil.dip2px(8), 0, DensityUtil.dip2px(8));
                    tv.setBackground(mContext.getResources().getDrawable(R.drawable.add_friends_grey_border));
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextColor(mContext.getResources().getColor(R.color.app_body_grey));
                    WidgetUtils.setText(tv, typeDatas.get(i).getName());
                    typeLayout.addView(tv, params);

                    if (TextUtils.equals(typeid, typeDatas.get(i).getFid()) || (i == 0 && TextUtils.isEmpty(typeid))) {
                        tv.setBackground(mContext.getResources().getDrawable(R.drawable.blue_border));
                        tv.setTextColor(mContext.getResources().getColor(R.color.app_bg_title));
                        typeid = typeDatas.get(i).getFid();
                    }

                    final int finalI = i;
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            for (int j = 0; j < typeLayout.getChildCount(); j++) {
                                typeLayout.getChildAt(j).setBackground(mContext.getResources().getDrawable(R.drawable.add_friends_grey_border));
                                ((TextView) typeLayout.getChildAt(j)).setTextColor(mContext.getResources().getColor(R.color.app_body_grey));
                            }
                            view.setBackground(mContext.getResources().getDrawable(R.drawable.blue_border));
                            ((TextView) view).setTextColor(mContext.getResources().getColor(R.color.app_bg_title));
                            typeid = typeDatas.get(finalI).getFid();
                        }
                    });
                }
            }


        } else {
            holder.setVisible(R.id.forum_type_layout, false);
            holder.setTextColorRes(R.id.forum_name, R.color.live_com_info_item);
        }

    }

    @Override
    public void setSelectIndex(int index) {
        CommunitySubTypeBean bean = DataTools.getBeanByListPos(mDatas, index);
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
    public void setDefFid(String fid, String typeid) {
        this.fid = fid;
        this.typeid = typeid;
        notifyDataSetChanged();
    }

    public String getTypeid() {
        return typeid;
    }

    public String getFid() {
        CommunitySubTypeBean bean = DataTools.getBeanByListPos(mDatas, selectIndex);
        if (bean != null) {
            fid = bean.getFid();
        }
        return fid;
    }
}