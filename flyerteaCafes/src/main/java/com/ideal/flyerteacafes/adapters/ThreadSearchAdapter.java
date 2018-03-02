package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.text.TextUtils;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.ThreadSearchBean;
import com.ideal.flyerteacafes.model.loca.SegmentedStringMode;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2016/12/19.
 */

public class ThreadSearchAdapter extends CommonAdapter<ThreadSearchBean> {

    private String searchKey;

    public ThreadSearchAdapter(Context context, List<ThreadSearchBean> datasAll, int layoutId, String searchKey) {
        super(context, datasAll, layoutId);
        this.searchKey = searchKey;
    }

    @Override
    public void convert(ViewHolder holder, ThreadSearchBean threadSearchBean) {

        if (!TextUtils.isEmpty(threadSearchBean.getSubject()) && !TextUtils.isEmpty(searchKey)) {

            int index = threadSearchBean.getSubject().indexOf(searchKey);
            if (index == -1) {
                holder.setText(R.id.item_thread_search_title, threadSearchBean.getSubject());
            } else {
                String str1 = threadSearchBean.getSubject().substring(0, index);
                String str2 = searchKey;
                String str3 = threadSearchBean.getSubject().substring(index + searchKey.length(), threadSearchBean.getSubject().length());

                List<SegmentedStringMode> modeList = new ArrayList<>();
                if (!TextUtils.isEmpty(str1)) {
                    SegmentedStringMode mode1 = new SegmentedStringMode(str1, R.dimen.app_body_size_1, R.color.app_body_grey, null);
                    modeList.add(mode1);
                }
                SegmentedStringMode mode2 = new SegmentedStringMode(str2, R.dimen.app_body_size_1, R.color.red, null);
                modeList.add(mode2);
                if (!TextUtils.isEmpty(str3)) {
                    SegmentedStringMode mode3 = new SegmentedStringMode(str3, R.dimen.app_body_size_1, R.color.app_body_grey, null);
                    modeList.add(mode3);
                }
                holder.setText(R.id.item_thread_search_title, DataUtils.getSegmentedDisplaySs(modeList));
            }
        } else {
            holder.setText(R.id.item_thread_search_title, threadSearchBean.getSubject());
        }
        holder.setText(R.id.item_thread_search_forumname, threadSearchBean.getForumname());
        holder.setText(R.id.item_thread_search_time, DataUtils.conversionTime(threadSearchBean.getDbdateline()));

        StringBuffer sb = new StringBuffer();
        if (DataTools.getInteger(threadSearchBean.getFlowers()) > 0) {
            sb.append(threadSearchBean.getFlowers());
            sb.append("鲜花");
            sb.append(".");
        }
        if (DataTools.getInteger(threadSearchBean.getReplies()) > 0) {
            sb.append(threadSearchBean.getReplies());
            sb.append("评论");
        }
        holder.setText(R.id.item_thread_search_comment_num, sb.toString());
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

}
