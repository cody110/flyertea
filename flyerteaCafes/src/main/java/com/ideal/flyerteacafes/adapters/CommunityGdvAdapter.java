package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.CommunitySubBean;
import com.ideal.flyerteacafes.utils.ToastUtils;

import java.util.List;

/**
 * Created by Cindy on 2016/11/17.
 */
public class CommunityGdvAdapter extends CommonAdapter<CommunitySubBean> {

    public CommunityGdvAdapter(Context context, List<CommunitySubBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }


    @Override
    public void convert(ViewHolder holder, final CommunitySubBean communitySubBean) {

        holder.setText(R.id.forum_two_level_name, communitySubBean.getName());
        holder.setImageUrl(R.id.forum_two_level_img, communitySubBean.getIcon(), R.drawable.icon_def);

        if(communitySubBean.isSelect()){
            holder.setVisible(R.id.forum_two_level_img_selected,true);
        }else{
            holder.setVisible(R.id.forum_two_level_img_selected,false);
        }

        holder.setOnClickListener(R.id.mrl_forum_two_layout, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communitySubBean.setIsSelect(!communitySubBean.isSelect());
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getCount() {
        return mDatas.size();
    }
}
