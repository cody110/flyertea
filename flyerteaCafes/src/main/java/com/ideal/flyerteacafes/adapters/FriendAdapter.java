package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.FriendsInfo;
import com.ideal.flyerteacafes.ui.controls.RoundImageView;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.xutils.XutilsHelp;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;


public class FriendAdapter extends BaseAdapter implements SectionIndexer {
    private List<FriendsInfo> list = null;
    private Context mContext;

    public FriendAdapter(Context mContext, List<FriendsInfo> list) {
        this.mContext = mContext;
        this.list = list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<FriendsInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup v) {
        ViewHolder viewHolder = null;
        final FriendsInfo friendsItem = list.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_friendinfo, null);
            x.view().inject(viewHolder, convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(friendsItem.getSortLetters());
        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }
        DataUtils.downloadPicture(viewHolder.headIcon, this.list.get(position).getFace(), R.drawable.def_face);
        viewHolder.tvTitle.setText(this.list.get(position).getUsername());
        if (!DataUtils.isEmpty(list.get(position).getSightml())) {
            viewHolder.tvSignature.setText(list.get(position).getSightml());
        } else {
            viewHolder.tvSignature.setText(mContext.getString(R.string.not_sightml));
        }

        return convertView;

    }


    final static class ViewHolder {
        @ViewInject(R.id.catalog)
        TextView tvLetter;
        @ViewInject(R.id.head_icon)
        RoundImageView headIcon;
        @ViewInject(R.id.friend_name)
        TextView tvTitle;
        @ViewInject(R.id.personality_signature)
        TextView tvSignature;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */

    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }


    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */

    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }
}