package com.ideal.flyerteacafes.adapters;

import java.util.List;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.SearchHistoryBean;
import com.ideal.flyerteacafes.utils.DataUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchHistoryAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<SearchHistoryBean> listBean;
    private boolean flag = false;

    public SearchHistoryAdapter(Context context, List<SearchHistoryBean> listBean, boolean flag) {
        inflater = LayoutInflater.from(context);
        this.listBean = listBean;
        this.flag = flag;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listBean.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null)
            convertView = inflater.inflate(R.layout.listview_search_history_layout, null);

        SearchHistoryBean bean = (SearchHistoryBean) getItem(position);
        TextView empty = (TextView) convertView.findViewById(R.id.list_search_history_cancel);
        TextView historyName = (TextView) convertView.findViewById(R.id.list_search_history_name);

        if (listBean.size() - 1 == position && flag) {
            empty.setText(bean.getSearchName());
            empty.setVisibility(View.VISIBLE);
            historyName.setVisibility(View.GONE);
        } else {
            historyName.setText(bean.getSearchName());
            empty.setVisibility(View.GONE);
            historyName.setVisibility(View.VISIBLE);
        }


        return convertView;
    }

    public void refreshData(List<SearchHistoryBean> historyList) {
        listBean.clear();
        if (!DataUtils.isEmpty(historyList))
            listBean.addAll(historyList);
        notifyDataSetChanged();
    }

}
