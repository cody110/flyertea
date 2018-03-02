package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.CommunitySubTypeBean;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;

import java.util.List;

/**
 * Created by fly on 2017/8/16.
 */

public class ForumChooseTypeAdapter extends CommonAdapter<CommunitySubTypeBean> {

    public ForumChooseTypeAdapter(Context context, List<CommunitySubTypeBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, final CommunitySubTypeBean communitySubTypeBean) {
        LinearLayout layout = holder.getView(R.id.root);
        layout.removeAllViews();
        TextView tv = new TextView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(DensityUtil.dip2px(20), DensityUtil.dip2px(8), DensityUtil.dip2px(20), DensityUtil.dip2px(8));
        tv.setPadding(0, DensityUtil.dip2px(8), 0, DensityUtil.dip2px(8));
        tv.setBackground(mContext.getResources().getDrawable(R.drawable.add_friends_grey_border));
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(mContext.getResources().getColor(R.color.app_body_grey));
        WidgetUtils.setText(tv, mDatas.get(holder.getPosition()).getName());
        layout.addView(tv, params);
        if (TextUtils.equals(typeid, communitySubTypeBean.getFid()) || (holder.getPosition() == 0 && TextUtils.isEmpty(typeid))) {
            tv.setBackground(mContext.getResources().getDrawable(R.drawable.blue_border));
            tv.setTextColor(mContext.getResources().getColor(R.color.app_bg_title));
            typeid = communitySubTypeBean.getFid();
        }

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeid = communitySubTypeBean.getFid();
                notifyDataSetChanged();
            }
        });
    }


    private String typeid;

    public void setTypeId(String typeId) {
        this.typeid = typeId;
    }

    public String getTypeid() {
        return typeid;
    }
}
