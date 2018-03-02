package com.ideal.flyerteacafes.adapters;

import android.content.Context;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.CommunitySubBean;

import java.util.List;

public class ForumTwoLevelAdapter extends CommonAdapter<CommunitySubBean> {


    public ForumTwoLevelAdapter(Context context, List<CommunitySubBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, CommunitySubBean communitySubBean) {

        holder.setImageUrl(R.id.forum_two_level_img, communitySubBean.getIcon(), R.drawable.icon_def);
        holder.setText(R.id.forum_two_level_name, communitySubBean.getName());
        holder.setText(R.id.forum_two_level_newpost, communitySubBean.getTodayposts());

    }
}
