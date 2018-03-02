package com.ideal.flyerteacafes.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.interfaces.IGridViewItemClick;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.model.entity.CommunitySubTypeBean;
import com.ideal.flyerteacafes.utils.WidgetUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2016/12/13.
 */

public class RcForumTypeAdapter extends RecyclerView.Adapter<RcForumTypeAdapter.ViewHolder> {


    private List<CommunitySubTypeBean> dataList = new ArrayList<>();
    private IGridViewItemClick itemClick;

    public RcForumTypeAdapter(List<CommunitySubTypeBean> dataList) {
        this.dataList = dataList;

    }

    public void setItemClick(IGridViewItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(FlyerApplication.getContext()).inflate(R.layout.item_gridview_community_type, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CommunitySubTypeBean bean = dataList.get(position);

        WidgetUtils.setText(holder.item_gridview_community_type_name, bean.getName());

        if (selectIndex == position) {
            holder.item_gridview_community_type_name.setBackgroundResource(R.drawable.text_bottom_line_bg);
        } else {
            holder.item_gridview_community_type_name.setBackground(null);
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.item_gridview_community_type_name)
        TextView item_gridview_community_type_name;

        public ViewHolder(View itemView) {
            super(itemView);
            x.view().inject(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClick != null)
                        itemClick.gridViewItemClick(getPosition());
                }
            });
        }
    }

    protected int selectIndex = 0;

    public void setSelectIndex(int index) {
        selectIndex = index;
        notifyDataSetChanged();
    }

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setData(List<CommunitySubTypeBean> list) {
        dataList.clear();
        if (list != null && !list.isEmpty()) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }

}
