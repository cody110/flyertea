package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.FeedLikeBean;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.UploadTask;

import java.util.List;

/**
 * Created by fly on 2016/5/24.
 */
public class LiveLikeAdapter extends CommonAdapter<FeedLikeBean> {

    public LiveLikeAdapter(Context context, List<FeedLikeBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(final ViewHolder holder, final FeedLikeBean feedLikeBean) {

        String face = UploadTask.getSplit(feedLikeBean.getUser_like_id()+"");
        holder.setImageUrl(R.id.live_like_face, face, R.drawable.def_face);

        holder.setText(R.id.live_like_name, feedLikeBean.getUser_like_name());
        DataUtils.setGroupName(holder.<ImageView>getView(R.id.live_like_level), feedLikeBean.getGroupname());

        if (feedLikeBean.getHas_sm()==2) {
            holder.setImageResource(R.id.live_like_renzheng,R.drawable.renzheng);
        } else {
            holder.setImageResource(R.id.live_like_renzheng,R.drawable.hui_renzheng);
        }

        holder.setOnClickListener(R.id.live_like_root, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iItemClick!=null)
                    iItemClick.clickLikeItem(feedLikeBean.getUser_like_id());
            }
        });
    }

    public interface iItemClick{
        void clickLikeItem(int pos);
    }


    private iItemClick iItemClick;

    public void setItemClick(iItemClick itemClick){
        this.iItemClick=itemClick;
    }


}
