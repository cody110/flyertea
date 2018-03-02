package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.adapters.interfaces.IGridViewItemClick;
import com.ideal.flyerteacafes.model.entity.Attachments;
import com.ideal.flyerteacafes.model.entity.MyThreadBean;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;

import java.util.List;

public class HisThreadAdapter extends CommonAdapter<MyThreadBean> {

    public static final String TYPE_TOPIC = "userthread", TYPE_REPLY = "userreply";
    public String type = TYPE_TOPIC;//默认他的帖子

    public HisThreadAdapter(Context context, List<MyThreadBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(final ViewHolder holder, final MyThreadBean bean) {
        if (TextUtils.equals(type, TYPE_TOPIC)) {//判断是否是他的帖子
            MyThreadAdapter.setContentText(mContext, (TextView) holder.getView(R.id.item_my_thread_title), bean);
            holder.setText(R.id.item_my_thread_forum, bean.getForumname());
            TextView item_my_thread_time = holder.getView(R.id.item_my_thread_time);
            item_my_thread_time.setVisibility(View.VISIBLE);
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
            GridView gridView = holder.getView(R.id.item_my_thread_img);
            gridView.setVisibility(View.VISIBLE);

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

            gridView.setAdapter(adapter);
            gridView.setClickable(false);
            gridView.setPressed(false);
            gridView.setEnabled(false);
        } else {//他的回复
            GridView gridView = holder.getView(R.id.item_my_thread_img);
            gridView.setVisibility(View.GONE);
            holder.setText(R.id.item_my_thread_title, bean.getSubject());
            holder.setText(R.id.item_my_thread_forum, bean.getForumname());
            TextView item_my_thread_time = holder.getView(R.id.item_my_thread_time);
            item_my_thread_time.setVisibility(View.INVISIBLE);
            String dbdateline = bean.getDbdateline();
            if (!TextUtils.isEmpty(dbdateline)) {
                holder.setText(R.id.item_my_thread_comment_num, DataUtils.conversionTime(dbdateline));
            } else {
                holder.setText(R.id.item_my_thread_comment_num, "");
            }
        }
    }

    private IGridViewItemClick itemClick;

    public HisThreadAdapter setGridViewItemClick(IGridViewItemClick itemClick) {
        this.itemClick = itemClick;
        return this;
    }

    /**
     * 设置帖子类型
     *
     * @param type
     */
    public void setThreadType(String type) {
        this.type = type;
    }
}
