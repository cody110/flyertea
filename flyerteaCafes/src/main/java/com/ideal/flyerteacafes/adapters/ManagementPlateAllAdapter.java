package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.CommunityBean;
import com.ideal.flyerteacafes.model.entity.CommunitySubBean;
import com.ideal.flyerteacafes.utils.tools.ViewTools;

import java.util.List;

/**
 * Created by fly on 2016/12/22.
 */

public class ManagementPlateAllAdapter extends BaseExpandableListAdapter {


    Context context;
    List<CommunityBean> datas;

    private LayoutInflater mInflater;

    public ManagementPlateAllAdapter(Context context, List<CommunityBean> datas) {
        this.context = context;
        this.datas = datas;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getGroupCount() {
        return datas.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return datas.get(i).getSubforums().size();
    }

    @Override
    public CommunityBean getGroup(int i) {
        return datas.get(i);
    }

    @Override
    public CommunitySubBean getChild(int i, int i1) {
        return datas.get(i).getSubforums().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mInflater.inflate(R.layout.item_plate_all_groupview, viewGroup, false);
        }
        TextView tv = (TextView) view;
        tv.setText(getGroup(i).getName());

        return tv;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildView childView;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_plate_all_child, viewGroup, false);
            childView = new ChildView();
            childView.tv = (TextView) view.findViewById(R.id.item_plate_all_child_name);
            childView.img = (ImageView) view.findViewById(R.id.item_plate_all_child_select_img);
            view.setTag(childView);
        } else {
            childView = (ChildView) view.getTag();
        }
        CommunitySubBean bean = getChild(i, i1);
        childView.tv.setText(bean.getName());
        if (bean.isSelect()) {
            childView.img.setImageResource(R.drawable.plate_add_ed);
        } else {
            childView.img.setImageResource(R.drawable.plate_add);
        }

        childView.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iPlateCancleClick != null) {
                    iPlateCancleClick.plateCancleClick(i, i1);
                }
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    class ChildView {
        TextView tv;
        ImageView img;
    }

    public interface IPlateCancleClick {
        void plateCancleClick(int i, int i1);
    }

    private IPlateCancleClick iPlateCancleClick;

    public void setiPlateCancleClick(IPlateCancleClick iPlateCancleClick) {
        this.iPlateCancleClick = iPlateCancleClick;
    }

}
