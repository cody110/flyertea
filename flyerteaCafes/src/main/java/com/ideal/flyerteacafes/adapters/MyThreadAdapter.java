package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.widget.GridView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.Attachments;
import com.ideal.flyerteacafes.model.entity.MyThreadBean;
import com.ideal.flyerteacafes.ui.view.CenterAlignImageSpan;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.Marks;
import com.ideal.flyerteacafes.utils.tools.DataTools;

import java.util.List;

public class MyThreadAdapter extends CommonAdapter<MyThreadBean> {


    public MyThreadAdapter(Context context, List<MyThreadBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(final ViewHolder holder, final MyThreadBean bean) {
        holder.setVisible(R.id.thread_status, TextUtils.equals(bean.getDisplayorder(), "-2"));
        setContentText(mContext, (TextView) holder.getView(R.id.item_my_thread_title), bean);
        holder.setText(R.id.item_my_thread_forum, bean.getForumname());
        holder.setText(R.id.item_my_thread_time, DataUtils.conversionTime(bean.getDbdateline()));
        String numStr = "";
        if (DataTools.getInteger(bean.getFlowers()) > 0) {
            numStr = bean.getFlowers() + "朵花";
        }
        if (DataTools.getInteger(bean.getReplies()) > 0) {
            if (!TextUtils.isEmpty(numStr))
                numStr += " · ";
            numStr += (bean.getReplies() + "评论");
        }
        holder.setText(R.id.item_my_thread_comment_num, numStr);

        CommonAdapter<Attachments> adapter = new CommonAdapter<Attachments>(mContext, bean.getAttachments(), R.layout.gridview_zhibo_tupian) {
            @Override
            public void convert(ViewHolder holder, Attachments attachments) {
                holder.setImageUrl(R.id.zhibo_gridview_img, attachments.getKimageurl(), R.drawable.post_def);
            }

            @Override
            public int getCount() {
                return bean.getAttachments().size() > 3 ? 3 : bean.getAttachments().size();
            }
        };

        GridView gridView = holder.getView(R.id.item_my_thread_img);
        gridView.setAdapter(adapter);

        gridView.setClickable(false);
        gridView.setPressed(false);
        gridView.setEnabled(false);

    }

    /**
     * 设置正文内容 + 热帖标识图标
     *
     * @param item_community_follow_title
     * @param communityTopicBean
     */
    public static void setContentText(Context mContext, TextView item_community_follow_title, MyThreadBean communityTopicBean) {
        LogFly.e("setContentText()");
        int showIconNum = 0;
        StringBuffer markSb = new StringBuffer();
        String mark = "icon";
        String spacing = " ";
        int markSpacingLenght = mark.length() + spacing.length();
        ImageSpan heatlevel_span = null, pushedaid_span = null, digest_span = null;

        if (!TextUtils.equals(communityTopicBean.getHeatlevel(), "0")) {
            Bitmap heatlevel_btm = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.heatlevel);
            heatlevel_span = new CenterAlignImageSpan(mContext, heatlevel_btm);
            markSb.append(mark);
            markSb.append(spacing);
            LogFly.e("火");
        }
        if (!TextUtils.equals(communityTopicBean.getPushedaid(), "0")) {
            Bitmap pushedaid_btm = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.pushedaid);
            pushedaid_span = new CenterAlignImageSpan(mContext, pushedaid_btm);
            markSb.append(mark);
            markSb.append(spacing);
            LogFly.e("好");
        }
        if (!TextUtils.equals(communityTopicBean.getDigest(), "0")) {
            Bitmap digest_btm = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.digest);
            digest_span = new CenterAlignImageSpan(mContext, digest_btm);
            markSb.append(mark);
            markSb.append(spacing);
            LogFly.e("精");
        }

        SpannableString spanString = new SpannableString(markSb.toString());
        if (!TextUtils.equals(communityTopicBean.getHeatlevel(), "0")) {
            spanString.setSpan(heatlevel_span, showIconNum * markSpacingLenght, (showIconNum + 1) * markSpacingLenght - spacing.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            showIconNum++;
        }
        if (!TextUtils.equals(communityTopicBean.getPushedaid(), "0")) {
            spanString.setSpan(pushedaid_span, showIconNum * markSpacingLenght, (showIconNum + 1) * markSpacingLenght - spacing.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            showIconNum++;
        }
        if (!TextUtils.equals(communityTopicBean.getDigest(), "0")) {
            spanString.setSpan(digest_span, showIconNum * markSpacingLenght, (showIconNum + 1) * markSpacingLenght - spacing.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            showIconNum++;
        }
        item_community_follow_title.setText(spanString);
        item_community_follow_title.append(communityTopicBean.getSubject());
    }


}
