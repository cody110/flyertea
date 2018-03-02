package com.ideal.flyerteacafes.adapters;

import java.util.List;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.SearchBean;
import com.ideal.flyerteacafes.utils.DataUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class SearchAdapter extends BaseAdapter {

    private Context context;
    private List<SearchBean> searchList;

    public SearchAdapter(Context context, List<SearchBean> searchList) {
        this.context = context;
        this.searchList = searchList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return searchList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return searchList.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_search_item, null);
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SearchBean bean = (SearchBean) getItem(position);

        if (!DataUtils.isEmpty(bean.getSubject()))
            holder.subject.setText(bean.getSubject());
        holder.replies.setText("回复：" + bean.getReplies());
        if (!DataUtils.isEmpty(bean.getDateline()))
            holder.dateline.setText(bean.getDateline());
        if (!DataUtils.isEmpty(bean.getName()))
            holder.forumName.setText(bean.getName());

        return convertView;
    }

    ViewHolder holder = null;

    private class ViewHolder {
        @ViewInject(R.id.listview_search_item_subject)
        TextView subject;
        @ViewInject(R.id.listview_search_item_replies)
        TextView replies;
        @ViewInject(R.id.listview_search_item_dateline)
        TextView dateline;
        @ViewInject(R.id.listview_search_item_forum_name)
        TextView forumName;
    }

    public void refreshData(List<SearchBean> searchList) {
        this.searchList.clear();
        if (searchList != null)
            this.searchList = searchList;
        notifyDataSetChanged();
    }

    public void addData(List<SearchBean> searchList) {
        this.searchList.addAll(searchList);
        notifyDataSetChanged();
    }

}
