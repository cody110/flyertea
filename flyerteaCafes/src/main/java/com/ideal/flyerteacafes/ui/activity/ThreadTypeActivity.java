package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.SelectItemAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.CommunitySubTypeBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2016/11/30.
 */
@ContentView(R.layout.activity_thread_type)
public class ThreadTypeActivity extends BaseActivity {

    @ViewInject(R.id.thrad_type_gridview)
    GridView thrad_type_gridview;
    @ViewInject(R.id.thrad_sort_gridview)
    GridView thrad_sort_gridview;


    private List<CommunitySubTypeBean> communitySubTypeBeanList;
    private List<String> sortlist;

    private SelectItemAdapter typeAdapter;

    private SelectItemAdapter sortAdapter;

    /**
     * 帖子分类下标
     * 排序要传的字段
     */
    public static final String BUNDLE_TYPE_INDEX = "typeindex", BUNDLE_SORT_STR = "sortstr";
    private int typeIndex = 0;
    private String sortStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        initVariables();
        initViews();
    }

    @Override
    public void initVariables() {
        super.initVariables();
        communitySubTypeBeanList = (List<CommunitySubTypeBean>) getIntent().getSerializableExtra("data");
        sortlist = new ArrayList<>();
        sortlist.add("回复时间");
        sortlist.add("发帖时间");
        typeIndex = getIntent().getIntExtra(BUNDLE_TYPE_INDEX, 0);
        sortStr = getIntent().getStringExtra(BUNDLE_SORT_STR);
    }

    @Override
    public void initViews() {
        super.initViews();

        thrad_type_gridview.setAdapter(typeAdapter = new SelectItemAdapter<CommunitySubTypeBean>(this, communitySubTypeBeanList, R.layout.item_thread_type_choose) {
            @Override
            public void convert(ViewHolder holder, CommunitySubTypeBean communitySubTypeBean) {
                if (selectIndex == holder.getPosition()) {
                    holder.setTextColorRes(R.id.item_thread_type_choose_name, R.color.app_bg_title);
                } else {
                    holder.setTextColorRes(R.id.item_thread_type_choose_name, R.color.app_body_blacks);
                }
                holder.setText(R.id.item_thread_type_choose_name, communitySubTypeBean.getName());
            }
        });


        thrad_sort_gridview.setAdapter(sortAdapter = new SelectItemAdapter<String>(this, sortlist, R.layout.item_thread_type_choose) {
            @Override
            public void convert(ViewHolder holder, String s) {
                if (selectIndex == holder.getPosition()) {
                    holder.setTextColorRes(R.id.item_thread_type_choose_name, R.color.app_bg_title);
                } else {
                    holder.setTextColorRes(R.id.item_thread_type_choose_name, R.color.app_body_blacks);
                }
                holder.setText(R.id.item_thread_type_choose_name, s);
            }
        });
        typeAdapter.setSelectIndex(typeIndex);
        if (TextUtils.equals(sortStr, "dateline")) {
            sortAdapter.setSelectIndex(1);
        } else {
            sortAdapter.setSelectIndex(0);
        }
    }

    @Event({R.id.toolbar_left, R.id.toolbar_right})
    private void click(View v) {
        if (v.getId() == R.id.toolbar_left) {
            finish();
        } else if (v.getId() == R.id.toolbar_right) {
            Bundle bundle = new Bundle();
            bundle.putInt(BUNDLE_TYPE_INDEX, typeAdapter.getSelectIndex());
            if (sortAdapter.getSelectIndex() == 0) {
                bundle.putString(BUNDLE_SORT_STR, "lastpost");
            } else {
                bundle.putString(BUNDLE_SORT_STR, "dateline");
            }
            jumpActivitySetResult(bundle);
        }
    }

    @Event(value = R.id.thrad_type_gridview, type = AdapterView.OnItemClickListener.class)
    private void typeItemClick(AdapterView<?> parent, View view, int position, long id) {
        typeAdapter.setSelectIndex(position);
    }

    @Event(value = R.id.thrad_sort_gridview, type = AdapterView.OnItemClickListener.class)
    private void sortItemClick(AdapterView<?> parent, View view, int position, long id) {
        sortAdapter.setSelectIndex(position);
    }


}
