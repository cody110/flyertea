package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.NotificationBean;
import com.ideal.flyerteacafes.utils.DataUtils;

import java.util.List;

/**
 * Created by Ceci on 2017/3/23.
 */
public class AddFriendsAdapter extends CommonAdapter<NotificationBean> {

    private IAddFriendsAdapterClick iAddFriendsAdapterClick;

    public interface IAddFriendsAdapterClick {
        /**
         * 忽略
         */
        void ignoreFriends(int position,NotificationBean notificationBean);

        /**
         * 接受
         */
        void acceptFriends(int position, NotificationBean notificationBean);
    }


    public AddFriendsAdapter(Context context, List<NotificationBean> datasAll, int layoutId,IAddFriendsAdapterClick iAddFriendsAdapterClick) {
        super(context, datasAll, layoutId);
        this.iAddFriendsAdapterClick=iAddFriendsAdapterClick;
    }

    @Override
    public void convert(final ViewHolder holder, final NotificationBean notificationBean) {

        holder.setText(R.id.item_my_reply_title,notificationBean.getFromuser()+"\b"+"请求加你为好友");
        String dbdateline= notificationBean.getDateline();
        if(!TextUtils.isEmpty(dbdateline)){
            holder.setText(R.id.item_my_reply_time, DataUtils.conversionTime(dbdateline));
        }else{
            holder.setText(R.id.item_my_reply_time,"");
        }

       holder.setOnClickListener(R.id.item_my_reply_ignore, new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(iAddFriendsAdapterClick!=null)
               iAddFriendsAdapterClick.ignoreFriends(holder.getPosition(),notificationBean);
           }
       });

        holder.setOnClickListener(R.id.item_my_reply_accept, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(iAddFriendsAdapterClick!=null)
                iAddFriendsAdapterClick.acceptFriends(holder.getPosition(),notificationBean);
            }
        });
    }
}
