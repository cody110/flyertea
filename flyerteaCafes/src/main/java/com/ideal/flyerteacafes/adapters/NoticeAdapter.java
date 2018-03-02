package com.ideal.flyerteacafes.adapters;

import java.util.List;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.NoticeBean;
import com.ideal.flyerteacafes.utils.DataUtils;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class NoticeAdapter extends BaseAdapter {
    private Context context;
    private List<NoticeBean> noticeList;

    public NoticeAdapter(Context context, List<NoticeBean> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return noticeList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return noticeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.listview_item_notice_fragment_layout, null);
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NoticeBean bean = (NoticeBean) getItem(position);
        holder.subjectText.setText(Html.fromHtml(bean.getSubject()));
        holder.timeText.setText(DataUtils.getTimeFormat(bean.getStarttime()));

        return convertView;
    }

    ViewHolder holder = null;

    private class ViewHolder {
        @ViewInject(R.id.listview_item_notice_isread)
        ImageView isReadImg;
        @ViewInject(R.id.listview_item_notice_subject)
        TextView subjectText;
        @ViewInject(R.id.listview_item_notice_time)
        TextView timeText;
    }
}
