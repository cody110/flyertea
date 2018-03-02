package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.InvitationInfo;
import com.ideal.flyerteacafes.utils.DataUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

public class MainViewPagerAdapter extends BaseAdapter {

    private Context context;
    private List<InvitationInfo> invitationList;

    public MainViewPagerAdapter(Context context,
                                List<InvitationInfo> invitationList) {
        this.context = context;
        this.invitationList = invitationList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return invitationList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return invitationList.get(position);
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
                    R.layout.listview_item_main, null, false);
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        InvitationInfo info = (InvitationInfo) getItem(position);
        String imgUrl = info.getImgUrl();
        if (!DataUtils.isEmpty(imgUrl) && !imgUrl.equals("0")) {
            holder.main_list_item_img.setVisibility(View.VISIBLE);
        } else {
            holder.main_list_item_img.setVisibility(View.GONE);
        }

        if (!DataUtils.isEmpty(info.getAuthor()))
            holder.main_list_item_author.setText(info.getAuthor());
        if (!DataUtils.isEmpty(info.getDbdateline()))
            holder.main_list_item_dbdateline.setText(DataUtils.getTimeFormat(info.getDbdateline()));
        if (!DataUtils.isEmpty(info.getSubject()))
            holder.main_list_item_title.setText(info.getSubject());
        if (!DataUtils.isEmpty(info.getReplies() + ""))
            holder.main_list_item_comment_num.setText(info.getReplies() + "");
        if (!DataUtils.isEmpty(info.getSummary())) {
            String summary = DataUtils.Html2Text(info.getSummary());
            summary = info.getSummary();
            summary = summary.replaceAll("\\[attach]\\d*\\[/attach]", "QQQQQQQQ");
            holder.main_list_item_body.setText(summary);
        }

        DataUtils.downloadPicture( holder.main_list_item_img, imgUrl,  R.drawable.post_def);
        DataUtils.downloadPicture( holder.main_list_item_ico, info.getForum_icon(), R.drawable.icon_def);


        return convertView;
    }

    ViewHolder holder = null;

    private class ViewHolder {
        @ViewInject(R.id.main_list_item_img)
        ImageView main_list_item_img;
        @ViewInject(R.id.main_list_item_title)
        TextView main_list_item_title;
        @ViewInject(R.id.main_list_item_ico)
        ImageView main_list_item_ico;
        @ViewInject(R.id.main_list_item_author)
        TextView main_list_item_author;
        @ViewInject(R.id.main_list_item_dbdateline)
        TextView main_list_item_dbdateline;
        @ViewInject(R.id.main_list_item_comment_num)
        TextView main_list_item_comment_num;
        @ViewInject(R.id.main_list_item_body)
        TextView main_list_item_body;

    }

    // 下拉，数据刷新了
    public void refreshData(List<InvitationInfo> invitationList) {
        this.invitationList = invitationList;
        notifyDataSetChanged();
    }

    // 添加数据
    public void addData(List<InvitationInfo> invitationList) {
        this.invitationList.addAll(invitationList);
        notifyDataSetChanged();
    }

}
