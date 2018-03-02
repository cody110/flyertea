package com.ideal.flyerteacafes.ui.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.RaidersGroupAdapter;
import com.ideal.flyerteacafes.adapters.RaidersGroupAdapter2;
import com.ideal.flyerteacafes.model.entity.RaidersRootBean;
import com.ideal.flyerteacafes.ui.activity.RaidersListActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ArticleCommentActivity;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;

/**
 * Created by fly on 2017/8/30.
 */

public class RaidersGroupLayout extends LinearLayout {

    @ViewInject(R.id.raiders_group_title)
    private TextView raiders_group_title;
    @ViewInject(R.id.raiders_group_horizontalscrollview_gridview)
    public GridView raiders_group_horizontalscrollview_gridview;


    private String identifier;

    public RaidersGroupLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.raiders_group_layout, this);
        x.view().inject(this, this);

        raiders_group_horizontalscrollview_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String requestUrl = Utils.HttpRequest.HTTP_ARTICLE_TYPE_LIST;
                RaidersRootBean.CatsBean typeInfoBean1 = datas.get(i);
                requestUrl = requestUrl + "&catid=" + typeInfoBean1.getCatid();
                RaidersListActivity.startRaidersListActivity(getContext(), datas.get(i).getName(), requestUrl, typeInfoBean1.getSortid(), identifier, typeInfoBean1.getCid(), datas.get(i).getCatid());

                HashMap<String, String> map = new HashMap<>();
                map.put("name", typeInfoBean1.getName());
                MobclickAgent.onEvent(getContext(), FinalUtils.EventId.guidelist_category_click, map);
            }
        });


    }

    public void bindGroup(RaidersRootBean.RaidersBean typeInfoBean1) {
        bindGroup(typeInfoBean1, false);
    }

    public void bindGroup(RaidersRootBean.RaidersBean typeInfoBean1, boolean ishot) {
        if (typeInfoBean1 == null) return;
        identifier = typeInfoBean1.getIdentifier();
        WidgetUtils.setText(raiders_group_title, typeInfoBean1.getName());
        bindGroup(typeInfoBean1.getCats(), ishot);
    }

    List<RaidersRootBean.CatsBean> datas;

    public void bindGroup(List<RaidersRootBean.CatsBean> datas, boolean ishot) {
        if (DataUtils.isEmpty(datas)) return;
        this.datas = datas;
        if (ishot) {
            RaidersGroupAdapter raidersGroupAdapter = new RaidersGroupAdapter(getContext(), datas, R.layout.item_raiders_group);
            raiders_group_horizontalscrollview_gridview.setAdapter(raidersGroupAdapter);
        } else {
            RaidersGroupAdapter2 raidersGroupAdapter = new RaidersGroupAdapter2(getContext(), datas, R.layout.item_raiders_group_2);
            raiders_group_horizontalscrollview_gridview.setAdapter(raidersGroupAdapter);
        }

    }

//    public void bindReport(List<RaidersRootBean.ArticlesBean> datas) {
//        if (DataUtils.isEmpty(datas)) return;
//        List<RaidersRootBean.ArticlesBean> tabBeanList = datas.subList(0, datas.size() > 3 ? 3 : datas.size());
//        int allWidth = DensityUtil.getWidth((Activity) getContext()) - DensityUtil.dip2px(getContext(), 24);
//        int LIEWIDTH = (int) (allWidth / 1.5);
//        reportAdapter = new RaidersRootAdapter(getContext(), tabBeanList, R.layout.item_root_gride_raider);
//        raiders_group_report_horizontalscrollview_gridview.setAdapter(reportAdapter);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(reportAdapter.getCount() * LIEWIDTH,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        raiders_group_report_horizontalscrollview_gridview.setLayoutParams(params);
//        raiders_group_report_horizontalscrollview_gridview.setColumnWidth(LIEWIDTH);
//        raiders_group_report_horizontalscrollview_gridview.setStretchMode(GridView.NO_STRETCH);
//        int count = reportAdapter.getCount();
//        raiders_group_report_horizontalscrollview_gridview.setNumColumns(count);
//    }

}
