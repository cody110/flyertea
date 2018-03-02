package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.CollectionBean;
import com.ideal.flyerteacafes.utils.DataUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

public class MyCollectAdapter extends BaseAdapter {

    private Context context;
    private List<CollectionBean> collectList;

    public MyCollectAdapter(Context context, List<CollectionBean> collectList) {
        this.context = context;
        this.collectList = collectList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return collectList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return collectList.get(position);
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
                    R.layout.listview_my_collect_layout, null, false);
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CollectionBean bean = (CollectionBean) getItem(position);

        if (!DataUtils.isEmpty(bean.getFace()))
            DataUtils.downloadPicture(holder.faceImg, bean.getFace(), R.drawable.def_face);

        if (!DataUtils.isEmpty(bean.getAuthor()))
            holder.nameText.setText(bean.getAuthor());
        if (!DataUtils.isEmpty(bean.getDateline()))
            holder.timeText
                    .setText(DataUtils.getTimeFormat(bean.getDateline()));
        if (!DataUtils.isEmpty(bean.getForum_name()))
            holder.typeText.setText(bean.getForum_name());
        if (!DataUtils.isEmpty(bean.getTitle()))
            holder.bodyText.setText(Html.fromHtml(bean.getTitle()));
        holder.replysText.setText(bean.getReplies() + "");

        return convertView;
    }

    ViewHolder holder = null;

    private class ViewHolder {
        @ViewInject(R.id.mycollect_face)
        ImageView faceImg;
        @ViewInject(R.id.mycollect_name)
        TextView nameText;
        @ViewInject(R.id.mycollect_time)
        TextView timeText;
        @ViewInject(R.id.mycollect_type)
        TextView typeText;
        @ViewInject(R.id.mycollect_body)
        TextView bodyText;
        @ViewInject(R.id.mycollect_replys)
        TextView replysText;
    }

}
