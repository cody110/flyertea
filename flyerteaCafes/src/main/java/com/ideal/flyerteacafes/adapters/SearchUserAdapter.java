package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.model.entity.SearchUserBean;
import com.ideal.flyerteacafes.ui.activity.ChatActivity;
import com.ideal.flyerteacafes.ui.activity.UserDatumActvity;
import com.ideal.flyerteacafes.ui.activity.VerificationActivity;
import com.ideal.flyerteacafes.ui.activity.messagecenter.AddMyFriendsActivity;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;

import java.util.List;

/**
 * Created by fly on 2018/1/21.
 */

public class SearchUserAdapter extends CommonAdapter<SearchUserBean> {

    private String searchKey;

    public SearchUserAdapter(Context context, List<SearchUserBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, final SearchUserBean searchUserBean) {
        holder.setImageUrl(R.id.igv_face, searchUserBean.getFace(), R.drawable.def_face_2);
        WidgetUtils.setHighLightKey((TextView) holder.getView(R.id.tv_user_name), searchUserBean.getUsername(), searchKey);
        holder.setText(R.id.tv_user_lever, searchUserBean.getGroupname());

        holder.setVisible(R.id.igv_is_friend, !TextUtils.equals(searchUserBean.getIsfriend(), "1"));

        if (UserManger.isLogin()) {
            holder.setVisible(R.id.igv_is_friend, !TextUtils.equals(searchUserBean.getUid(), UserManger.getUserInfo().getMember_uid()));
            holder.setVisible(R.id.igv_send_private_letter, !TextUtils.equals(searchUserBean.getUid(), UserManger.getUserInfo().getMember_uid()));
        }

        holder.setOnClickListener(R.id.igv_send_private_letter, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("uid", searchUserBean.getUid());
                intent.putExtra("name", searchUserBean.getUsername());
//                bundle.putInt("pmnum",searchUserBean.get);
                mContext.startActivity(intent);
            }
        });
        holder.setOnClickListener(R.id.igv_is_friend, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, VerificationActivity.class);
                intent.putExtra("uid", searchUserBean.getUid());
                mContext.startActivity(intent);
            }
        });

        View.OnClickListener userClick=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UserDatumActvity.class);
                intent.putExtra("uid", searchUserBean.getUid());
                mContext.startActivity(intent);
            }
        };

        holder.setOnClickListener(R.id.igv_face,userClick);
        holder.setOnClickListener(R.id.tv_user_name,userClick);



    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
        notifyDataSetChanged();
    }
}
