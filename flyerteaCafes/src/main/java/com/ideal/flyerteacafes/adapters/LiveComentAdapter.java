package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.model.entity.ReplysBean;
import com.ideal.flyerteacafes.ui.activity.UserDatumActvity;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.DialogUtils;
import com.ideal.flyerteacafes.utils.UploadTask;

import java.util.List;

public class LiveComentAdapter extends CommonAdapter<ReplysBean> {

    private Context context;

    public LiveComentAdapter(Context context, List<ReplysBean> datas, int layoutId) {
        super(context, datas, layoutId);
        this.context = context;
    }

    @Override
    public void convert(final ViewHolder holder, final ReplysBean replys) {
        String headUrl= UploadTask.getSplit(String.valueOf(replys.getFrom_user_id()));
        holder.setImageUrl(R.id.item_comment_head, headUrl, R.drawable.def_face);
        holder.setText(R.id.item_comment_name, replys.getFrom_user_name());
        holder.setText(R.id.item_comment_time, DataUtils.conversionTime(replys.getCreated_at()));
        if(replys.getDirect_reply()==0){
            holder.setText(R.id.item_comment_content, replys.getContent());
        }else if(replys.getDirect_reply()==1){
            String temp="回复"+replys.getTo_user_name()+":"+replys.getContent();
            SpannableStringBuilder builder = new SpannableStringBuilder(temp);
            //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
            ForegroundColorSpan blueSpan = new ForegroundColorSpan(Color.BLUE);
            int pos=temp.indexOf(":");
//            try{
                builder.setSpan(blueSpan, 2, pos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
/*                zanNameTv.setMovementMethod(LinkMovementMethod.getInstance());
                zanNameTv.setText(addClickablePart(likeUsers), TextView.BufferType.SPANNABLE);*/
//                holder.setText(R.id.item_comment_content, builder);
//                holder.setText(R.id.item_comment_content,addClickablePart(temp,replys.getTo_user_name()));// addClickablePart(temp,replys.getTo_user_name());

           TextView tv= holder.getView(R.id.item_comment_content);
            tv.setMovementMethod(LinkMovementMethod.getInstance());
            tv.setText(addClickablePart(temp,replys.getTo_user_name(),replys.getTo_user_id()), TextView.BufferType.SPANNABLE);

        /*    }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
                holder.setText(R.id.item_comment_content, replys.getContent());
                addClickablePart(temp, replys.getTo_user_name());
            }*/


        }
        holder.getView(R.id.item_comment_head).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (UserManger.isLogin()) {
                    Bundle bundle=new Bundle();
                    bundle.putString("uid", String.valueOf(replys.getFrom_user_id()));
                    bundle.putString("activity", "SearchMemberActivity");
                    Intent intent=new Intent();
                    intent.putExtras(bundle);
                    intent.setClass(context,UserDatumActvity.class);
                    context.startActivity(intent);
                } else {
                    DialogUtils.showDialog(context);
                }
            }
        });

        holder.getView(R.id.RelativeLayout1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ck!=null){
                    ck.clickItem(holder.getPosition());
                }
            }
        });
    }

    /**
     *  局部给TextView 添加点击事件
     *  @param str
     **/
    private SpannableStringBuilder addClickablePart(String str,String userName,final int userId) {

        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
                final int start = str.indexOf(userName);
                ssb.setSpan(new ClickableSpan() {

                    @Override
                    public void onClick(View widget) {
/*                        if (ck != null) {
                            ck.clickUserName(userId);
                        }*/
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(context.getResources().getColor(R.color.live_com_info_item));
//                         ds.setColor(Color.RED); // 设置文本颜色
                        // 去掉下划线
                        ds.setUnderlineText(false);
                    }

                }, start, start + userName.length(), 0);
        return ssb;
    }
    private  Click ck;
    public void setClick(Click click){
        ck=click;
    }
    public interface Click{
           void clickUserName(int userId);
           void clickItem(int index);
    }

}
