package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.util.Size;
import android.view.View;
import android.widget.AdapterView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.adapters.interfaces.IGridViewItemClick;
import com.ideal.flyerteacafes.model.entity.CommunityBean;
import com.ideal.flyerteacafes.model.entity.CommunitySubBean;
import com.ideal.flyerteacafes.ui.controls.NoScrollGridView;
import com.ideal.flyerteacafes.utils.tools.ViewTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cindy on 2016/11/17.
 */
public class CommunityListAdapter extends CommonAdapter<CommunityBean> {

    private Context context;
    private List<CommunityBean> datas;
    private int nameNum;

    public CommunityListAdapter(Context context, List<CommunityBean> datas, int layoutId) {
        super(context, datas, layoutId);
        this.context = context;
        this.datas = datas;
    }


    @Override
    public void convert(ViewHolder holder, final CommunityBean communityBean) {
        holder.setText(R.id.forum_one_level_text, communityBean.getName());
        nameNum = 0;


        final NoScrollGridView gridView = holder.getView(R.id.mgdv_communit);

        List<CommunitySubBean> subBeanList = new ArrayList<>();
        if (communityBean.getSubforums() != null) {
            nameNum = communityBean.getSubforums().size();
            int size = 0;
            if (communityBean.isShowAll()) {
                size = communityBean.getSubforums().size();
            } else {
                size = communityBean.getSubforums().size() > 8 ? 8 : communityBean.getSubforums().size();
            }
            for (CommunitySubBean subean : communityBean.getSubforums()) {
                if (isNeedFirst(subean)) {
                    subBeanList.add(subean);
                    break;
                }
            }

            for (int i = 0; i < size; i++) {
                if (!isNeedFirst(communityBean.getSubforums().get(i)))
                    subBeanList.add(communityBean.getSubforums().get(i));
            }
        }

        final CommunityGdvAdapter adapter;
        if (gridView.getTag() == null) {
            if (!subBeanList.isEmpty())
                subBeanList.get(0).setIsSelect(true);
            adapter = new CommunityGdvAdapter(context, subBeanList, R.layout.gridview_choose_communit_item);
            gridView.setAdapter(adapter);
            gridView.setTag(adapter);
        } else {
            adapter = (CommunityGdvAdapter) gridView.getTag();
            adapter.refresh(subBeanList);
            adapter.notifyDataSetChanged();
        }


        if (communityBean.isShowAll()) {
            holder.setVisible(R.id.mtv_load_more, false);
        } else {
            holder.setVisible(R.id.mtv_load_more, true);
        }

        holder.setOnClickListener(R.id.mtv_load_more, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communityBean.setShowAll(true);
                notifyDataSetChanged();
            }
        });


        holder.setText(R.id.forum_one_level_num, "(" + nameNum + ")");

    }

    /**
     * 是否是需要显示在第一项的
     *
     * @param subbean
     * @return
     */
    private boolean isNeedFirst(CommunitySubBean subbean) {
        //23 ihg  110  星空联盟  226 国内信用卡
        if (TextUtils.equals(subbean.getFid(), "23") || TextUtils.equals(subbean.getFid(), "110") || TextUtils.equals(subbean.getFid(), "59")) {
            return true;
        }
        return false;
    }


    @Override
    public int getCount() {
        return datas.size() > 3 ? 3 : datas.size();
    }
}
