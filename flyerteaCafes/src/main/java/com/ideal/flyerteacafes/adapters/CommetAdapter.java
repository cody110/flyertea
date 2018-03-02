package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.DisComment;
import com.ideal.flyerteacafes.model.entity.DisComment.Comment;
import com.ideal.flyerteacafes.ui.activity.UserDatumActvity;
import com.ideal.flyerteacafes.utils.DataUtils;

import java.util.List;

public class CommetAdapter extends CommonAdapter<DisComment.Comment> {

    private Context context;

    public CommetAdapter(Context context, List<Comment> datas, int layoutId) {
        super(context, datas, layoutId);
        this.context = context;
    }

    @Override
    protected boolean isNeedMyList() {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, final Comment t) {
        holder.setImageUrl(R.id.item_comment_head, t.getFace(), R.drawable.def_face);
        holder.setText(R.id.item_comment_name, t.getAuthor());
        holder.setText(R.id.item_comment_time, DataUtils.conversionTime(Long.parseLong(t.getDateline())));
        holder.setText(R.id.item_comment_content, t.getMessage());
        holder.getView(R.id.item_comment_head).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Bundle bundle = new Bundle();
                bundle.putString("uid", t.getUid());
                bundle.putString("activity", "SearchMemberActivity");
                Intent intent = new Intent();
                intent.setClass(context, UserDatumActvity.class);
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
                context.startActivity(intent);
            }
        });
    }

}
