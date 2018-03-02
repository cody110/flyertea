package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.NewFriendsBean.NewFriendsInfo;

import java.util.List;

public class NewFriendsAdapter extends CommonAdapter<NewFriendsInfo> {

    private INewFriends Inew;

    public NewFriendsAdapter(Context context, List<NewFriendsInfo> datas,
                             int layoutId, INewFriends Inew) {
        super(context, datas, layoutId);
        this.Inew = Inew;
        // TODO Auto-generated constructor stub
    }

    @Override
    public void convert(ViewHolder holder, final NewFriendsInfo t) {
        // TODO Auto-generated method stub
        holder.setText(R.id.new_friend_username, t.getFusername());
        holder.setText(R.id.new_friend_state, "同意");
        holder.setImageUrl(R.id.new_friend_portrait, t.getFace(), R.drawable.def_face);
        holder.setText(R.id.new_friend_yanzheng_message, t.getNote());


        holder.getView(R.id.new_friend_state).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Inew.agreeAddFriend(t);
            }
        });
    }

    public interface INewFriends {
        <T> void agreeAddFriend(T t);
    }

}
